package com.normiesgram.app.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.normiesgram.app.dtos.PostDTO;

import com.normiesgram.app.utils.AuthenticatedUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@JsonComponent
public class PostJsonSerializer extends JsonSerializer<PostDTO> {

    @Autowired
    private AuthenticatedUsername authenticatedUsername;

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public void serialize(PostDTO post, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String currentUser = authenticatedUsername.getAuthenticatedUsername();

        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", post.getId());
        jsonGenerator.writeStringField("caption", post.getCaption());
        jsonGenerator.writeStringField("postedAt", df.format(post.getPostedAt()));
        jsonGenerator.writeStringField("lastEditAt", df.format(post.getLastEditAt()));

        jsonGenerator.writeFieldName("user");

        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("id", post.getUser().getId().toString());
        jsonGenerator.writeStringField("username", post.getUser().getUsername());
        jsonGenerator.writeStringField("name", post.getUser().getName());
        jsonGenerator.writeBooleanField("isCurrentUser", currentUser.equals(post.getUser().getUsername()));

        jsonGenerator.writeEndObject();

        jsonGenerator.writeNumberField("likes", post.getLikes());
        jsonGenerator.writeBooleanField("isLiking", false);
        jsonGenerator.writeNumberField("comments", post.getComments() != null ? post.getComments().size() : 0);

        jsonGenerator.writeEndObject();
    }
}
