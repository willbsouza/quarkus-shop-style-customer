package br.com.compass.mscustomer.resource;

import br.com.compass.mscustomer.domain.model.Address;
import br.com.compass.mscustomer.domain.model.Customer;
import br.com.compass.mscustomer.repository.AddressRepository;
import br.com.compass.mscustomer.repository.CustomerRepository;
import br.com.compass.mscustomer.resource.dto.AddressDto;
import br.com.compass.mscustomer.resource.dto.AddressFormDto;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/addresses")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;

    @Inject
    public AddressResource(AddressRepository addressRepository, CustomerRepository customerRepository){
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @POST
    public Response create(@Valid AddressFormDto addressFormDto){
        Customer customer = customerRepository.findById(addressFormDto.getCustomerId());
        if(customer == null){
            return Response.status(404).entity("Customer id: " + addressFormDto.getCustomerId() + " not found.").build();
        }
        Address address = new Address(addressFormDto, customer);
        addressRepository.persist(address);

        return Response.status(201).entity(new AddressDto(address)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, @Valid AddressFormDto addressFormDto){
        Address address = addressRepository.findById(id);
        if(address == null){
            return Response.status(404).entity("Address id: " + id + " not found.").build();
        }
        address.setStreet(addressFormDto.getStreet());
        address.setNumber(addressFormDto.getNumber());
        address.setComplement(addressFormDto.getComplement());
        address.setDistrict(addressFormDto.getDistrict());
        address.setCity(addressFormDto.getCity());
        address.setState(addressFormDto.getState());
        address.setCep(addressFormDto.getCep());

        return Response.status(200).entity(new AddressDto(address)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        Address address = addressRepository.findById(id);
        if (address == null) {
            return Response.status(404).entity("Address id: " + id + " not found.").build();
        }
        addressRepository.deleteById(id);

        return Response.noContent().build();
    }
}
