package com.ads.enade.repository;

import com.ads.enade.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Curso, Long> {
}
