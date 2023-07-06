package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Conquista;
import br.com.cwi.crescer.api.domain.TipoConquista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConquistaRepository extends JpaRepository<Conquista, Long> {
    List<Conquista> findByTipo(TipoConquista tipo);
}
