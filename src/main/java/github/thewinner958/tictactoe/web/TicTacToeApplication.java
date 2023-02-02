package github.thewinner958.tictactoe.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicTacToeApplication {

    public TicTacToeApplication(Application application) {
        this.application = application;
    }

    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }

    final Application application;

}
