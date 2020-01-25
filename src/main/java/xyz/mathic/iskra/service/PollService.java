package xyz.mathic.iskra.service;

import xyz.mathic.iskra.dom.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class PollService implements Runnable {
    private User user;
    private URL url;

    public PollService(User user) {
        this.user = user;
        try {
            this.url = new URL("http://rd.iskrauraltel.ru:8888/contest1/status/" + this.user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            poll();
        } catch (SocketTimeoutException e) {
            user.setStatus(User.Status.OFFLINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void poll() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status = connection.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        user.setStatus(User.Status.valueOf(content.toString()));
    }
}
