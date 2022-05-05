import json

cloud_path = "/home/ubuntu/aurin"
local_path = "/Users/gaohaoyu/aurin"


with open(local_path, 'r') as f:
    file = json.load(f)
    restuarant_list = file["feature"]
    for restuarant in restuarant_list:
        pass

