package net.artem.javacore.jdbc.application.model;

import lombok.*;

import net.artem.javacore.jdbc.application.enums.WriterStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private WriterStatus writerStatus;


}
