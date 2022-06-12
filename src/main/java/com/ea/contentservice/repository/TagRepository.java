package com.ea.contentservice.repository;

import com.ea.contentservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
}
