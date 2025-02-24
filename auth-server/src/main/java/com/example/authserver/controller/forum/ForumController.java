package com.example.authserver.controller.forum;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.authserver.utils.codes.ProductCode.*;

@RestController
@RequestMapping(V1_URI + FORUMS_URI)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForumController {

    //TODO написать логику опубликовывания ответов
    @GetMapping
    public ResponseEntity<List<Object>> getForums(@RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok(List.of());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getForum(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}" + USERS_URI)
    public ResponseEntity<Object> getForumUsers(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Object> createForum(@RequestBody Object forum) {
        return null;
    }
    @PutMapping
    public ResponseEntity<Object> updateForum(@RequestBody Object forum) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteForum(@RequestBody Object forum) {
        return null;
    }
}
