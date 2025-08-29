package com.stackblitz.OnlineIDE.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FileTreeDTO {
    private Long id;
    private String name;
    private String type;

    public FileTreeDTO(String name) {
        this.name = name;
    }

}
