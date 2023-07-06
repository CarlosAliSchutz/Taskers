import medalhaBronze from "../../../assets/img/medalha-bronze.png";
import medalhaOuro from "../../../assets/img/medalha-ouro.png";
import medalhaPrata from "../../../assets/img/medalha-prata.png";
import "./index.css";

export function BoxConquista({
  nome,
  descricao,
  progresso,
  objetivo,
  concluido,
  dificuldade,
}) {
  function renderMedalha() {
    if (dificuldade === "FACIL")
      return <img src={medalhaBronze} alt="Medalha de Bronze" />;
    if (dificuldade === "MEDIO")
      return <img src={medalhaPrata} alt="Medalha de Prata" />;
    if (dificuldade === "DIFICIL")
      return <img src={medalhaOuro} alt="Medalha de Ouro" />;
  }

  function renderProgresso() {
    if (concluido) return "Concluída";
    return `Progresso: ${progresso}/${objetivo}`;
  }

  return (
    <div
      className="box-conquista"
      style={
        concluido
          ? { backgroundColor: "#FEDF64" }
          : { backgroundColor: "#7f3bfa" }
      }
    >
      <div
        className="conquista-medalha"
        style={
          concluido
            ? { backgroundColor: "#272E63" }
            : { backgroundColor: "#3f1e7e" }
        }
      >
        {renderMedalha()}
      </div>
      <div className="conquista-dados">
        <h1 style={concluido ? { color: "#1C1B1B" } : { color: "#f0efef" }}>
          {nome}
        </h1>
        <p style={concluido ? { color: "#1C1B1B" } : { color: "#f0efef" }}>
          {descricao}
        </p>
        <section
          style={
            concluido
              ? { backgroundColor: "#272E63" }
              : { backgroundColor: "#3f1e7e" }
          }
          className="conquista-progresso"
        >
          {renderProgresso()}
        </section>
      </div>
    </div>
  );
}
