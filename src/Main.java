import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        DisplayWindows displayWindows = new DisplayWindows();
        new EditWindows(displayWindows);
    }

    //TODO : Test IF Working.
    static void showOnSecondScreen(JFrame frame)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        if(2 < gs.length)
        {
            gs[2].setFullScreenWindow(frame);
        }
        else
        {
            gs[1].setFullScreenWindow(frame);
        }
    }


}
