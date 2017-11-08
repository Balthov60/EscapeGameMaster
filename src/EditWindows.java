import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

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

    private Timer timer;
    private Cooldown cooldown;
    private JLabel timerLabel;

    EditWindows(DisplayWindows displayWindow) {
        this.setTitle("Escape Game : Mode d'édition");
        this.setSize(1000, 650);
        this.setMinimumSize(new Dimension(750, 500));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.displayWindows = displayWindow;

        this.initComponents();
        this.displayComponents();
        this.add(panel);

        createTimer();

        this.setVisible(true);
    }

    private void initComponents() {
        cooldown = new Cooldown();

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

        timerLabel = new JLabel("Retour Timer");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
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
        constraints.gridwidth = 3;

        constraints.gridy += constraints.gridheight;
        panel.add(startButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(pauseButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(resetButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(setFullscreenButton, constraints);

        constraints.gridx += constraints.gridwidth;
        panel.add(timerLabel, constraints);
    }

    // TODO: Remove this duplicated code.
    private void createTimer() {
        ActionListener task = evt -> {
            if (!cooldown.decreaseSeconds()) {
                timerLabel.setText("Fin du temps imparti");
                timer.stop();
            } else {
                timerLabel.setText(cooldown.toString());
            }
        };
        timer = new Timer(1000, task);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            cooldown.setMinutes(Integer.parseInt(startTime.getText()));
            displayWindows.startTimer(startTime.getText());
            timer.start();
        }
        else if (e.getSource() == resetButton) {
            displayWindows.resetTimer();
            timer.stop();
            cooldown.reset();
            timerLabel.setText(cooldown.toString());
        }
        else if (e.getSource() == pauseButton) {
            displayWindows.pauseTimer();
            timer.stop();
        }
        else if (e.getSource() == sendButton) {
            displayWindows.displayHint(message.getText());

            sendButton.setBackground(Color.red);
            sendButton.setEnabled(false);

            java.util.Timer hintTimer = new java.util.Timer();
            hintTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendButton.setBackground(null);
                    sendButton.setEnabled(true);
                }
            }, DisplayWindows.HINT_TIME_IN_MILLIS);
        }
        else if (e.getSource() == setFullscreenButton) {
            Main.showOnSecondScreen(displayWindows);
        }
     }
}
