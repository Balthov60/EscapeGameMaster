import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        DisplayWindows displayWindows = new DisplayWindows();

        // Disable this to test code on single screen.
        showOnSecondScreen(displayWindows);
        displayWindows.setAlwaysOnTop(true);

        new EditWindows(displayWindows);
    }

    static void showOnSecondScreen(JFrame frame)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        if(2 < gs.length)
        {
            Rectangle bounds = gs[2].getDefaultConfiguration().getBounds();
            frame.setSize(bounds.width, bounds.height);
            frame.setLocation(bounds.x, bounds.y);
        }
        else
        {
            Rectangle bounds = gs[1].getDefaultConfiguration().getBounds();
            frame.setSize(bounds.width, bounds.height);
            frame.setLocation(bounds.x, bounds.y);
        }
    }


}
