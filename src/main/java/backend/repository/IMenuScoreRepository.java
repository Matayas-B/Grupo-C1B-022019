package backend.repository;

import backend.model.MenuScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuScoreRepository extends CrudRepository<MenuScore, Long> {
}
