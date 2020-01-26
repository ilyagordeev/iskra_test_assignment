package xyz.mathic.iskra.dom;

import java.util.List;

/**
 * Pojo сообщения, отправляемого клиенту, содержит список игроков
 */
public class ReadyMessage {

    private List<User> users;

    public ReadyMessage() {
    }

    public ReadyMessage(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
