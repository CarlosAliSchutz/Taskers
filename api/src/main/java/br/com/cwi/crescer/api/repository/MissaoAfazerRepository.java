package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.MissaoAfazer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissaoAfazerRepository extends JpaRepository<MissaoAfazer, Long> {

    MissaoAfazer findByProprietarioId(Long usuarioId);

    Optional<MissaoAfazer> findByProprietarioIdAndAfazerIdAndConcluidaFalse(Long usuarioId, Long idAfazer);
}
