package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestedJson<T> {
    private String type;
    private T data;
}
