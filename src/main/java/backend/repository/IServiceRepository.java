package backend.repository;

import backend.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IServiceRepository extends CrudRepository<Service, Long> {

    Optional<Service> findByServiceName(String serviceName);
}
