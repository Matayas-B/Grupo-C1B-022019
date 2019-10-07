package backend.repository;

import backend.model.SupplierUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends CrudRepository<SupplierUser, Long> { }
