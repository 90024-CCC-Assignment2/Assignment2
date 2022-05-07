package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {
    /**
     *     "_id": "1149012336147144705",
     *     "_rev": "1-0a9becbd6c80cba34d8cc8459a8bc4eb",
     *     "text": "RT @Ahlvled_: na burger na pizza, airport chor kar ayega apka jija. https://t.co/1GdKKlDjUh",
     *     "created_at": "Wed Jul 10 17:47:43 +0000 2019",
     *     "user_name": "2463340429",
     *     "user_avatar": "http://pbs.twimg.com/profile_images/773885782343970816/IP73NJtB_normal.jpg",
     *     "coordinates": null,
     *     "pictures": null,
     *     "country": "China",
     *     "tag": 1
     */
    private String id;
    private String _rev;
    private String text;
    private String created_at;
    private String user_name;
    private String user_avatar;
    private Coordinates coordinates;
    private String pictures;
    private String country;
    private Integer tag;
}
