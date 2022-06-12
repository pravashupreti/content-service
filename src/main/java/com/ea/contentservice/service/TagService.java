package com.ea.contentservice.service;

import com.ea.contentservice.model.Tag;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;

public interface TagService {

    PagedResponse<Tag> getAllTags(int page, int size);

    Tag getTag(Long id);

    Tag addTag(Tag tag);

    Tag updateTag(Long id, Tag newTag);

    ApiResponse deleteTag(Long id);

}
