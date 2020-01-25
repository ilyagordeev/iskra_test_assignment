package xyz.mathic.iskra.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import xyz.mathic.iskra.dom.ReadyMessage;
import xyz.mathic.iskra.dom.User;
import xyz.mathic.iskra.controller.MainController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class ReadyService {

    private final MainController mainController;

    private Set<User> users = new HashSet<>();

    public ReadyService(MainController mainController) {
        this.mainController = mainController;
        List<String> names = List.of("stretch", "jessie", "wheezy", "squeeze", "lenny",
                "etch", "sarge", "woody", "potato", "slink", "hamm");
        names.forEach(name -> users.add(new User(name)));
        pollAll();
    }

    public void pollAll() {
        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(11);
        users.forEach(user -> service.scheduleAtFixedRate(new PollService(user), 0, 10, TimeUnit.SECONDS));
    }

    @Scheduled(fixedRate = 2000)
    private void check() {
        var notReady = users.stream()
                .filter(user -> !user.getStatus().equals(User.Status.READY))
                .collect(Collectors.toList());
        mainController.send(new ReadyMessage(notReady));
    }

}
