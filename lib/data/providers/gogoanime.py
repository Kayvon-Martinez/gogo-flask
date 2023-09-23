import requests
from bs4 import BeautifulSoup
from typing import Any

from lib.data.models.base_models import CategoryModel, ItemModel
from lib.utils.values.patterns import style_image_url_pattern


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

    @staticmethod
    def check_url_for_redirect(url: str) -> str:
        response = requests.get(url)
        return response.url
