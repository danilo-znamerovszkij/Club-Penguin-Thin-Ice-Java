package it3.designpatterns.thinice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel {

    private final int OFFSET = 30;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private final int IN_MAIN_MENU = 1;
    private final int IN_GAME = 2;
    private final int ON_PAUSE = 3;

    private StateObserver stateObserver;
    private ArrayList<Block> blocks;
    private ArrayList<MoneyBag> bags;
    private ArrayList<NextLevelDoor> nextLevelDoors;
    private ArrayList<MeltedIce> meltedBlocks;

    private Player penguin;
    private int w = 0;
    private int h = 0;

    private int score = 0;

    private boolean isCompleted = false;

    private String currentLevel;

    private String level3
            = "    #########################\n"
            + "    ##############     $ #\n"
            + "    ##$          #   #     #\n"
            + "  ####      ####   ###   ####$##\n"
            + "  ##  $ $ #               ####  \n"
            + "#### # ## #   #########   ########\n"
            + "##   # ## #####  #    @#######    ###\n"
            + "###   $  $        ########       ####\n"
            + "###  ### ##          $           @ #\n"
            + "##   ##     ############################\n"
            + "# . ########\n"
            + "#########\n";

    private String level2
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   #################\n"
            + "##   # ## #####  #    @#####\n"
            + "###  ## $  $        ########\n"
            + "###  ### ### # ##  #\n"
            + "##   ##     #########\n"
            + "# . ########\n"
            + "#  #######\n";

    private String level1
            = "    ##################\n"
            + "    ##.       $     @#\n"
            + "    ##################\n";


    public Board() {

        initBoard();
    }

    private void initBoard() {

        currentLevel = level1;
        stateObserver = new StateObserver();

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld(currentLevel);
    }

    public int getBoardWidth() {

        return this.w;
    }

    public int getBoardHeight() {

        return this.h;
    }

    private void initWorld(String level) {

        blocks = new ArrayList<>();
        bags = new ArrayList<>();
        nextLevelDoors = new ArrayList<>();
        meltedBlocks = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Block block;
        MoneyBag b;
        NextLevelDoor nextLevel;

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x;
                    }

                    x = OFFSET;
                    break;

                case '#':
                    block = new Block(x, y);
                    blocks.add(block);
                    x += SPACE;
                    break;

                case '$':
                    b = new MoneyBag(x, y);
                    bags.add(b);
                    x += SPACE;
                    break;

                case '.':
                    nextLevel = new NextLevelDoor(x, y);
                    nextLevelDoors.add(nextLevel);
                    x += SPACE;
                    break;

                case '@':
                    penguin = new Player(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }

            h = y;
        }
    }

    private void buildMenu(Graphics g) {

        g.setColor(new Color(18, 0, 162));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setFont(new Font("Gill Sans", Font.PLAIN, 20));

        //set score

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("src/resources/startScreen.jpg");
        g.drawImage(i, 95, 0, this);

        g.setColor(new Color(255, 255, 255));
        g.drawString("Start by pressing any key", this.getWidth() - 460, this.getHeight() - 10);

    }

    private void buildWorld(Graphics g) {

        g.setColor(new Color(143, 198, 250));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());


        ArrayList<Piece> world = new ArrayList<>();

        world.addAll(blocks);
        world.addAll(nextLevelDoors);
        world.addAll(bags);
        world.addAll(meltedBlocks);

        world.add(penguin);

        for (int i = 0; i < world.size(); i++) {

            Piece item = world.get(i);

            if (item instanceof Player || item instanceof MoneyBag) {

                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {

                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            g.setFont(new Font("Gill Sans", Font.PLAIN, 15));

            //set score
            g.setColor(new Color(155, 16, 19));
            g.drawString("Score: " + score, 460, 20);

            //restart tips
            g.setFont(new Font("Gill Sans", Font.PLAIN, 13));
            g.setColor(new Color(155, 16, 19));
            g.drawString("To restart press R", 460, 350);

            if (isCompleted) {
                g.setFont(new Font("Gill Sans", Font.PLAIN, 25));
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    private void pauseMenu(Graphics g) {

        buildWorld(g);
        g.setColor(new Color(4, 4, 75));

        g.fillRect(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2, this.getHeight() / 2);

        g.setFont(new Font("Gill Sans", Font.PLAIN, 22));

        //set score
        g.setColor(new Color(255, 255, 255));
        g.drawString("Restart (R)", this.getWidth() / 4 + 30, this.getHeight() / 4 + 50);

        g.drawString("Back to main menu (M)", this.getWidth() / 4 + 30, this.getHeight() / 4 + 120);

        if (isCompleted) {
            g.setFont(new Font("Gill Sans", Font.PLAIN, 25));
            g.setColor(new Color(0, 0, 0));
            g.drawString("Completed", 25, 20);
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        switch (stateObserver.getCurrentState()) {
            case 1:
                buildMenu(g);
                break;
            case 2:
                buildWorld(g);
                break;
            case 3:
                pauseMenu(g);
                break;

        }

    }

    private boolean checkWaterCollision(Piece piece, int type) {

        switch (type) {

            case LEFT_COLLISION:

                for (int i = 0; i < meltedBlocks.size(); i++) {

                    MeltedIce meltedBlock = meltedBlocks.get(i);

                    if (piece.isLeftCollision(meltedBlock)) {

                        return true;
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < meltedBlocks.size(); i++) {

                    MeltedIce meltedBlock = meltedBlocks.get(i);

                    if (piece.isRightCollision(meltedBlock)) {
                        return true;
                    }
                }

                return false;

            case TOP_COLLISION:

                for (int i = 0; i < meltedBlocks.size(); i++) {

                    MeltedIce meltedBlock = meltedBlocks.get(i);

                    if (piece.isTopCollision(meltedBlock)) {

                        return true;
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < meltedBlocks.size(); i++) {

                    MeltedIce meltedBlock = meltedBlocks.get(i);

                    if (piece.isBottomCollision(meltedBlock)) {

                        return true;
                    }
                }

                return false;

            default:
                break;
        }

        return false;
    }

    private boolean lightAreaOnFire(Piece piece, int type) {
        MeltedIce a = new MeltedIce(piece.x(), piece.y());
        meltedBlocks.add(a);
        return true;
    }

    private boolean checkWallCollision(Piece piece, int type) {

        switch (type) {

            case LEFT_COLLISION:

                for (int i = 0; i < blocks.size(); i++) {

                    Block block = blocks.get(i);

                    if (piece.isLeftCollision(block)) {

                        return true;
                    }
                }

                return false;

            case RIGHT_COLLISION:

                for (int i = 0; i < blocks.size(); i++) {

                    Block block = blocks.get(i);

                    if (piece.isRightCollision(block)) {
                        return true;
                    }
                }

                return false;

            case TOP_COLLISION:

                for (int i = 0; i < blocks.size(); i++) {

                    Block block = blocks.get(i);

                    if (piece.isTopCollision(block)) {

                        return true;
                    }
                }

                return false;

            case BOTTOM_COLLISION:

                for (int i = 0; i < blocks.size(); i++) {

                    Block block = blocks.get(i);

                    if (piece.isBottomCollision(block)) {

                        return true;
                    }
                }

                return false;

            default:
                break;
        }

        return false;
    }

    private boolean checkBagCollision() {

        for (int i = 0; i < bags.size(); i++) {

            MoneyBag bag = bags.get(i);

            if (penguin.x() == bag.x() && penguin.y() == bag.y()) {

                bag.setImage(null);
                score += 100;
                isCompleted();
            }
        }

        return false;

    }

    public void isCompleted() {

        NextLevelDoor nextLevelDoor = nextLevelDoors.get(0);
        if (penguin.x() == nextLevelDoor.x() && penguin.y() == nextLevelDoor.y()) {

            isCompleted = true;
            if (currentLevel.equals(level1)) {
                currentLevel = level2;

            } else if (currentLevel.equals(level2)) {
                currentLevel = level3;
            } else if (currentLevel.equals(level3)) {
                currentLevel = level1;
                stateObserver.setState(IN_MAIN_MENU);
            }

            //initiate the next level
            restartLevel();
            repaint();
        }

    }

    private void restartLevel() {

        nextLevelDoors.clear();
        bags.clear();
        blocks.clear();
        score = 0;

        initWorld(currentLevel);

        if (isCompleted) {
            isCompleted = false;
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (stateObserver.getCurrentState() == IN_GAME)
                switch (key) {

                    case KeyEvent.VK_LEFT:

                        if (checkWallCollision(penguin,
                                LEFT_COLLISION)) {
                            return;
                        }

                        if (checkWaterCollision(penguin, LEFT_COLLISION)) {
                            return;
                        }

                        lightAreaOnFire(penguin, LEFT_COLLISION);

                        penguin.move(-SPACE, 0);

                        if (checkBagCollision()) {
                            return;
                        }

                        isCompleted();

                        break;

                    case KeyEvent.VK_RIGHT:

                        if (checkWallCollision(penguin, RIGHT_COLLISION)) {
                            return;
                        }

                        if (checkWaterCollision(penguin, RIGHT_COLLISION)) {
                            return;
                        }

                        lightAreaOnFire(penguin, RIGHT_COLLISION);

                        penguin.move(SPACE, 0);

                        if (checkBagCollision()) {
                            return;
                        }

                        isCompleted();

                        break;

                    case KeyEvent.VK_UP:

                        if (checkWallCollision(penguin, TOP_COLLISION)) {
                            return;
                        }

                        if (checkWaterCollision(penguin, TOP_COLLISION)) {
                            return;
                        }

                        lightAreaOnFire(penguin, TOP_COLLISION);

                        penguin.move(0, -SPACE);

                        if (checkBagCollision()) {
                            return;
                        }

                        isCompleted();

                        break;

                    case KeyEvent.VK_DOWN:

                        if (checkWallCollision(penguin, BOTTOM_COLLISION)) {
                            return;
                        }

                        if (checkWaterCollision(penguin, BOTTOM_COLLISION)) {
                            return;
                        }

                        lightAreaOnFire(penguin, BOTTOM_COLLISION);

                        penguin.move(0, SPACE);

                        if (checkBagCollision()) {
                            return;
                        }

                        isCompleted();

                        break;

                    case KeyEvent.VK_R:

                        restartLevel();

                        break;

                    case KeyEvent.VK_P:
                        stateObserver.setState(ON_PAUSE);
                        break;

                    default:
                        break;
                }
            else if (stateObserver.getCurrentState() == ON_PAUSE)
                switch (key) {

                    case KeyEvent.VK_R:

                        stateObserver.setState(IN_GAME);
                        restartLevel();

                        break;

                    case KeyEvent.VK_M:

                        stateObserver.setState(IN_MAIN_MENU);
                        currentLevel = level1;

                        break;

                    case KeyEvent.VK_P:

                        stateObserver.setState(IN_GAME);
                        break;

                    default:
                        break;
                }
            else if (stateObserver.getCurrentState() == IN_MAIN_MENU)
                if (key > 0) {
                    stateObserver.setState(IN_GAME);
                }
            repaint();
        }
    }
}
