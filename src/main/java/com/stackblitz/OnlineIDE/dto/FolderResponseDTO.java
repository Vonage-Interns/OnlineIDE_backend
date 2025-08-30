package com.stackblitz.OnlineIDE.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FolderResponseDTO {
    private Long id;
    private String name;
    private Long projectId;
    private Long parentFolderId;
}
