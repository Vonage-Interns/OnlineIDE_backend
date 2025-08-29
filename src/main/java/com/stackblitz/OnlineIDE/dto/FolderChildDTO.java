package com.stackblitz.OnlineIDE.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FolderChildDTO {
    private Long id;
    private String name;

    public FolderChildDTO(String name){
        this.name = name;
    }
}


