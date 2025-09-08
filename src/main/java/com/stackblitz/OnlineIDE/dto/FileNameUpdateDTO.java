package com.stackblitz.OnlineIDE.dto;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FileNameUpdateDTO {
    private Long FileId;
    private String FileName;
=======

import lombok.Data;

@Data
public class FileNameUpdateDTO {
    private long fileId;
    private String fileName;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
}
