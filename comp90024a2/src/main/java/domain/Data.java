package domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private String type = "FeatureCollection";
    private List<Feature> features;
}
