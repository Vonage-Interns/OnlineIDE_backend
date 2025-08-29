package com.stackblitz.OnlineIDE.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FolderTreeDTO {
    private Long id;
    private String name;

    public FolderTreeDTO(String name) {
        this.name = name;
    }
}
