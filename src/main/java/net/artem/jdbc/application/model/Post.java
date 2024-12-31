package net.artem.jdbc.application.model;

import lombok.*;

import net.artem.jdbc.application.enums.PostStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Post {
    private Long id;
    private String content;
    private PostStatus postStatus;
    private List<Label> labels;
    private Date created;
    private Date updated;
    private Writer writer;


}










