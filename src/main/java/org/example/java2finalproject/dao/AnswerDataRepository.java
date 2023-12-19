package org.example.java2finalproject.dao;

import jakarta.transaction.Transactional;
import org.example.java2finalproject.entity.AnswerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AnswerDataRepository extends JpaRepository<AnswerData, Long> {
//    public AnswerData findByAnswer_id(long answer_id);
    public List<AnswerData> findByQuestionId(long question_id);

}
