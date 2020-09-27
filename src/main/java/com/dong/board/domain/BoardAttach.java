package com.dong.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tbl_attach")
@ToString(exclude = "board")
public class BoardAttach {
    @Id
    @Column(name = "uuid", length = 100, nullable = false)
    private String uuid;

    @Column(name = "uploadPath", length = 200, nullable = false)
    private String uploadPath;

    @Column(name = "fileName",length = 100, nullable = false)
    private String fileName;

    @Column(name = "fileType")
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Board board;


    public void changeBoard(Board board){
        this.board=board;
        board.getAttachList().add(this);
    }
}