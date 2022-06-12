package com.ea.contentservice.repository;

import com.ea.contentservice.model.Blog;
import com.ea.contentservice.model.Category;
import com.ea.contentservice.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
	Page<Blog> findByUserId(Long userId, Pageable pageable);

	Page<Blog> findByCategory(Category category, Pageable pageable);

	Page<Blog> findByTagsIn(List<Tag> tags, Pageable pageable);

//	Long countByCreatedBy(Long userId);
}
