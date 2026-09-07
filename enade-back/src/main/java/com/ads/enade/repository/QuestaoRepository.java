package com.ads.enade.repository;

import com.ads.enade.entity.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    @Query(value = "SELECT * FROM questao ORDER BY RAND() LIMIT :quantidade", nativeQuery = true)
    List<Questao> buscarQuestoesPorQuantidade(@Param("quantidade") int quantidade);
}
