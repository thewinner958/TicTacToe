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
    @Transactional
    @Modifying
    @Query("update Player p set p.id = ?1, p.username = ?2, p.email = ?3, p.password = ?4, p.createTime = ?5, p.isBot = ?6")
    void updateIdAndUsernameAndEmailAndPasswordAndCreateTimeAndIsBotBy(@NonNull Integer id, @NonNull String username, @Nullable String email, @NonNull String password, @NonNull Instant createTime, @NonNull Byte isBot);
    Player findByUsername(String username);
}