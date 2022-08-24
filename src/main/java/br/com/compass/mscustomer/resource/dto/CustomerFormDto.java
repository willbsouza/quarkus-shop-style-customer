package br.com.compass.mscustomer.resource.dto;

import br.com.compass.mscustomer.domain.model.enums.Sex;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerFormDto {

    @NotNull
    @Length(min = 3)
    private String firstName;

    @NotNull
    @Length(min = 3)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    @CPF
    private String cpf;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 6)
    private String password;

    @NotNull
    private Boolean active;
}
