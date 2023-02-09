package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.Game;
import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Lazy
public class Application {

    private final Game game;

    @Autowired
    protected Application(Game game) {
        this.game = game;
    }

    protected Application() {
        this.game = null;
    }

    @PostConstruct
    public void run() throws IllegalMoveException {
        if (this.game != null) {
            game.play(-1);
        }
    }
}
