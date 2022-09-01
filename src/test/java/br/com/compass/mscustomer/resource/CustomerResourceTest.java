package br.com.compass.mscustomer.resource;

import br.com.compass.mscustomer.domain.model.Customer;
import br.com.compass.mscustomer.domain.model.enums.Sex;
import br.com.compass.mscustomer.repository.CustomerRepository;
import br.com.compass.mscustomer.resource.dto.*;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CustomerResourceTest {

    @InjectMock
    private CustomerRepository customerRepository;

    @Inject
    private CustomerResource customerResource;
    private Customer customer;
    private CustomerDto customerDto;
    private CustomerFormDto customerFormDto;
    private CustomerFormUpdateDto customerFormUpdateDto;
    private CustomerLoginDto customerLoginDto;
    private CustomerChangePasswordDto customerChangePasswordDto;
    private CustomerChangePasswordDto customerChangePasswordErrorDto;
    private Optional<Customer> optCustomer;

    private static final LocalDate BIRTHDATE = LocalDate.now();

    @BeforeEach
    void setUp() {
        startMocks();
    }

    @Test
    @DisplayName("Should return an CustomerDto when findById")
    void findById() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customer);
        Response response = customerResource.findById(1L);
        CustomerDto entity = (CustomerDto) response.getEntity();

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(CustomerDto.class, entity.getClass());
        assertEquals(1L, entity.getId());
        assertEquals("Willams", entity.getFirstName());
        assertEquals("Souza", entity.getLastName());
        assertEquals("willams@email.com", entity.getEmail());
        assertEquals(BIRTHDATE, entity.getBirthdate());
        assertEquals(Sex.MASCULINO, entity.getSex());
        assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Should return error message when findById")
    void findByIdError() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(null);
        Response response = customerResource.findById(2L);

        assertEquals("Customer id: 2 not found.", response.getEntity());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should create an Customer when create and return CustomerDto")
    void create() {
        Mockito.doNothing().when(customerRepository).persist(customer);
        Response response = customerResource.create(customerFormDto);
        CustomerDto entity = (CustomerDto) response.getEntity();
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(CustomerDto.class, entity.getClass());
        assertEquals("Willams", entity.getFirstName());
        assertEquals("Souza", entity.getLastName());
        assertEquals("willams@email.com", entity.getEmail());
        assertEquals(BIRTHDATE, entity.getBirthdate());
        assertEquals(Sex.MASCULINO, entity.getSex());
        assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Should update an Customer when updateById and return CustomerDto")
    void updateById() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customer);
        Response response = customerResource.updateById(1L, customerFormUpdateDto);
        CustomerDto entity = (CustomerDto) response.getEntity();

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(CustomerDto.class, entity.getClass());
        assertEquals(1L, entity.getId());
        assertEquals("Willams", entity.getFirstName());
        assertEquals("Lima", entity.getLastName());
        assertEquals("willams@email.com", entity.getEmail());
        assertEquals(BIRTHDATE, entity.getBirthdate());
        assertEquals(Sex.MASCULINO, entity.getSex());
        assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Should return error message when updateById")
    void updateByIdError() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(null);
        Response response = customerResource.updateById(2L, customerFormUpdateDto);

        assertEquals("Customer id: 2 not found.", response.getEntity());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("Should return an CustomerDto and status OK when login")
    void login() {
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(optCustomer);
        Response response = customerResource.login(customerLoginDto);
        CustomerDto entity = (CustomerDto) response.getEntity();

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(CustomerDto.class, entity.getClass());
        assertEquals(1L, entity.getId());
        assertEquals("Willams", entity.getFirstName());
        assertEquals("Souza", entity.getLastName());
        assertEquals("willams@email.com", entity.getEmail());
        assertEquals(BIRTHDATE, entity.getBirthdate());
        assertEquals(Sex.MASCULINO, entity.getSex());
        assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Should return an CustomerDto and change password when changePassword")
    void changePassword() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customer);
        Response response = customerResource.changePassword(1L, customerChangePasswordDto);
        CustomerDto entity = (CustomerDto) response.getEntity();

        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(CustomerDto.class, entity.getClass());
        assertEquals(1L, entity.getId());
        assertEquals("Willams", entity.getFirstName());
        assertEquals("Souza", entity.getLastName());
        assertEquals("willams@email.com", entity.getEmail());
        assertEquals(BIRTHDATE, entity.getBirthdate());
        assertEquals(Sex.MASCULINO, entity.getSex());
        assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Should return an error message when changePassword")
    void changePasswordError() {
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customer);
        Response response = customerResource.changePassword(1L, customerChangePasswordErrorDto);
        String entity = (String) response.getEntity();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Incorrect data. Check the email information, cpf and the new password and password confirmation fields must be the same.", entity);
    }

    @Test
    @DisplayName("Should return an CustomerDto when getCustomer")
    void getCustomer(){
        Mockito.when(customerRepository.findById(Mockito.anyLong())).thenReturn(customer);
        Customer response = customerResource.getCustomer(1L);

        assertNotNull(response);
        assertEquals(Customer.class, response.getClass());
        assertEquals(1L, response.getId());
        assertEquals("Willams", response.getFirstName());
        assertEquals("Souza", response.getLastName());
        assertEquals("willams@email.com", response.getEmail());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(Sex.MASCULINO, response.getSex());
        assertEquals(true, response.getActive());
    }


    private void startMocks(){
        customerFormDto = new CustomerFormDto();

        customerFormDto.setActive(true);
        customerFormDto.setCpf("01234567890");
        customerFormDto.setBirthdate(BIRTHDATE);
        customerFormDto.setSex(Sex.MASCULINO);
        customerFormDto.setPassword("12345678");
        customerFormDto.setLastName("Souza");
        customerFormDto.setFirstName("Willams");
        customerFormDto.setEmail("willams@email.com");

        customerFormUpdateDto = new CustomerFormUpdateDto();
        customerFormUpdateDto.setFirstName("Willams");
        customerFormUpdateDto.setLastName("Lima");
        customerFormUpdateDto.setSex(Sex.MASCULINO);
        customerFormUpdateDto.setCpf("60456467033");
        customerFormUpdateDto.setBirthdate(BIRTHDATE);
        customerFormUpdateDto.setActive(true);

        customerLoginDto = new CustomerLoginDto();
        customerLoginDto.setEmail("willams@email.com");
        customerLoginDto.setPassword("12345678");

        customerChangePasswordDto = new CustomerChangePasswordDto();
        customerChangePasswordDto.setEmail("willams@email.com");
        customerChangePasswordDto.setCpf("01234567890");
        customerChangePasswordDto.setOldPassword("12345678");
        customerChangePasswordDto.setNewPassword("1234567890");
        customerChangePasswordDto.setNewPasswordConfirmation("1234567890");

        customerChangePasswordErrorDto = new CustomerChangePasswordDto();
        customerChangePasswordErrorDto.setEmail("willams@email.com");
        customerChangePasswordErrorDto.setCpf("01234567890");
        customerChangePasswordErrorDto.setOldPassword("123456789");
        customerChangePasswordErrorDto.setNewPassword("1234567890");
        customerChangePasswordErrorDto.setNewPasswordConfirmation("1234567890");

        customer = new Customer(customerFormDto);
        customer.setId(1L);
        customerDto = new CustomerDto(customer);

        optCustomer = Optional.of(customer);
    }
}