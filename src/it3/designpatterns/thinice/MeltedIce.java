package it3.designpatterns.thinice;

import java.awt.Image;
import javax.swing.ImageIcon;

public class MeltedIce extends Piece {

    public MeltedIce(int x, int y) {
        super(x, y);
        initArea();
    }

    private void initArea() {

        ImageIcon icon = new ImageIcon("src/resources/water.png");
        Image image = icon.getImage();
        setImage(image);
    }
}
