package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Properties {
    private Content content;
    private User user;
    private Integer attitude_label;
}
