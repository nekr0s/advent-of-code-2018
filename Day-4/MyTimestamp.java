import java.util.Date;

public class MyTimestamp implements Comparable<MyTimestamp> {
    private Date date;
    private String command;
    private String id;
    private boolean isBeingShift;
    private boolean isFellAsleep;
    private boolean isWokeUp;

    MyTimestamp(Date date, String command, char identification) {
        this.date = date;
        this.command = command;
        setBooleans(identification);
        setId();
    }

    public Date getDate() {
        return date;
    }

    public String getCommand() {
        return command;
    }

    private void setBooleans(char identification) {
        this.isFellAsleep = identification == 'f';
        this.isWokeUp = identification == 'w';
        this.isBeingShift = identification == 'G';
    }

    private void setId() {
        if (this.isBeingShift)
            this.id = command.substring(7, command.indexOf('b') - 1);
    }

    public String getId() {
        return id;
    }

    public boolean isBeingShift() {
        return isBeingShift;
    }

    public boolean isFellAsleep() {
        return isFellAsleep;
    }

    public boolean isWokeUp() {
        return isWokeUp;
    }

    @Override
    public int compareTo(MyTimestamp o) {
        return this.date.compareTo(o.date);
    }

    @Override
    public String toString() {
        return "[" + date + "]" + " " + command;
    }
}
