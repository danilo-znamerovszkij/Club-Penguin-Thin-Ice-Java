package it3.designpatterns.thinice;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    private final int OFFSET = 60;

    public Game() {

        initUI();
    }

    private void initUI() {
        
        Board board = new Board();
        add(board);

        setTitle("Thin Ice");
        
        setSize(board.getBoardWidth() + OFFSET,
                board.getBoardHeight() + 2 * OFFSET);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            
            Game game = new Game();

            game.setVisible(true);
        });

    }
}
