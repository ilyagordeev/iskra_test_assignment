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

    public PollService(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            poll();
        } catch (SocketTimeoutException e) {
            System.out.println("Offline");
            user.setStatus(User.Status.OFFLINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void poll() throws IOException {
        String s = "http://rd.iskrauraltel.ru:8888/contest1/status/";
        URL url = new URL(s + user.getName());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        user.setStatus(User.Status.valueOf(content.toString()));
        System.out.print(user.getName() + " " + status + " ");
        System.out.println(content.toString());
    }
}
