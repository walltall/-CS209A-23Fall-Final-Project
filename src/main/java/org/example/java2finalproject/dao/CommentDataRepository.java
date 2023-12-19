package org.example.java2finalproject.dao;

import org.example.java2finalproject.entity.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDataRepository extends JpaRepository<CommentData, Long> {
    public List<CommentData>findCommentDataByPostIdAndPostType(long post_id,String post_type);
}
