import json


class TweetAttributeFilter:
    def __init__(self, raw_json):
        self.raw_json = raw_json
    # def to_json(self):

    def to_db_json(self):
        new_json = dict()
        new_json['_id'] = str(self.raw_json['id'])

        return new_json

