import json
import random
from datetime import datetime


class TweetAttributeFilter:

    def to_db_json(self, raw_json, country, tag, Areas):
        new_json = dict()
        new_json['_id'] = str(raw_json['_id'])
        new_json['text'] = str(raw_json['text'])
        try:
            new_json['created_at'] = str(datetime.strptime(raw_json['created_at'], '%a %b %d %H:%M:%S %z %Y'))
        except:
            new_json['created_at'] = str(datetime.strptime(raw_json['created_at'], ''))
        # new_json['created_at'] = datetime.strptime(raw_json['created_at'], ""))
        new_json['user_name'] = str(raw_json['user']['screen_name'])
        new_json['user_avatar'] = str(raw_json['user']['profile_image_url'])

        if raw_json['coordinates'] is not None:
            new_json['coordinates'] = raw_json['coordinates']
        elif Areas is not None:
            # TODO fake coordinate, remember to delete after using it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ############################################################
            area = random.choice(Areas)
            coords_bound = area['geo']
            longi = random.random() * (float(coords_bound['up']) - float(coords_bound['down'])) + float(coords_bound['down'])
            lat = random.random() * (float(coords_bound['right']) - float(coords_bound['left'])) + float(coords_bound['left'])
            new_json['coordinates'] = {"type": "Point", "coordinates": (lat, longi)}
            ############################################################

        try:
            new_json['pictures'] = raw_json['extended_tweet']['extended_entities']['media'][0]
        except:
            try:
                print(raw_json.keys())
                new_json['pictures'] = raw_json['entities']['media'][0]['media_url']
            except:
                new_json['pictures'] = ""
        new_json['country'] = country
        new_json['tag'] = tag

        return new_json

