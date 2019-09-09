package ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.repository;

import ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findByNombre(String name);
}
