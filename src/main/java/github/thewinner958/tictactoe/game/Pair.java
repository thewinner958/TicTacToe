package github.thewinner958.tictactoe.game;

import net.jcip.annotations.Immutable;

@Immutable
public record Pair<K, V>(K key, V value) {

}

