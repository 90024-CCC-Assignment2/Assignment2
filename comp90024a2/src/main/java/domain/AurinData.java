package domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AurinData {
    private List<String> label;
    private List<List<Integer>> source;
}
