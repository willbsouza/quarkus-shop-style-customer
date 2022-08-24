package br.com.compass.mscustomer.domain.model;

import br.com.compass.mscustomer.domain.model.enums.Sex;
import br.com.compass.mscustomer.resource.dto.CustomerFormDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    private String firstName;

    @NotNull @NotEmpty
    private String lastName;

    @Enumerated(EnumType.STRING) @NotNull
    private Sex sex;

    @CPF @NotNull
    private String cpf;

    @NotNull
    private LocalDate birthdate;

    @NotNull @Email @Column(unique = true)
    private String email;

    @NotNull @NotEmpty
    private String password;

    @NotNull
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Address> addresses = new ArrayList<>();

    public Customer(CustomerFormDto customerFormDto) {
        this.firstName = customerFormDto.getFirstName();
        this.lastName = customerFormDto.getLastName();
        this.sex = customerFormDto.getSex();
        this.cpf = customerFormDto.getCpf();
        this.birthdate = customerFormDto.getBirthdate();
        this.email = customerFormDto.getEmail();
        this.password = customerFormDto.getPassword();
        this.active = customerFormDto.getActive();
    }

}
