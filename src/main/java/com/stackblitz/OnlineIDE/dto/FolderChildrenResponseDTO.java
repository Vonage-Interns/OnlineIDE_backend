package com.stackblitz.OnlineIDE.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FolderChildrenResponseDTO {
    private List<FolderChildDTO> folders;
    private List<FileResponseDTO> files;

    private long totalFiles; // added total files in the folder
    private boolean hasMore;
}
