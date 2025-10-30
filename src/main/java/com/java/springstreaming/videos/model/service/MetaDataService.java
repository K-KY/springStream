package com.java.springstreaming.videos.model.service;

import com.java.springstreaming.videos.model.entity.MetaData;
import com.java.springstreaming.videos.model.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetaDataService {

    private final MetadataRepository metadataRepository;

    public Page<MetaData> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return metadataRepository.findAll(pageable);
    }

    public void save(MetaData metaData) {
        metadataRepository.save(metaData);
    }

    public void save(List<MetaData> metaData) {
        metadataRepository.saveAll(metaData);
    }
}
