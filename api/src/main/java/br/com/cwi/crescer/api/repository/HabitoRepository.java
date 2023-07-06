package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Habito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HabitoRepository extends JpaRepository<Habito, Long> {

    Page<Habito> findByAtivoAndProprietarioIdAndNomeContainingIgnoreCase(boolean ativo, Long id, String text, Pageable pageable);

    @Query("SELECT h FROM Habito h WHERE h.ativo = true AND h.proprietario.id = :proprietarioId ORDER BY RANDOM()")
    Page<Habito> buscarHabitoDisponivelAleatorio(@Param("proprietarioId") Long proprietarioId, Pageable pageable);

    List<Habito> findAllByAtivoTrue();
}
