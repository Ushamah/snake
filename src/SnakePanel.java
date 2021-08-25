import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

public class SnakePanel extends JPanel implements ActionListener, KeyListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int DELAY = 200; //how quick the game is. The higher the number the slower the game
    final int[] xCoordinates = new int[GAME_UNITS];
    final int[] yCoordinates = new int[GAME_UNITS];
    char snakeInitDirection = 'R';
    int snakeInitLength = 1;
    int applesEaten;
    int appleX;
    int appleY;
    boolean snakeIsMoving = false;
    Timer timer;
    Random random;

    public SnakePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        showNewApple();
        snakeIsMoving = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (snakeIsMoving) {
           /* for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }*/

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snakeInitLength; i++) {
                if (i == 0) {
                    //The head of the snake
                    g.setColor(Color.GREEN);
                }
                else {
                    //the body of the snake
                    g.setColor(Color.RED);
                }
                g.fillRect(xCoordinates[i], yCoordinates[i], UNIT_SIZE, UNIT_SIZE);

            }
            //Set game over color
            setColorAndFont(g, Color.BLUE, new Font("TimesRoman", Font.BOLD, 40));
            FontMetrics scoreDisplayMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,
                    (SCREEN_WIDTH - scoreDisplayMetrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        }

        else {
            gameOver(g);
        }
    }

    public void showNewApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void moveSnake() {
        for (int i = snakeInitLength; i > 0; i--) {
            xCoordinates[i] = xCoordinates[i - 1];
            yCoordinates[i] = yCoordinates[i - 1];
        }
        switch (snakeInitDirection) {
            case 'U':
                yCoordinates[0] = yCoordinates[0] - UNIT_SIZE;
                break;
            case 'D':
                yCoordinates[0] = yCoordinates[0] + UNIT_SIZE;
                break;
            case 'L':
                xCoordinates[0] = xCoordinates[0] - UNIT_SIZE;
                break;
            case 'R':
                xCoordinates[0] = xCoordinates[0] + UNIT_SIZE;
                break;

        }

    }

    public void checkApple() {
        int snakeHeadPositionX = xCoordinates[0];
        int applePositionX = appleX;
        int snakeHeadPositionY = yCoordinates[0];
        int applePositionY = appleY;
        if ((snakeHeadPositionX == applePositionX) && (snakeHeadPositionY == applePositionY)) {
            snakeInitLength++;
            applesEaten++;
            showNewApple();
        }
    }

    public void checkCollisions() {
        for (int i = snakeInitLength; i > 0; i--) {
            //checks if the head collides with the body
            if ((xCoordinates[0] == xCoordinates[i]) && (yCoordinates[0] == yCoordinates[i])) {
                snakeIsMoving = false;
            }
            //check if head touches the left boarder
            if (xCoordinates[0] < 0) {
                snakeIsMoving = false;
            }

            //check if head touches the right boarder
            if (xCoordinates[0] > SCREEN_WIDTH) {
                snakeIsMoving = false;
            }

            //check if head touches the TOP boarder
            if (yCoordinates[0] < 0) {
                snakeIsMoving = false;
            }

            //check if head touches the Bottom boarder
            if (yCoordinates[0] > SCREEN_HEIGHT) {
                snakeIsMoving = false;
            }
            if (!snakeIsMoving) {
                timer.stop();
            }

        }

    }

    public void gameOver(Graphics g) {
        //Set game over color
        setColorAndFont(g, Color.RED, new Font("TimesRoman", Font.BOLD, 75));
        FontMetrics gameOverDisplayMetrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - gameOverDisplayMetrics.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (snakeIsMoving) {
            moveSnake();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    private void setColorAndFont(Graphics g, Color color, Font font) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics scoreDisplayMetrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten,
                (SCREEN_WIDTH - scoreDisplayMetrics.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (snakeInitDirection != 'R') {
                    snakeInitDirection = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (snakeInitDirection != 'L') {
                    snakeInitDirection = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (snakeInitDirection != 'D') {
                    snakeInitDirection = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (snakeInitDirection != 'U') {
                    snakeInitDirection = 'D';
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
