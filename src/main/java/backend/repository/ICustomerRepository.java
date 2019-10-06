package backend.repository;

import org.springframework.stereotype.Repository;

import backend.model.CustomerUser;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface ICustomerRepository extends CrudRepository<CustomerUser, Long> { }
