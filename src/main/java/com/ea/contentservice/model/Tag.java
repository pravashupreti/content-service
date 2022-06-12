package com.ea.contentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tags")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tag  {

	private static final long serialVersionUID = -5298707266367331514L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
	private List<Blog> blogs;

	public Tag(String name) {
		super();
		this.name = name;
	}

	public List<Blog> getBlogs() {
		return blogs == null ? null : new ArrayList<>(blogs);
	}

	public void setBlogs(List<Blog> blogs) {
		if (blogs == null) {
			this.blogs = null;
		} else {
			this.blogs = blogs;
		}
	}

}
