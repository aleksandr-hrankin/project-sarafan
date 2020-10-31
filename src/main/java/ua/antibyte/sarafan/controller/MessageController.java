package ua.antibyte.sarafan.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.antibyte.sarafan.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("messages")
public class MessageController {
    private int counter = 4;
    private final List<Map<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "one"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "two"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "three"); }});
    }};

    @GetMapping
    public List<Map<String, String>> getAll() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getById(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> add(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> newMessage) {
        Map<String, String> messageFromDb = messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        messageFromDb.putAll(newMessage);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> messageFromDb = messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        messages.remove(messageFromDb);
    }
}
