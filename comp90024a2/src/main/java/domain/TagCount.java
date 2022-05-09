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
    private Integer totalCount;
    //like
    private Integer countForOne;
    //dislike
    private Integer countForZero;

}

