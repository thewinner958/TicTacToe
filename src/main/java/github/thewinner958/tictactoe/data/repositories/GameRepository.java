package github.thewinner958.tictactoe.data.repositories;

import github.thewinner958.tictactoe.data.entities.Game;
import github.thewinner958.tictactoe.data.entities.Player;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Game findByPlayer1_Username(String username);
    @Transactional
    @Modifying
    @Query("update Game g set g.state = ?1 where g.id = ?2")
    int updateGameState(String state, Integer id);
    @Transactional
    @Modifying
    @Query("update Game g set g.gameStatus = ?1, g.whoWon = ?2 where g.id = ?3")
    int updateGameStatusAndWhoWonById(Boolean gameStatus, Integer whoWon, Integer id);
    @Transactional
    @Modifying
    @Query("update Game g set g.player2 = ?1 where g.id = ?2")
    int updatePlayer2ByGameId(Player player2, Integer id);
}