package com.stackblitz.OnlineIDE.dto;

<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import lombok.Data;

import java.util.Map;

@Data
<<<<<<< HEAD
=======
@Builder
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
public class FileDTO {
    private String fileName;
//    private String content;
    private Map<String, String> content;
    private String type;
    private Long projectID;
    private Long folderID;
}
