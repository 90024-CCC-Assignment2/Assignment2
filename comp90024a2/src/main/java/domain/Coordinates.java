package domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    private String type;
    private List<Double> coordinates;
}
