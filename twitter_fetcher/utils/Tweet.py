import json
import random


class TweetAttributeFilter:

    def to_db_json(self, raw_json, country, tag, Areas):
        new_json = dict()
        new_json['_id'] = str(raw_json['_id'])
        new_json['text'] = str(raw_json['text'])
        new_json['created_at'] = str(raw_json['created_at'])
        new_json['user_name'] = str(raw_json['user']['id'])
        new_json['user_avatar'] = str(raw_json['user']['profile_image_url'])
        if raw_json['coordinates'] is not None:
            new_json['coordinates'] = raw_json['coordinates']
        else:
            # TODO fake coordinate, remember to delete after using it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ############################################################
            area = random.choice(Areas)
            coords_bound = area['geo']
            longi = random.random() * (int(coords_bound['up']) - int(coords_bound['down'])) + int(coords_bound['down'])
            lat = random.random() * (int(coords_bound['right']) - int(coords_bound['left'])) + int(coords_bound['left'])
            new_json['coordinates'] = {"type": "Point", "coordinates": (lat, longi)}
            ############################################################
        try:
            new_json['pictures'] = [media['media_url'] for media in raw_json['extended_tweet']['extended_entities']['media']]
        except:
            new_json['pictures'] = None
        new_json['country'] = country
        new_json['tag'] = tag

        return new_json

