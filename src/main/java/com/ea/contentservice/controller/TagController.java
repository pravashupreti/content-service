package com.ea.contentservice.controller;

import com.ea.contentservice.model.Tag;
import com.ea.contentservice.payload.ApiResponse;
import com.ea.contentservice.payload.PagedResponse;

import com.ea.contentservice.service.TagService;
import com.ea.contentservice.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<PagedResponse<Tag>> getAllTags(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<Tag> response = tagService.getAllTags(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping

    public ResponseEntity<Tag> addTag(@Valid @RequestBody Tag tag
    ) {
        Tag newTag = tagService.addTag(tag);

        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable(name = "id") Long id) {
        Tag tag = tagService.getTag(id);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PutMapping("/{id}")

    public ResponseEntity<Tag> updateTag(@PathVariable(name = "id") Long id, @Valid @RequestBody Tag tag
    ) {

        Tag updatedTag = tagService.updateTag(id, tag);

        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<ApiResponse> deleteTag(@PathVariable(name = "id") Long id) {
        ApiResponse apiResponse = tagService.deleteTag(id);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
