package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.MissaoHabito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MissaoHabitoRepository extends JpaRepository<MissaoHabito, Long> {

    MissaoHabito findByProprietarioId(Long usuarioId);

    Optional<MissaoHabito> findByProprietarioIdAndHabitoIdAndConcluidaFalse(Long id, Long idHabito);
}
