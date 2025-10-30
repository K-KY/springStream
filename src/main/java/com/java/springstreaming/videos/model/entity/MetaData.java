package com.java.springstreaming.videos.model.entity;

import com.java.springstreaming.util.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaData extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mapSeq;//아이디

    @Column
    private String fileType;//파일 타입

    @Column
    private String fileTitle;//제목

    @Column
    private String fileName;//실제 파일 이름

    @Column
    private String filePath;//파일 경로

    @Column
    private Long fileLength;//파일의 길이 -> 초

    @Column
    private Long fileSize;//파일의 크기

    @ManyToOne(fetch = FetchType.EAGER)
    private MetaDataGroup fileGroup;//파일이 속할 그룹
}
