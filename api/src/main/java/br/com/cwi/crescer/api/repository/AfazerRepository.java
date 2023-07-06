package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Afazer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AfazerRepository extends JpaRepository<Afazer, Long> {

    Page<Afazer> findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(boolean ativo, Long id, String text, Pageable pageable);

    @Query("SELECT a FROM Afazer a WHERE a.ativo = true AND a.proprietario.id = :proprietarioId AND a.finalizado = false ORDER BY RANDOM()")
    Page<Afazer> buscarAfazerDisponivelAleatorio(@Param("proprietarioId") Long proprietarioId, Pageable pageable);


}

