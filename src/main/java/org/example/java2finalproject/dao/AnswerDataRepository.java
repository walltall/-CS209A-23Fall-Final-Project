package org.example.java2finalproject.dao;

import org.example.java2finalproject.entity.AnswerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerDataRepository extends JpaRepository<AnswerData, Long> {
}
