import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.TimerTask;

class DisplayWindows extends JFrame {
    private JTextPane hint;
    private JTextField timerText;

    private JPanel hintPanel;

    private Cooldown cooldown;
    private Timer timer;

    private Boolean isHintActivated = false;

    DisplayWindows() {
        this.setTitle("Escape Game");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);

        initComponents();
        createTimer();
        this.getContentPane().add(timerText);

        this.setVisible(true);
    }

    private void initComponents() {
        cooldown = new Cooldown();

        Font font = new Font("Serif", Font.PLAIN, 100);

        hint = new JTextPane();
        timerText = new JTextField(cooldown.toString());

        hint.setEditable(false);
        hint.setBackground(Color.BLACK);
        hint.setForeground(Color.WHITE);
        hint.setFont(font);

        StyledDocument doc = hint.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(10, doc.getLength(), center, false);

        font = new Font("Serif", Font.PLAIN, 250);

        timerText.setEditable(false);
        timerText.setBackground(Color.BLACK);
        timerText.setForeground(Color.RED);
        timerText.setHorizontalAlignment(JTextField.CENTER);
        timerText.setFont(font);
        timerText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    private void initHintPanel() {
        hintPanel = new JPanel();
        hintPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global Constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;

        constraints.gridy = 0;
        constraints.weighty = 1;
        hintPanel.add(timerText, constraints);

        constraints.gridy = 1;
        constraints.weighty = 5;
        hintPanel.add(hint, constraints);
    }
    private void createTimer() {
        ActionListener task = evt -> {
            if (!cooldown.decreaseSeconds()) {
                timerText.setText("Fin du temps imparti");
                timer.stop();
            } else {
                timerText.setText(cooldown.toString());
            }
        };
        timer = new Timer(1000, task);
    }

    void pauseTimer() {
        timer.stop();
    }
    void startTimer(String minutes) {
        cooldown.setMinutes(Integer.parseInt(minutes));
        timer.start();
    }
    void resetTimer() {
        timer.stop();
        cooldown.reset();
        timerText.setText(cooldown.toString());
    }

    void displayHint(String text) {
        if (isHintActivated)
            return;

        isHintActivated = true;
        hint.setText(text);
        displayText();

        java.util.Timer hintTimer = new java.util.Timer();
        hintTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayTimer();
                isHintActivated = false;
            }
        }, 10000);
    }
    private void displayText() {
        this.getContentPane().removeAll();
        this.initHintPanel();
        this.getContentPane().add(hintPanel);

        this.repaint();
        this.revalidate();
    }
    private void displayTimer() {
        this.getContentPane().removeAll();
        this.getContentPane().add(timerText);
        this.repaint();
        this.revalidate();
    }
}