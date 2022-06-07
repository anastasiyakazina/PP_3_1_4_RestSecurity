package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@RestController
@RequestMapping ("/api/users")
@AllArgsConstructor
public class AdminRestController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

//    ResponseEntity - специальный класс, который представляет http-ответ.
//    Он содержит тело ответа, код состояния, заголовки.
//    Мы можем использовать его для более тонкой настройки http-ответа.

    @GetMapping("/{id}")
    public ResponseEntity <User> getUserById (@PathVariable ("id") int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity<User> addUser (@RequestBody User user) {
        return ResponseEntity.ok(userService.add(user));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<User> editUser (@RequestBody User user, @PathVariable ("id") int id) {
        return ResponseEntity.ok(userService.update(user, id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser (@PathVariable ("id") int id) {
        userService.delete(id);
    }
}
