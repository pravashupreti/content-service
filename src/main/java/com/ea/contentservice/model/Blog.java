package com.ea.contentservice.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "blogs", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Blog {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public List<Comment> getComments() {
        return comments == null ? null : new ArrayList<>(comments);
    }

    public void setComments(List<Comment> comments) {
        if (comments == null) {
            this.comments = null;
        } else {
            this.comments = comments;
        }
    }

    public List<Tag> getTags() {
        return tags == null ? null : new ArrayList<>(tags);
    }

    public void setTags(List<Tag> tags) {
        if (tags == null) {
            this.tags = null;
        } else {
            this.tags = tags;
        }
    }
}
