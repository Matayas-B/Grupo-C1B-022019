package backend.repository;

import backend.model.CustomerScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerScoreRepository extends CrudRepository<CustomerScore, Long> {
}
