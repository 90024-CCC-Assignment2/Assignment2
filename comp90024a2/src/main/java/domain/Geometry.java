package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    private String type = "Point";
    private String coordinates;
}
