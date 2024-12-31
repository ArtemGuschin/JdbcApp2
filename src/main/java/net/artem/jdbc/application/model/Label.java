package net.artem.jdbc.application.model;

import lombok.*;

import net.artem.jdbc.application.enums.LabelStatus;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Label {
    private Long id;
    private String name;
    private LabelStatus labelStatus;






    public Label(Long id, String name) {

    }
}
