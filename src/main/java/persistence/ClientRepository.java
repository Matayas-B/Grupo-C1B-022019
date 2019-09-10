package persistence;

import org.springframework.stereotype.Repository;

import model.CustomerUser;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ClientRepository extends CrudRepository<CustomerUser, Long> {

 //   List<CustomerUser> findByNombre(String name);
}
