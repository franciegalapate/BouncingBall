import javax.swing.*;

public class BallFrame extends JFrame {
    BallPanel panel;
    BallFrame() {   //BallFrame constructor
        panel = new BallPanel();    //creates a new panel

        this.setTitle("Bouncing Ball Program"); // sets the title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //exits the application upon clicking close operation
        this.setResizable(false);
        this.add(panel);    //adds panel inside the frame
        this.pack();    //sized to fit its included subcomponents
        this.setLocationRelativeTo(null);   // sets location of frame relative to null
        this.setVisible(true);  //shows window
    }
}
