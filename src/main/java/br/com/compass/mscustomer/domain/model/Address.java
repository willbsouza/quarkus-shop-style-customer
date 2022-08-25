package br.com.compass.mscustomer.domain.model;

import br.com.compass.mscustomer.domain.model.enums.State;
import br.com.compass.mscustomer.resource.dto.AddressFormDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @NotNull @NotEmpty
    private String city;

    @NotNull @NotEmpty
    private String district;

    @NotNull @NotEmpty
    private String street;

    @NotNull @NotEmpty
    private String number;

    private String complement;

    @NotNull @NotEmpty
    private String cep;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public Address(AddressFormDto addressFormDto, Customer customer) {
        this.street = addressFormDto.getStreet();
        this.number = addressFormDto.getNumber();
        this.complement = addressFormDto.getComplement();
        this.district = addressFormDto.getDistrict();
        this.city = addressFormDto.getCity();
        this.state = addressFormDto.getState();
        this.cep = addressFormDto.getCep();
        this.customer = customer;
    }
}
