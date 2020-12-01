package com.conrad.spring.board.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@ToString
public class Board {

    private int boardNo;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;
    private String originName;
    private String changeName;
    private int count;
    private String createdDate;
    private String status;

}
