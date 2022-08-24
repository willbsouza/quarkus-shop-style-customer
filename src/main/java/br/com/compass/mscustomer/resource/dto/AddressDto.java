package br.com.compass.mscustomer.resource.dto;

import br.com.compass.mscustomer.domain.model.Address;
import br.com.compass.mscustomer.domain.model.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {

    private Long id;
    private State state;
    private String city;
    private String district;
    private String street;
    private String number;
    private String complement;
    private String cep;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.district = address.getDistrict();
        this.city = address.getCity();
        this.state = address.getState();
        this.cep = address.getCep();
    }
}
