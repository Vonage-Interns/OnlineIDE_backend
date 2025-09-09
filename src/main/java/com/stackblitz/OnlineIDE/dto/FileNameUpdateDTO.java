package com.stackblitz.OnlineIDE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FileNameUpdateDTO {
    private Long FileId;
    private String FileName;

}
