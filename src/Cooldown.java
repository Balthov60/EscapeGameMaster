public class Cooldown {
    final static int DEFAULT_MINUTES = 50;
    private final static int DEFAULT_ADDITIONAL_TIME = 10;

    private int minutes;
    private int seconds;
    private boolean additionalTime;

    Cooldown() {
        reset();
    }

    void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    void reset() {
        minutes = DEFAULT_MINUTES;
        seconds = 0;
        additionalTime = false;
    }
    boolean decreaseSeconds() {
        seconds--;
        if (seconds < 0) {
            seconds = 59;
            if (!decreaseMinutes())
                return false;
        }
        return true;
    }
    private boolean decreaseMinutes() {
        minutes--;
        if (minutes < 0) {
            if (!additionalTime)
                launchAdditionalTime();
            else
                return false;
        }
        return true;
    }

    private void launchAdditionalTime() {
        minutes = DEFAULT_ADDITIONAL_TIME;
        seconds = 0;
        additionalTime = true;
    }

    public String toString() {
        return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}
