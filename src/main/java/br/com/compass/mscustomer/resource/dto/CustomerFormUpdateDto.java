package br.com.compass.mscustomer.resource.dto;

import br.com.compass.mscustomer.domain.model.enums.Sex;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerFormUpdateDto {

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
    private Boolean active;
}
