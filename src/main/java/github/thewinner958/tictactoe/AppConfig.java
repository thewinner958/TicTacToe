package github.thewinner958.tictactoe;

import github.thewinner958.tictactoe.game.Game;
import github.thewinner958.tictactoe.game.GameSetUp;
import github.thewinner958.tictactoe.game.player.PlayerInterface;
import github.thewinner958.tictactoe.game.player.PlayerConsole;
import github.thewinner958.tictactoe.game.player.bot.Bot;
import github.thewinner958.tictactoe.game.player.bot.BotDifficulty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Game startGame(@Qualifier("consolePlayer") PlayerInterface player1, @Qualifier("bot") PlayerInterface player2) {
        Game game = new Game(getGameConfig(), getStartingState());
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        return game;
    }
    @Bean("difficulty")
    public BotDifficulty getDifficulty() {
        return BotDifficulty.HARD;
    }

    @Bean("consolePlayer")
    public PlayerInterface getConsole() {
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
    public PlayerInterface getBot(BotDifficulty difficulty) {
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
