package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.payload.request.HashtagAddRequest;
import com.deroahe.gallerybe.payload.response.MessageResponse;
import com.deroahe.gallerybe.service.impl.HashtagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {

    private HashtagServiceImpl hashtagService;

    @Autowired
    public HashtagController(HashtagServiceImpl hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/{id}")
    public Hashtag findHashtagById(@PathVariable int id) {
        return hashtagService.findHashtagById(id);
    }

    @GetMapping
    public List<Hashtag> findAllHashtags() {
        return hashtagService.findAllHashtags();
    }

    @PostMapping
    public ResponseEntity<?> saveHashtag(@RequestBody String hashtagName) {
        Hashtag hashtag = hashtagService.saveHashtag(hashtagName);
        if (hashtag == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Hashtag not saved"));
        }

        return ResponseEntity.ok().body(new MessageResponse("Hashtag saved successfully"));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateHashtag(@RequestBody Hashtag hashtag) {
        if (hashtagService.updateHashtag(hashtag) != null) {
            return ResponseEntity.ok().body(new MessageResponse("Hashtag updated successfully"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Hashtag not updated"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteHashtagById(@PathVariable int id) {
        if (!hashtagService.deleteHashtagById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Hashtag not deleted"));
        }

        return ResponseEntity.ok().body(new MessageResponse("Hashtag deleted successfully"));
    }

    @PostMapping("/delete-all")
    public ResponseEntity<?> deleteAllHashtags() {
        hashtagService.deleteAllHashtags();
        return ResponseEntity.ok().body(new MessageResponse("All hashtags deleted"));
    }
}
