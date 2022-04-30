package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Feature {
    private String id;
    private String type;
    private String created_at;
    private Properties properties;
    private Geometry geometry;
}
