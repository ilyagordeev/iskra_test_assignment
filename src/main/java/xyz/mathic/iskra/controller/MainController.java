package xyz.mathic.iskra.controller;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.mathic.iskra.dom.ReadyMessage;

@Controller
public class MainController {

    private final SimpMessagingTemplate template;

    public MainController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping
    public String index() {
        return "index.html";
    }

    @SendTo("/activity/get")
    public void send(ReadyMessage readyMessage) {
        this.template.convertAndSend("/activity/get", readyMessage);
    }
}
