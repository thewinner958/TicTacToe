package github.thewinner958.tictactoe.data.repositories;

import github.thewinner958.tictactoe.data.entities.Move;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoveRepository extends CrudRepository<Move, Integer>{
    List<Move> findByGame_Id(Integer id);
}