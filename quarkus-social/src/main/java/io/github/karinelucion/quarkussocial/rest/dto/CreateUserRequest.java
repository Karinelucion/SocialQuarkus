package io.github.karinelucion.quarkussocial.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data //Equivalente a @Getter, @Setter, @RequiredArgsConstructor, @ToString e @EqualsAndHashCode
public class CreateUserRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotNull(message = "Age is Required")
    private Integer age;
}
