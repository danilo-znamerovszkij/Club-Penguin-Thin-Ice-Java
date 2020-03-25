package it3.designpatterns.thinice;

import java.awt.Image;

public abstract class Piece {

    private final int SPACE = 20;

    private int x;
    private int y;
    private Image image;

    public Piece(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    public boolean isLeftCollision(Piece piece) {
        
        return x() - SPACE == piece.x() && y() == piece.y();
    }

    public boolean isRightCollision(Piece piece) {
        
        return x() + SPACE == piece.x() && y() == piece.y();
    }

    public boolean isTopCollision(Piece piece) {
        
        return y() - SPACE == piece.y() && x() == piece.x();
    }

    public boolean isBottomCollision(Piece piece) {
        
        return y() + SPACE == piece.y() && x() == piece.x();
    }
}
