package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagCount {
    private String country;
    //dislike
    private Integer countForZero;
    //like
    private Integer countForOne;
    private Integer totalCount;
}

