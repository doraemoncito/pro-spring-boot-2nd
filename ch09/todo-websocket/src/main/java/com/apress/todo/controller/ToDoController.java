package com.apress.todo.controller;

import com.apress.todo.config.ToDoProperties;
import com.apress.todo.domain.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example TO-DO rest controller.  Send messages to the controller using curl commands like the one shown bellow:
 *
 * curl -XPOST -d '{"description":"Learn to program SpringBoot"}' -H "Content-Type: application/json" http://localhost:8080/to-do
 */
@Component
@RestController
@Slf4j
public class ToDoController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ToDoProperties toDoProperties;

    public ToDoController(final SimpMessagingTemplate simpMessagingTemplate, final ToDoProperties toDoProperties) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.toDoProperties = toDoProperties;
    }

    @RequestMapping("/to-do")
    public void addToDo(@RequestBody final ToDo toDo) {

        log.info("** Sending Message to WS: ws://todo/new - {}", toDo);
        this.simpMessagingTemplate.convertAndSend(this.toDoProperties.getBroker() + "/new", toDo);
    }

}
