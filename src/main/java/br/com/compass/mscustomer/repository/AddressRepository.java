package br.com.compass.mscustomer.repository;

import br.com.compass.mscustomer.domain.model.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {
}
