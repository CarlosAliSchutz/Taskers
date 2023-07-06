package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.MissaoDiaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissaoDiariaRepository extends JpaRepository<MissaoDiaria, Long> {

    MissaoDiaria findByProprietarioId(Long usuarioId);

    Optional<MissaoDiaria> findByProprietarioIdAndDiariaIdAndConcluidaFalse(Long id, Long idDiaria);
}
