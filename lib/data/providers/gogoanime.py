import requests
from bs4 import BeautifulSoup
from typing import Any

from lib.data.models.base_models import CategoryModel, DiscreteCategoryModel, EpisodeModel, GenreModel, ItemModel, DetailedItemModel, TypeModel
from lib.utils.helpers.time_helper import year_string_to_datetime_string
from lib.utils.values.patterns import style_image_url_pattern, item_id_remove_pattern


class GogoanimeProvider:
    base_url = "https://gogoanime3.net"
    ajax_url = "https://ajax.gogo-load.com/ajax"
    home_cats_paths = [
        "page-recent-release-ongoing.html?page=1",
        "page-recent-release.html?page=1&type=1",
        "page-recent-release.html?page=1&type=2",
        "page-recent-release.html?page=1&type=3",
    ]

    def __init__(self) -> None:
        GogoanimeProvider.base_url = GogoanimeProvider.check_url_for_redirect(
            GogoanimeProvider.base_url)
        GogoanimeProvider.ajax_url = GogoanimeProvider.check_url_for_redirect(
            GogoanimeProvider.ajax_url)

    def parse_ajax_category_page(self, soup: BeautifulSoup, path: str) -> CategoryModel:
        if path.endswith("ongoing.html?page=1"):
            category_name = "Ongoing"
        elif path.endswith("1"):
            category_name = "Recent Release Sub"
        elif path.endswith("2"):
            category_name = "Recent Release Dub"
        else:
            category_name = "Recent Release Chinese"
        items = []
        for item in soup.select("ul")[-1].select("li"):
            item_id = item.select_one("a")["href"].strip().split("/")[-1]
            item_title = item.select_one("a")["title"].strip()
            try:
                item_image_url = style_image_url_pattern.search(
                    item.select_one("a > div").get("style")).group(1)
            except Exception:
                item_image_url = item.select_one("a > img")["src"]
            item_sub = False if ("(Dub)") in item_title else True
            item_episodes = int(item.select(
                "p")[-1].text.strip().split(" ")[-1])
            items.append(ItemModel(
                id=item_id,
                title=item_title,
                imageUrl=item_image_url,
                sub=item_sub,
                numOfEps=item_episodes
            ))
        page_urls = map(lambda x: f"{path.split('?')[0]}{x['href']}", soup.select(
            "ul.pagination-list > li > a"))
        category = CategoryModel(
            name=category_name, items=items, pageUrls=page_urls)
        return category

    def get_ajax_categories(self) -> dict[str, Any]:
        categories = []
        for home_cat_path in GogoanimeProvider.home_cats_paths:
            response = requests.get(
                f"{GogoanimeProvider.ajax_url}/{home_cat_path}")
            soup = BeautifulSoup(response.text, features="html.parser")
            categories.append(
                self.parse_ajax_category_page(soup, home_cat_path))
        return [category.toJson() for category in categories]

    def parse_item_details_page(self, soup: BeautifulSoup) -> DetailedItemModel:
        title = soup.select_one("h1").text.strip()
        image_url = soup.select_one("div.anime_info_body_bg > img")["src"]
        sub = True if not ("(Dub)") in title else False
        info_tags = soup.select("p.type")
        type = None
        try:
            type_tag = list(filter(
                lambda x: "Type:" in x.text.strip(), info_tags))[0]
            type = TypeModel(
                id=type_tag.select_one("a")["href"].strip().split("/")[-1],
                name=type_tag.select_one("a").text.strip()
            )
        except Exception as e:
            print(e)
            pass
        synopsis = ""
        try:
            synopsis = list(filter(lambda x: "Plot Summary:" in x.text.strip(), info_tags))[
                0].text.replace("Plot Summary:", "").strip().replace("\n", "").replace("\r", "").replace("\t", "")
        except Exception:
            pass
        genres = []
        try:
            for genre in list(filter(lambda x: "Genre:" in x.text.strip(), info_tags))[0].select("a"):
                print(genre)
                genres.append(GenreModel(
                    id=genre["href"].strip().split("/")[-1],
                    name=genre.text.strip()
                ))
        except Exception:
            pass
        released = ""
        try:
            released = year_string_to_datetime_string(list(filter(
                lambda x: "Released:" in x.text.strip(), info_tags))[0].text.replace("Released:", "").strip())
        except Exception:
            pass
        status = None
        try:
            status_tag = list(filter(lambda x: "Status:" in x.text.strip(), info_tags))[
                0].select_one("a")
            status = DiscreteCategoryModel(
                id=status_tag["href"].strip().split("/")[-1],
                title=status_tag.text.strip()
            )
        except Exception:
            pass
        other_names = []
        try:
            other_names = list(map(lambda x: x.strip(), list(filter(lambda x: "Other name:" in x.text.strip(), info_tags))[
                0].text.replace("Other name:", "").strip().split("/")))
        except Exception:
            pass
        episodes = []
        episodes_response = requests.get(
            f"{GogoanimeProvider.ajax_url}/load-list-episode?ep_start=0&ep_end=999999&&id={soup.select_one('input#movie_id')['value']}&default_ep=0&alias={soup.select_one('input#alias_anime')['value']}")
        episodes_soup = BeautifulSoup(
            episodes_response.text, features="html.parser")
        for episode in episodes_soup.select("li > a"):
            episodes.append(EpisodeModel(
                id=episode["href"].strip().split("/")[-1],
                number=int(episode.select_one(
                    "div.name").text.strip().replace("EP", "").strip()),
                sub=episode.select_one("div.cate").text.strip() == "SUB",
            ))
        num_of_eps = len(episodes)
        detailed_item = DetailedItemModel(
            id=soup.select_one("input#movie_id")["value"],
            title=title,
            imageUrl=image_url,
            sub=sub,
            numOfEps=num_of_eps,
            type=type,
            synopsis=synopsis,
            genres=genres,
            released=released,
            status=status,
            otherNames=other_names,
            episodes=episodes
        )
        return detailed_item

    def get_item_details(self, id: str) -> dict[str, Any]:
        new_id = item_id_remove_pattern.sub("", id)
        response = requests.get(
            f"{GogoanimeProvider.base_url}/category/{new_id}")
        soup = BeautifulSoup(response.text, features="html.parser")
        item_details = self.parse_item_details_page(soup)
        return item_details.toJson()

    @staticmethod
    def check_url_for_redirect(url: str) -> str:
        response = requests.get(url)
        return response.url
