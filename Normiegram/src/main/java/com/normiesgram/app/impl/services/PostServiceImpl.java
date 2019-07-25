package com.normiesgram.app.impl.services;

import com.normiesgram.app.dtos.PostDTO;
import com.normiesgram.app.entities.Post;
import com.normiesgram.app.entities.User;
import com.normiesgram.app.repositories.PostRepository;
import com.normiesgram.app.services.PostService;
import com.normiesgram.app.services.UserService;
import com.normiesgram.app.utils.AuthenticatedUsername;
import com.normiesgram.app.utils.ObjectMapperUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @Autowired
    private AuthenticatedUsername authenticatedUsername;

    @Override
    public List<PostDTO> getAllPosts(int page, int resultsPerPage) {
        PageRequest pageRequest = PageRequest.of(page, resultsPerPage);

        Page<Post> pagedPosts = postRepository.findAll(pageRequest);
        List<PostDTO> posts = objectMapperUtils.mapAll(pagedPosts.getContent(), PostDTO.class);

        return objectMapperUtils.mapAll(posts, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null) return null;

        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO createNewPost(PostDTO post) {
        String currentUsername = authenticatedUsername.getAuthenticatedUsername();

        post.setUser(userService.getUserByUsername(currentUsername));

        Post newPost = postRepository.saveAndFlush(modelMapper.map(post, Post.class));

        if (newPost == null) return null;

        return modelMapper.map(newPost, PostDTO.class);
    }
}
