package it3.designpatterns.thinice;

import java.awt.Image;
import javax.swing.ImageIcon;

public class NextLevelDoor extends Piece {

    public NextLevelDoor(int x, int y) {
        super(x, y);
        
        initArea();
    }
    
    private void initArea() {

        ImageIcon icon = new ImageIcon("src/resources/nextLevelDoor.png");
        Image image = icon.getImage();
        setImage(image);
    }
}
