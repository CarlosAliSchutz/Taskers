import { Recompensa } from "../";
import finalizado from "../../../assets/icon/certinho-roxo.png";
import taskcoinIcon from "../../../assets/img/recompensa-taskcoin.png";
import xpIcon from "../../../assets/img/recompensa-xp.png";
import "./index.css";

export function BoxMissao({
  id,
  titulo,
  descricao,
  concluida,
  execucoesRealizadas,
  execucoesNecessarias,
}) {
  function renderTitulo() {
    if (titulo.length > 16) {
      return `${titulo.substring(0, 16)}...`;
    }
    return titulo;
  }

  function renderProgresso() {
    return concluida ? (
      <img src={finalizado} alt="simbolo de finalizado" />
    ) : (
      `${execucoesRealizadas}/${execucoesNecessarias}`
    );
  }

  function renderRecompensas() {
    return (
      <>
        <Recompensa valor={10} icon={taskcoinIcon} finalizado={concluida} />
        <Recompensa valor={50} icon={xpIcon} finalizado={concluida} />
      </>
    );
  }

  return id ? (
    <div className="box-missao">
      <section className="box-missao-esquerda">
        <header>{renderTitulo()}</header>
        <section>
          <p>{descricao}</p>
        </section>
      </section>
      <section className="box-missao-direita">
        <section>
          <p>{renderProgresso()}</p>
        </section>
        <footer>{renderRecompensas()}</footer>
      </section>
    </div>
  ) : null;
}

BoxMissao.defaultProps = {
  execucoesRealizadas: 0,
  execucoesNecessarias: 1
}
