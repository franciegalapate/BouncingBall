import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BallPanel extends JPanel implements ActionListener, ChangeListener {
    final int PANEL_WIDTH = 800;
    final int PANEL_HEIGHT = 550;
    final int BALL_PANEL_WIDTH = 800;
    final int BALL_PANEL_HEIGHT = 500;
    Image ball;
    ImageIcon bgIcon;
    Timer timer;
    double xVelocity = 2.0; // Use double for more precise calculations
    double yVelocity = 0.0; // Initial y velocity is 0
    double gravity = 0.2;
    boolean isBouncing = false;
    boolean constantBouncing = false;
    int x, y = 0;
    JButton start = new JButton("START");
    JButton stop = new JButton("STOP");
    JButton constant = new JButton("CONSTANT");
    JButton reset = new JButton("RESET");
    JLabel speed = new JLabel();
    JSlider slider = new JSlider(0, 20, 10);

    BallPanel() {   // BallPanel constructor
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));    // sets the preferred size of dimension
        this.setBackground(Color.black);    //may be removed since it will be covered by court.png
        ball = new ImageIcon("src/pics/ball.png").getImage();  //creates image

        //sets court.png as background and resizing it to fit the size of panel
        bgIcon = new ImageIcon("src/pics/court.png");
        Image bgImage = bgIcon.getImage();
        Image newBgImage = bgImage.getScaledInstance(BALL_PANEL_WIDTH, BALL_PANEL_HEIGHT, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(newBgImage);

        start.setBounds(0, 500, 100, 50);
        start.setFont(new Font("Ink Free", Font.BOLD, 20));
        start.setForeground(Color.blue);
        start.setBackground(Color.orange);
        start.setOpaque(true);
        start.setFocusable(false);
        start.setBorder(BorderFactory.createEtchedBorder());
        start.addActionListener(this);

        stop.setBounds(100, 500, 100, 50);
        stop.setFont(new Font("Ink Free", Font.BOLD, 20));
        stop.setForeground(Color.blue);
        stop.setBackground(Color.orange);
        stop.setOpaque(true);
        stop.setFocusable(false);
        stop.setBorder(BorderFactory.createEtchedBorder());
        stop.addActionListener(this);

        constant.setBounds(200, 500, 100, 50);
        constant.setFont(new Font("Ink Free", Font.BOLD, 15));
        constant.setForeground(Color.blue);
        constant.setBackground(Color.orange);
        constant.setOpaque(true);
        constant.setFocusable(false);
        constant.setBorder(BorderFactory.createEtchedBorder());
        constant.addActionListener(this);

        reset.setBounds(700, 500, 100, 50);
        reset.setFont(new Font("Ink Free", Font.BOLD, 20));
        reset.setForeground(Color.red);
        reset.setBackground(Color.orange);
        reset.setOpaque(true);
        reset.setFocusable(false);
        reset.addActionListener(this);

        speed.setBounds(330, 510, 80, 40);
        speed.setFont(new Font("MV Boli", Font.PLAIN, 12));
        speed.setForeground(Color.white);
        speed.setText("Timer Delay:");

        slider.setForeground(Color.black);
        slider.setBounds(400, 510, 200, 40);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(2);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setOrientation(SwingConstants.HORIZONTAL);
        slider.addChangeListener(this);

        this.add(start);
        this.add(stop);
        this.add(constant);
        this.add(reset);
        this.add(speed);
        this.add(slider);
        this.setLayout(null);

        timer = new Timer(10, this);    //creates a timer for BallPanel
        timer.stop();
    }

    public void paint(Graphics g) {
        super.paint(g); //paints the background
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(bgIcon.getImage(), 0, 0, null); //draws bgImage at (0,0) coordinates
        g2D.drawImage(ball, x, y, null);    //draws the ball at (x,y) coordinates continuously
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //animation
        if (isBouncing) {
            yVelocity += gravity;
            x += (int) xVelocity;
            y += (int) yVelocity;
            if (x >= BALL_PANEL_WIDTH - ball.getWidth(null) || x < 0) {
                xVelocity *= -0.8;
            }
            if (y >= BALL_PANEL_HEIGHT - ball.getHeight(null) || y < 0) {
                y = BALL_PANEL_HEIGHT - ball.getHeight(null) - 1;
                yVelocity *= -0.8;
            }
            repaint();
        }
        if (constantBouncing) {
            if (x >= BALL_PANEL_WIDTH - ball.getWidth(null) || x < 0) {
                xVelocity *= -1;
            }
            x += (int) xVelocity;
            if (y >= BALL_PANEL_HEIGHT - ball.getHeight(null) || y < 0) {
                yVelocity *= -1;
            }
            y += (int) yVelocity;
            repaint();
        }

        //buttons
        if (e.getSource() == start) {
            if(!isBouncing) {
                timer.start();
                isBouncing = true;
            }
        }
        if (e.getSource() == stop) {
            timer.stop();
            isBouncing = false;
            constantBouncing = false;
        }
        if (e.getSource() == constant) {
            if(!constantBouncing) {
                xVelocity = 3;
                yVelocity = 5;
                timer.start();
                constantBouncing = true;
            }
        }
        if (e.getSource() == reset) {
            timer.stop();
            x = 0;
            y = 0;
            xVelocity = 2;
            yVelocity = 0;
            gravity = 0.2;
            isBouncing = false;
            constantBouncing = false;
            repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        timer.setDelay(slider.getValue());
    }
}

