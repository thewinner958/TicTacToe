package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.Game;
import github.thewinner958.tictactoe.game.GameSetUp;
import github.thewinner958.tictactoe.game.player.Player;
import github.thewinner958.tictactoe.game.player.PlayerConsole;
import github.thewinner958.tictactoe.game.player.bot.Bot;
import github.thewinner958.tictactoe.game.player.bot.BotDifficulty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Game getGame() {
        return new Game(getGameConfig(), getStartingState());
    }
    @Bean("difficulty")
    public BotDifficulty getDifficulty() {
        return BotDifficulty.HARD;
    }

    @Bean
    public Player getConsole() {
        return new PlayerConsole(getPlayerName());
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
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}};
    }
}
