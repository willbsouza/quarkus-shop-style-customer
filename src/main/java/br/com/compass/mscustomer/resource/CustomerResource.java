package br.com.compass.mscustomer.resource;

import br.com.compass.mscustomer.domain.model.Customer;
import br.com.compass.mscustomer.repository.CustomerRepository;
import br.com.compass.mscustomer.resource.dto.*;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/v1/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private CustomerRepository customerRepository;

    @Inject
    public CustomerResource(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        Customer customer = customerRepository.findById(id);
        if(customer == null){
            return Response.status(404).build();
        }
        return Response.ok(new CustomerDto(customer)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid CustomerFormDto customerFormDto){
        Customer customer = new Customer(customerFormDto);
        customerRepository.persist(customer);
        return Response.status(201).entity(new CustomerDto(customer)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateById(@PathParam("id") Long id, @Valid CustomerFormUpdateDto customerFormUpdateDto){
        Customer customer = customerRepository.findById(id);
        if(customer == null){
            return Response.status(404).build();
        }
        customer.setFirstName(customerFormUpdateDto.getFirstName());
        customer.setLastName(customerFormUpdateDto.getLastName());
        customer.setSex(customerFormUpdateDto.getSex());
        customer.setCpf(customerFormUpdateDto.getCpf());
        customer.setBirthdate(customerFormUpdateDto.getBirthdate());
        customer.setActive(customerFormUpdateDto.getActive());

        return Response.status(200).entity(new CustomerDto(customer)).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid CustomerLoginDto customerLoginDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerLoginDto.getEmail());
        if(customerOptional.isPresent()
                && customerOptional.get().getPassword().equals(customerLoginDto.getPassword())) {
            return Response.status(200).entity(new CustomerDto(customerOptional.get())).build();
        }
        return Response.status(401).entity("Incorrect email and/or password!").build();
    }

    @PUT
    @Transactional
    @Path("/{id}/password")
    public Response changePassword(@PathParam("id") Long id, @Valid CustomerChangePasswordDto passwordDto) {
        Customer customer = customerRepository.findById(id);
        if(verificationPassword(customer, passwordDto)) {
            customer.setPassword(passwordDto.getNewPassword());
            return Response.status(200).entity(new CustomerDto(customer)).build();
        }
        return Response.status(400).entity("Incorrect data. Check the email information, cpf and the new password and password confirmation fields must be the same.").build();
    }

    private Boolean verificationPassword(Customer customer, CustomerChangePasswordDto passwordDto) {
        if(passwordDto.getOldPassword().equals(customer.getPassword())
                && passwordDto.getCpf().equals(customer.getCpf())
                && passwordDto.getEmail().equals(customer.getEmail())
                && passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirmation())) {
            return true;
        }
        return false;
    }
}
