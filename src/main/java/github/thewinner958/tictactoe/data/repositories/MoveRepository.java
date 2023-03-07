package github.thewinner958.tictactoe.data.repositories;

import github.thewinner958.tictactoe.data.entities.Move;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoveRepository extends CrudRepository<Move, Integer>{
    @Query("select m from Move m where m.game.id = ?1")
    List<Move> getAllMovesByGameId(Integer id);
    List<Move> findByGame_Id(Integer id);
}