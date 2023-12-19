package org.example.java2finalproject.dao;
import jakarta.transaction.Transactional;
import org.example.java2finalproject.entity.QuestionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface QuestionDataRepository extends JpaRepository<QuestionData, Long> {


}
