package br.com.compass.mscustomer.repository;

import br.com.compass.mscustomer.domain.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Optional<Customer> findByEmail(String email){
        Map<String, Object> params = Parameters.with("email", email).map();
        return find("email = :email", params).firstResultOptional();
    }
}
