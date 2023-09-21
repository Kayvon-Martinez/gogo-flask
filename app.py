import logging
import os
import sys
import time

from dotenv import dotenv_values
from flask import Flask
from flask_cors import CORS
from flask_json import FlaskJSON, JsonError, as_json, json_response
from watchdog.events import LoggingEventHandler
from watchdog.observers import Observer

config = dotenv_values(".env")

app = Flask(__name__)
CORS(app)
json = FlaskJSON(app)


@app.route("/")
def index():
    return json_response(message="Welcome to the Gogo-Flask api")


if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO,
                        format='%(asctime)s - %(message)s',
                        datefmt='%Y-%m-%d %H:%M:%S')
    path = sys.argv[1] if len(sys.argv) > 1 else '.'
    event_handler = LoggingEventHandler()
    observer = Observer()
    observer.schedule(event_handler, path, recursive=True)
    observer.start()
    try:
        app.run(host=config["FLASK_SERVER_IP"],
                port=int(config["FLASK_SERVER_PORT"]))
    except KeyboardInterrupt:
        observer.stop()
    observer.join()
