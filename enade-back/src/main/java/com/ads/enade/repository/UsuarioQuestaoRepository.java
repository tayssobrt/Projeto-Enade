package com.ads.enade.repository;

import com.ads.enade.entity.UsuarioQuestao;
import com.ads.enade.entity.UsuarioSimulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioQuestaoRepository extends JpaRepository<UsuarioQuestao,Long> {

    @Query("""
    SELECT uq FROM UsuarioQuestao uq
    WHERE uq.usuarioSimulado = :usuarioSimulado
    AND uq.questao.id = :idQuestao
""")
    Optional<UsuarioQuestao> verificarQuestaoJaRespondida(@Param("usuarioSimulado") UsuarioSimulado usuarioSimulado, @Param("idQuestao") Long idQuestao);
}
