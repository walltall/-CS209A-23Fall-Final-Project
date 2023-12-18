package org.example.java2finalproject.dao;

import org.example.java2finalproject.entity.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDataRepository extends JpaRepository<CommentData, Long> {

}
