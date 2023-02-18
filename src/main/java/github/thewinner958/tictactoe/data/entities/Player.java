package github.thewinner958.tictactoe.data.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "Player")
@Table(name = "player", schema = "tictactoe", indexes = {
        @Index(name = "email_UNIQUE", columnList = "email", unique = true)
})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "isBot", nullable = false)
    private Byte isBot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Byte getIsBot() {
        return isBot;
    }

    public void setIsBot(Byte isBot) {
        this.isBot = isBot;
    }

}