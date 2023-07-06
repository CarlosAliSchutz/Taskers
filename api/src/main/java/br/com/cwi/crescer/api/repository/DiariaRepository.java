package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Diaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface DiariaRepository extends JpaRepository<Diaria, Long> {

    Page<Diaria> findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(boolean ativo, Long id, String text, Pageable pageable);

    List<Diaria> findByAtivoTrueAndHoraAndDiasId(LocalTime agora, Long diaId);

    List<Diaria> findByAtivoTrueAndDiasId(Long diaId);

    @Query("SELECT d FROM Diaria d WHERE d.ativo = true AND d.proprietario.id = :proprietarioId AND d.finalizado = false ORDER BY RANDOM()")
    Page<Diaria> buscarDiariaDisponivelAleatoria(@Param("proprietarioId") Long proprietarioId, Pageable pageable);
}
