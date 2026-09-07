package com.ads.enade.repository;

import com.ads.enade.entity.Usuario;
import com.ads.enade.entity.UsuarioSimulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioSimuladoRepository extends JpaRepository<UsuarioSimulado,Long> {

    @Query("""
    SELECT us FROM UsuarioSimulado us
    WHERE us.usuario = :usuario
    ORDER BY us.dataConclusao
""")
    List<UsuarioSimulado> buscarTodosOsSimuladosDoUsuario(@Param("usuario") Usuario usuario);

    @Query("""
    SELECT us FROM UsuarioSimulado us
    WHERE us.usuario = :usuario
    AND us.id = :id
""")
    Optional<UsuarioSimulado> buscarSimuladoDoUsuario(@Param("id") Long idUsuarioSimulado, @Param("usuario") Usuario usuario);
}
