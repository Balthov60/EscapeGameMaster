import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

class DisplayWindows extends JFrame {
    private JTextPane hint;
    private JTextField timerText;

    private JPanel hintPanel;

    private Cooldown cooldown;
    private Timer timer;

    static final int HINT_TIME_IN_MILLIS = 15000;

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
        playHintSound();
        hint.setText(text);
        displayText();

        java.util.Timer hintTimer = new java.util.Timer();
        hintTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayTimer();
            }
        }, HINT_TIME_IN_MILLIS);
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

    private synchronized void playHintSound() {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("./res/hintNotification.wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}