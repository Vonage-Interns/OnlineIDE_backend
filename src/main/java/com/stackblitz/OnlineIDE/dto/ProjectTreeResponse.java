package com.stackblitz.OnlineIDE.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class ProjectTreeResponse {
    private List<FolderTreeDTO> folders;
    private List<FileTreeDTO> files;
}
