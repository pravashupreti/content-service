package com.ea.contentservice.service.impl;

import com.ea.contentservice.exception.ResourceNotFoundException;

import com.ea.contentservice.model.Tag;

import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;
import com.ea.contentservice.repository.TagRepository;

import com.ea.contentservice.service.TagService;
import com.ea.contentservice.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PagedResponse<Tag> getAllTags(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<Tag> tags = tagRepository.findAll(pageable);

        List<Tag> content = tags.getNumberOfElements() == 0 ? Collections.emptyList() : tags.getContent();

        return new PagedResponse<>(content, tags.getNumber(), tags.getSize(), tags.getTotalElements(), tags.getTotalPages(), tags.isLast());
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag newTag) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        tag.setName(newTag.getName());
        return tagRepository.save(tag);
    }

    @Override
    public ApiResponse deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));

        tagRepository.deleteById(id);
        return new ApiResponse(Boolean.TRUE, "You successfully deleted tag");

    }
}






















