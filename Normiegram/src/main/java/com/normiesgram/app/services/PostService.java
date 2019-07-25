package com.normiesgram.app.services;

import com.normiesgram.app.dtos.PostDTO;
import com.normiesgram.app.entities.Post;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts(int page, int resultsPerPage);

    PostDTO getPostById(Long id);

    PostDTO createNewPost(PostDTO post);
}
