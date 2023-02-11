package com.apress.todo.event;

import com.apress.todo.config.ToDoProperties;
import com.apress.todo.domain.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@Slf4j
public class ToDoEventHandler {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ToDoProperties toDoProperties;

    public ToDoEventHandler(final SimpMessagingTemplate simpMessagingTemplate, final ToDoProperties toDoProperties) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.toDoProperties = toDoProperties;
    }

    @HandleAfterCreate
    public void handleToDoSave(final ToDo toDo) {
        log.info(">> Sending Message to WS: ws://todo/new - " + toDo);
        this.simpMessagingTemplate.convertAndSend(this.toDoProperties.getBroker() + "/new", toDo);
    }

}
