import requests
from bs4 import BeautifulSoup


class GogoanimeProvider:
    base_url = "https://gogoanime3.net"
    ajax_url = "https://ajax.gogo-load.com/ajax"

    def __init__(self) -> None:
        GogoanimeProvider.base_url = GogoanimeProvider.check_url_for_redirect(
            GogoanimeProvider.base_url)
        GogoanimeProvider.ajax_url = GogoanimeProvider.check_url_for_redirect(
            GogoanimeProvider.ajax_url)

    @staticmethod
    def check_url_for_redirect(url: str, **kwargs) -> str:
        response = requests.get(url)
        return response.url
