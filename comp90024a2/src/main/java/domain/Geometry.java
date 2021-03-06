package domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    private String type = "Point";
    private List<Double> coordinates;
}
