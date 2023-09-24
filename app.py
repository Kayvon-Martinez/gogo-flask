from dotenv import dotenv_values
from flask import Flask
from flask_cors import CORS
from flask_json import FlaskJSON, JsonError, json_response

from lib.data.providers.gogoanime import GogoanimeProvider

config = dotenv_values(".env")

app = Flask(__name__)
CORS(app)
json = FlaskJSON(app)

gogoanime_provider = GogoanimeProvider()


@app.route("/")
def index():
    return json_response(message="Welcome to the Gogo-Flask api")


@app.route("/api/home-cats")
def home_categories():
    home_cats_data = gogoanime_provider.get_ajax_categories()
    return json_response(result=home_cats_data)


@app.route("/api/details/<string:item_id>")
def item_details(item_id: str):
    item_details_data = gogoanime_provider.get_item_details(item_id)
    return json_response(result=item_details_data)


@app.route("/api/search/<string:query>")
def search(query: str):
    search_data = gogoanime_provider.get_search_results(query)
    return json_response(result=search_data)


if __name__ == "__main__":
    app.run(host=config["FLASK_SERVER_IP"],
            port=int(config["FLASK_SERVER_PORT"]))
