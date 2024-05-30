package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


public class Connect4TDDSpec {

    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();

        //Se instancia el juego modificado para acceder a la salida de consola
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameStartsTheBoardIsEmpty() {

        String expected = """
                """;

        assertThat(output.toString().replaceAll("\r", "")).isEqualTo(expected);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        assertThatThrownBy(() -> tested.putDiscInColumn(-1)).isInstanceOf(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i <= 7; i++) {
                tested.putDiscInColumn(6);
            }
        });
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        assertThat(tested.putDiscInColumn(0)).isEqualTo(0);
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);

        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |G| | | | | | |
                |R| | | | | | |
                """;

        assertThat(output.toString().replaceAll("\r","")).isEqualTo(expected);
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {

        int discosiniciales = 0;

        for (int i = 0; i < 7; i++) {
            tested.putDiscInColumn(i);
            assertThat(tested.getNumberOfDiscs()).isEqualTo(discosiniciales + 1 + i);
        }
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);

        assertThrows(RuntimeException.class, () -> {
                tested.putDiscInColumn(0);
            }
        );
    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        tested.putDiscInColumn(0);

        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                """;

        assertThat(output.toString().replaceAll("\r","")).isEqualTo(expected);
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsGreen() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);

        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R|G| | | | | |
                """;

        assertThat(output.toString().replaceAll("\r","")).isEqualTo(expected);
    }

    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {
        String currentPlayer = tested.getCurrentPlayer();
        String mensajeOutput = String.format("Player %s turn%n",currentPlayer);

        assertThat(output.toString()).isEqualTo(mensajeOutput);
    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {

    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {

        tested.putDiscInColumn(0);

        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                """;

        assertThat(output.toString().replaceAll("\r","")).isEqualTo(expected);

        tested.putDiscInColumn(1);

        String expected2 = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R|G| | | | | |
                """;

        assertThat(output.toString().replaceAll("\r","")).isEqualTo(expected2);
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);

        System.out.println(tested.getWinner());

       assertTrue(tested.isFinished());
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {

        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);

        assertEquals(tested.getWinner(), "R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);

        assertEquals(tested.getWinner(), "R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);

        assertEquals(tested.getWinner(), "R");
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(5);
        tested.putDiscInColumn(5);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);

        System.out.println(output.toString());
        assertEquals(tested.getWinner(), "R");
    }
}
