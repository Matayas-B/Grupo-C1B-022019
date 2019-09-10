package persistence;

import org.springframework.stereotype.Repository;

import model.Client;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

 //   List<Client> findByNombre(String name);
}
