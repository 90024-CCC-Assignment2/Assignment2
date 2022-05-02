# BackEnd API

1.Request all tweets by the given country

- Request Path: http://localhost:8080/streamingtwitter/country?country=china

- Request Method: GET

- Paras: country

- Return: 
 
  **If flag is true**
  
  ```json
  {
    "flag": true,
    "data": {
      "type": "geojson",
      "data": {
        "type": "FeatureCollection",
        "features": [
          {
            "type": "china",
            "created_at": "Wed Jul 10 17:47:43 +0000 2019",
            "properties": {
              "content": {
                "text": "RT @Ahlvled_: na burger na pizza, airport chor kar ayega apka jija. https://t.co/1GdKKlDjUh"
              },
              "user": {
                "username": "2463340429",
                "profile_img_url": "http://pbs.twimg.com/profile_images/773885782343970816/IP73NJtB_normal.jpg"
              },
              "attitude_label": 1
            },
            "geometry": {
              "type": "Point"
            }
          }
        ]
      }
    }
  }    
  ``` 

  **If flag is false**

  ```json
  {
    "flag": true,
    "msg": "this is the error msg"
  }
  ```

