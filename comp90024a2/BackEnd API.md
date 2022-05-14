# BackEnd API

1.Request all tweets by the given country

- Request
  Path: http://172.26.133.175:8080/comp90024a2/tweet/search-tweet?dbType=restful&country=China&period=1w

- Request Method: GET

- Paras: dbType(historical,restful,streaming), country(Australia,China...),period(1w 1m 3m 6m 1y 2y
  3y)

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

2.Request up to 5 pictures by the given country

- Request Path: http://172.26.133.175:8080/comp90024a2/tweet/search-pic?
  dbType=restful&country=China&period=1w &period=1w

- Request Method: GET

- Paras: dbType(historical,restful,streaming), country(Australia,China...),period(1w 1m 3m 6m 1y 2y
  3y)

- Return:
  ```json
  {
      "flag": true, 
      "data": {
          "type": "images", 
          "data": [
              "http://pbs.twimg.com/media/FSJFyINUUAAZB5q.jpg", 
              "http://pbs.twimg.com/media/FSFldZqXoAAje5t.jpg", 
              "http://pbs.twimg.com/media/FSJfkKHaAAAD0yX.jpg", 
              "http://pbs.twimg.com/media/FSJie4-XIAEPad0.jpg", 
              "http://pbs.twimg.com/media/FSJie4-XIAEPad0.jpg"
          ]
      }
  }
  ```

3.Count food reference by db

- Request Path: http://172.26.133.175:8080/comp90024a2/tweet/count-all-tags?dbType=restful

- Request Method: GET

- Paras: dbType(historical,restful,streaming),

- Return:
  ```json
  {
      "flag": true, 
      "data": {
          "type": "Tweet Count", 
          "data": [
              {
                  "country": "China", 
                  "totalCount": 3952, 
                  "countForOne": 2933, 
                  "countForZero": 1019
              }, 
              {
                  "country": "Thai", 
                  "totalCount": 32, 
                  "countForOne": 28, 
                  "countForZero": 4
              }, 
              {
                  "country": "Korea", 
                  "totalCount": 69, 
                  "countForOne": 51, 
                  "countForZero": 18
              }, 
              {
                  "country": "Japan", 
                  "totalCount": 1725, 
                  "countForOne": 1268, 
                  "countForZero": 457
              }, 
              {
                  "country": "Mexican", 
                  "totalCount": 468, 
                  "countForOne": 381, 
                  "countForZero": 87
              }, 
              {
                  "country": "India", 
                  "totalCount": 964, 
                  "countForOne": 765, 
                  "countForZero": 199
              }, 
              {
                  "country": "Italy", 
                  "totalCount": 1447, 
                  "countForOne": 1163, 
                  "countForZero": 284
              }, 
              {
                  "country": "America", 
                  "totalCount": 2303, 
                  "countForOne": 1834, 
                  "countForZero": 469
              }, 
              {
                  "country": "Spain", 
                  "totalCount": 68, 
                  "countForOne": 61, 
                  "countForZero": 7
              }, 
              {
                  "country": "Turkey", 
                  "totalCount": 37, 
                  "countForOne": 34, 
                  "countForZero": 3
              }, 
              {
                  "country": "Greece", 
                  "totalCount": 0, 
                  "countForOne": 0, 
                  "countForZero": 0
              }, 
              {
                  "country": "Pakistan", 
                  "totalCount": 72, 
                  "countForOne": 68, 
                  "countForZero": 4
              }, 
              {
                  "country": "Ukraine", 
                  "totalCount": 4, 
                  "countForOne": 2, 
                  "countForZero": 2
              }, 
              {
                  "country": "Australia", 
                  "totalCount": 107, 
                  "countForOne": 87, 
                  "countForZero": 20
              }
          ]
      }
  }
  ```

4.Count Aurin restaurant number

- Request Path: http://localhost:8080/aurin/count-all-country

- Request Method: GET

- Paras: none

- Return:
  ```json
   {
      "flag": true, 
      "data": {
          "type": "Restaurant Number", 
          "data": {
              "label": [
                  "America and Mexican", 
                  "Australia", 
                  "Eastern Asia", 
                  "Europe", 
                  "Southern Asia"
              ], 
              "source": [
                  [
                      30, 
                      34, 
                      42, 
                      48, 
                      49, 
                      49, 
                      56, 
                      68, 
                      73, 
                      75, 
                      80, 
                      84, 
                      101, 
                      104, 
                      107, 
                      109
                  ], 
                  [
                      562, 
                      591, 
                      638, 
                      677, 
                      716, 
                      762, 
                      769, 
                      789, 
                      821, 
                      810, 
                      808, 
                      826, 
                      815, 
                      799, 
                      826, 
                      802
                  ], 
                  [
                      83, 
                      94, 
                      115, 
                      129, 
                      144, 
                      154, 
                      161, 
                      198, 
                      217, 
                      236, 
                      236, 
                      249, 
                      252, 
                      247, 
                      253, 
                      245
                  ], 
                  [
                      40, 
                      42, 
                      38, 
                      47, 
                      57, 
                      70, 
                      72, 
                      76, 
                      82, 
                      104, 
                      120, 
                      117, 
                      135, 
                      140, 
                      147, 
                      142
                  ], 
                  [
                      20, 
                      22, 
                      24, 
                      30, 
                      37, 
                      50, 
                      65, 
                      74, 
                      72, 
                      75, 
                      73, 
                      70, 
                      73, 
                      73, 
                      74, 
                      75
                  ]
              ]
          }
      }
  }
  ```

