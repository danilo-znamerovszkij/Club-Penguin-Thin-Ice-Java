package it3.designpatterns.thinice;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class GUI {


    public GUI(){

    }


    public void buildMenu(Graphics g, Board board) {

        g.setColor(new Color(18, 0, 162));
        g.fillRect(0, 0, 700, 400);

        g.setFont(new Font("Gill Sans", Font.PLAIN, 20));

        //set score

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("src/resources/startScreen.jpg");
        g.drawImage(i, 95, 5, board);
    }

    public void buildLevels(Graphics g, Board board, boolean isCompleted, int score, ArrayList<Piece> world) {

        g.setColor(new Color(143, 198, 250));
        g.fillRect(0, 0, 700, 400);

        for (int i = 0; i < world.size(); i++) {

            Piece item = world.get(i);

            if (item instanceof Player || item instanceof MoneyBag) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, board);
            } else {

                g.drawImage(item.getImage(), item.x(), item.y(), board);
            }

            g.setFont(new Font("Gill Sans", Font.PLAIN, 15));

            //set score
            g.setColor(new Color(155, 16, 19));
            g.drawString("Score: " + score, 460, 20);

            //pause tips
            g.setFont(new Font("Gill Sans", Font.PLAIN, 13));
            g.setColor(new Color(155, 16, 19));
            g.drawString("To pause press P", 460, 350);

            if (isCompleted) {
                g.setFont(new Font("Gill Sans", Font.PLAIN, 25));
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    public void pauseMenu(Graphics g) {

        //buildLevels(g);
        g.setColor(new Color(4, 4, 75));

        g.fillRect(175, 75, 350, 200);

        g.setFont(new Font("Gill Sans", Font.PLAIN, 22));

        //set score
        g.setColor(new Color(255, 255, 255));
        g.drawString("Restart (R)", 205, 125);

        g.drawString("Back to main menu (M)", 205, 180);

    }
}
