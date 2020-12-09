package com.conrad.spring.board.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class Reply {

    private int replyNo;
    private String replyContent;
    private int refBoardNo;
    private String replyWriter;
    private String createDate;
    private String status;
}
