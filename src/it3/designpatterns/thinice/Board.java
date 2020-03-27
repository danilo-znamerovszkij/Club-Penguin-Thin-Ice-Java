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

    private GUI gui;
    private StateObserver stateObserver;
    private StartGame startGame;
    private PauseGame pauseGame;
    private EndGame endGame;

    private EasyLevel easyLevel;
    private MediumLevel mediumLevel;
    private HardLevel hardLevel;

    private ArrayList<Block> blocks;
    private ArrayList<MoneyBag> bags;
    private ArrayList<NextLevelDoor> nextLevelDoors;
    private ArrayList<MeltedIce> meltedBlocks;

    private Player penguin;

    private int score = 0;

    private boolean isCompleted = false;

    private String currentLevel;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        gui = new GUI();
        easyLevel = new EasyLevel();
        stateObserver = new StateObserver();

        //commands
        startGame= new StartGame();
        pauseGame = new PauseGame();
        endGame = new EndGame();

        //level generators
        easyLevel = new EasyLevel();
        mediumLevel = new MediumLevel();
        hardLevel = new HardLevel();

        currentLevel = easyLevel.generate();

        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld(currentLevel);
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

        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        switch (stateObserver.getCurrentState()) {
            case 1:
                gui.buildMenu(g, this);
                break;
            case 2:

                //prepare components
                ArrayList<Piece> world = new ArrayList<>();
                world.addAll(blocks);
                world.addAll(nextLevelDoors);
                world.addAll(bags);
                world.addAll(meltedBlocks);
                world.add(penguin);

                gui.buildLevels(g, this, isCompleted, score, world);
                break;
            case 3:
                //draw again for background
                world = new ArrayList<>();
                world.addAll(blocks);
                world.addAll(nextLevelDoors);
                world.addAll(bags);
                world.addAll(meltedBlocks);
                world.add(penguin);

                gui.buildLevels(g, this, isCompleted, score, world);

                gui.pauseMenu(g);
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
            if (currentLevel.equals(easyLevel.generate())) {
                currentLevel = mediumLevel.generate();
            } else if (currentLevel.equals(mediumLevel.generate())) {
                currentLevel = hardLevel.generate();
            } else if (currentLevel.equals(hardLevel.generate())) {
                currentLevel = easyLevel.generate();
                endGame.init(stateObserver);
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
                        pauseGame.init(stateObserver);
                        break;

                    default:
                        break;
                }
            else if (stateObserver.getCurrentState() == ON_PAUSE)
                switch (key) {

                    case KeyEvent.VK_R:

                        startGame.init(stateObserver);
                        restartLevel();

                        break;

                    case KeyEvent.VK_M:

                        endGame.init(stateObserver);
                        currentLevel = easyLevel.generate();

                        break;

                    case KeyEvent.VK_P:

                        startGame.init(stateObserver);
                        break;

                    default:
                        break;
                }
            else if (stateObserver.getCurrentState() == IN_MAIN_MENU)
                if (key > 0) {
                    startGame.init(stateObserver);
                }
            repaint();
        }
    }
}
