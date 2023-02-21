package github.thewinner958.tictactoe.data.repositories;

import github.thewinner958.tictactoe.data.entities.Player;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update Player p set p.email = ?1, p.password = ?2 where p.username = ?3")
    int updateEmailAndPasswordByUsername(String email, String password, String username);
}