package github.thewinner958.tictactoe;

import github.thewinner958.tictactoe.game.GameSetUp;
import github.thewinner958.tictactoe.game.player.Player;
import github.thewinner958.tictactoe.game.player.bot.Bot;
import github.thewinner958.tictactoe.game.player.bot.BotDifficulty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("difficulty")
    public BotDifficulty getDifficulty() {
        return BotDifficulty.HARD;
    }

    @Bean("name")
    public String getPlayerName() {
        return "TheWinner";
    }

    @Bean("game_config")
    public GameSetUp getGameConfig() {
        return new GameSetUp(3, 3);
    }

    @Bean("bot")
    public Player getBot(BotDifficulty difficulty) {
        return new Bot(difficulty);
    }

    @Bean
    public char[][] getStartingState() {
        return new char[][]{
                {'O', '_', '_'},
                {'_', 'X', 'O'},
                {'_', 'O', '_'}};
    }
}
