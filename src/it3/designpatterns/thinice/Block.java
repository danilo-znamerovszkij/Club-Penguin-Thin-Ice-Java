package it3.designpatterns.thinice;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Block extends Piece {

    private Image image;

    public Block(int x, int y) {
        super(x, y);
        initWall();
    }
    
    private void initWall() {
        
        ImageIcon icon = new ImageIcon("src/resources/block.png");
        image = icon.getImage();
        setImage(image);
    }
}
