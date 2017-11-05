import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditWindows extends JFrame implements ActionListener {
    private DisplayWindows displayWindows;

    private JPanel panel;

    private JTextField startTime;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;

    private JTextArea message;
    private JButton sendButton;

    private JButton setFullscreenButton;

    EditWindows(DisplayWindows displayWindows) {
        this.setTitle("Escape Game : Mode d'édition");
        this.setSize(1000, 650);
        this.setMinimumSize(new Dimension(750, 500));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayWindows = displayWindows;

        this.initComponents();
        this.displayComponents();
        this.add(panel);

        this.setVisible(true);
    }

    private void initComponents() {
        startButton = new JButton("Start Timer");
        startButton.addActionListener(this);

        pauseButton = new JButton("Pause Timer");
        pauseButton.addActionListener(this);

        resetButton = new JButton("Reset Timer");
        resetButton.addActionListener(this);

        message = new JTextArea("Entrez l'indice ici");
        message.setFont(new Font("Serif", Font.PLAIN, 25));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);

        sendButton = new JButton("Envoyer L'indice");
        sendButton.addActionListener(this);

        startTime = new JTextField("" + Cooldown.DEFAULT_MINUTES);
        startTime.setFont(new Font("Serif", Font.PLAIN, 50));
        startTime.setHorizontalAlignment(JTextField.CENTER);

        setFullscreenButton = new JButton("Set Fullscreen");
        setFullscreenButton.addActionListener(this);
    }
    private void displayComponents() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global Constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridheight = 1;

        // Indices

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 11;
        panel.add(message, constraints);

        constraints.gridx += constraints.gridwidth;
        constraints.gridwidth = 1;
        panel.add(sendButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(startTime, constraints);

        // Timer

        constraints.gridx = 0;
        constraints.gridwidth = 4;

        constraints.gridy += constraints.gridheight;
        panel.add(startButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(pauseButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(resetButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(setFullscreenButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            displayWindows.startTimer(startTime.getText());
        }
        else if (e.getSource() == resetButton) {
            displayWindows.resetTimer();
        }
        else if (e.getSource() == pauseButton) {
            displayWindows.pauseTimer();
        }
        else if (e.getSource() == sendButton) {
            displayWindows.displayHint(message.getText());
        }
        else if (e.getSource() == setFullscreenButton) {
            Main.showOnSecondScreen(displayWindows);
        }
     }
}
