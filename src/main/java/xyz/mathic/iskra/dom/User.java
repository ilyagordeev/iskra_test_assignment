package xyz.mathic.iskra.dom;

public class User {

    public enum Status {
        AWAY,
        BUSY,
        READY,
        OFFLINE
    }

    private String name;
    private Status status;
    private int key;
    private static int count = 0;


    public int getKey() {
        return key;
    }

    public User(String name) {
        this.name = name;
        this.key = count++;
        this.setStatus(Status.OFFLINE);
    }

    public String getName() {
        return name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

}
