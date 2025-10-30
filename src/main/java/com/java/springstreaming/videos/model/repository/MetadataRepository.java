package com.java.springstreaming.videos.model.repository;

import com.java.springstreaming.videos.model.entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<MetaData, Long> {}
