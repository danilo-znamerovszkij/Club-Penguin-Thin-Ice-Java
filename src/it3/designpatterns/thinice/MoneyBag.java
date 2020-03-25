package it3.designpatterns.thinice;

import java.awt.Image;
import javax.swing.ImageIcon;

public class MoneyBag extends Piece {

    public MoneyBag(int x, int y) {
        super(x, y);
        
        initBaggage();
    }
    
    private void initBaggage() {
        
        ImageIcon icon = new ImageIcon("src/resources/moneyBag.png");
        Image image = icon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
