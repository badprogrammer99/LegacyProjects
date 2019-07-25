package com.normiesgram.app.controllers;

import com.normiesgram.app.dtos.PostDTO;
import com.normiesgram.app.services.PostService;
import com.normiesgram.app.utils.AuthenticatedUsername;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthenticatedUsername authenticatedUsername;

    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public List<PostDTO> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int resultsPerPage) {
         Optional<String> username = Optional.ofNullable(authenticatedUsername.getAuthenticatedUsername());

         return username.isPresent() ? postService.getAllPosts(page, resultsPerPage)
                 : postService.getAllPosts(0, 20);
    }

    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);

        return Optional.ofNullable(post)
                .map(p -> ResponseEntity.ok().body(post))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "posts", method = RequestMethod.POST)
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO post) {
        PostDTO newPost = postService.createNewPost(post);

        return Optional.ofNullable(newPost)
                .map(p -> ResponseEntity.ok().body(newPost))
                .orElseGet(() -> ResponseEntity.status(500).build());
    }
}
