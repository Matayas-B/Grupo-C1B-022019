package backend.repository;

import backend.model.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPurchaseRepository extends CrudRepository<Purchase, Long> {
}
