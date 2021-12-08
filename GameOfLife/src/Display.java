import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable{

    private JFrame frame;
    private Thread thread;

    private GameOfLife gameOfLife;
    private Mouse mouse;
    private boolean startLife = false;

    private boolean running = false;

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private final Color backgroundColor = new Color(15, 15, 60);

    public Display() {
        this.frame = new JFrame();
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        this.gameOfLife = new GameOfLife();
        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 's':
                        if (startLife) {
                            startLife = false;
                            return;
                        }
                        startLife = true;
                        break;
                    case 'e':
                        gameOfLife.resetLife();
                        startLife = false;
                        break;
                }
            }
        });
    }

    private void render() throws InterruptedException {

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        drawLife(g);

        g.dispose();
        bs.show();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }


    @Override
    public void run() {
        while (running) {
            try {

                if (startLife) {
                    gameOfLife.updateLife();
                }
                Thread.sleep(100);

                updateMouse();
                render();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void drawLife(Graphics g) {
        int divider = gameOfLife.getDivider();
        Color aliveColor = new Color(120, 160, 255);
        Color borderColor = new Color(27, 50, 130);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));


        for (int i = 0; i < gameOfLife.getRows(); i++) {
            for (int j = 0; j < gameOfLife.getCols(); j++) {

                g2.setColor(borderColor);

                g2.drawRect(j * divider, i * divider, divider, divider);

                if (gameOfLife.getCellGrid()[i][j] == true) {
                    g2.setColor(aliveColor);
                    g2.fillRect(j * divider, i * divider, divider, divider);
                } else {
                    g2.setColor(backgroundColor);
                    g2.fillRect(j * divider, i * divider, divider, divider);
                }


            }
        }
    }

    public void updateMouse() {

        int x;
        int y;

        if (this.mouse.getMouseButton() == 1) {


            x = this.mouse.getMouseX() / gameOfLife.getDivider();
            y = this.mouse.getMouseY() / gameOfLife.getDivider();

            gameOfLife.updateCell(y, x);
        }


    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.frame.setTitle("Game of Life");
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);

        display.frame.setVisible(true);
        display.start();


    }
}
