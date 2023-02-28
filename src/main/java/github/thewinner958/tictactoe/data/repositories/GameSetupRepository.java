package github.thewinner958.tictactoe.data.repositories;

import github.thewinner958.tictactoe.data.entities.GameSetup;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface GameSetupRepository extends CrudRepository<GameSetup, Integer> {
    @Transactional
    @Modifying
    @Query("""
            update GameSetup g set g.player1Char = ?1, g.player2Char = ?2, g.emptyChar = ?3, g.dimension = ?4
            where g.id = ?5""")
    int updateById(Character player1Char, Character player2Char, Character emptyChar, Long dimension, Integer id);
}