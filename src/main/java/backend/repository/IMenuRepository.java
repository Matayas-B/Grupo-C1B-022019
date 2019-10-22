package backend.repository;

import backend.model.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuRepository extends CrudRepository<Menu, Integer> {
}
