package com.java.springstreaming.videos.model.entity;

import com.java.springstreaming.util.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.io.File;

@Entity
public class MetaDataGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSeq;

    @Column
    private String groupName;

    @Column
    private File groupImage;
}
