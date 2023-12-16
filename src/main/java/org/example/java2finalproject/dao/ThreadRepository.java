package org.example.java2finalproject.dao;
import org.example.java2finalproject.entity.ThreadsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<ThreadsData, Long> {


}
