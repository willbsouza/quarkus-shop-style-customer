package br.com.compass.mscustomer.resource.dto;

import br.com.compass.mscustomer.domain.model.Customer;
import br.com.compass.mscustomer.domain.model.enums.Sex;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private LocalDate birthdate;
    private String email;
    private Boolean active;
    private List<AddressDto> addresses = new ArrayList<>();

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.sex = customer.getSex();
        this.birthdate = customer.getBirthdate();
        this.email = customer.getEmail();
        this.active = customer.getActive();
        this.addresses = customer.getAddresses().stream().map(AddressDto::new).collect(Collectors.toList());
    }
}
