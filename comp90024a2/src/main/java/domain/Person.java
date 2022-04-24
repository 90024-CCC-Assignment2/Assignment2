package domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String _id;
    private String _rev;
    private String name;
    private int age;
}
