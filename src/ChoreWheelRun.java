import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Alex on 10/4/2016.
 */
public class ChoreWheelRun extends JPanel{
    public final static int MS_PER_UPDATE = 20;
    private boolean[] keys;
    private ChoreWheel wheel;
    private boolean gameOver = false;

    public ChoreWheelRun(int width, int height) {
        keys = new boolean[65536];
        wheel = new ChoreWheel(this, width, height);
        setBackground(Color.WHITE);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocus();
        wheel.render(g);
    }

    public void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0.0;
        while (!gameOver)
        {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            wheel.processInput(keys);

            while (lag >= MS_PER_UPDATE)
            {
                wheel.update();
                lag -= MS_PER_UPDATE;
            }

            repaint();
        }
    }

    private void start() {
        gameLoop();
    }

    public void endLoop() {
        gameOver = true;
    }

    public static void main(String[] args) {
        ChoreWheelRun run = new ChoreWheelRun((int)(1920 * 1.5), (int)(1080 * 1.5 + 300));
        run.start();
    }
}
