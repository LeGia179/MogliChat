package htwberlin.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class MogliChatController {
    @GetMapping("/mogli")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }


}