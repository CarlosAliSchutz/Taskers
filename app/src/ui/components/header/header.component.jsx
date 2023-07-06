import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Logo from "../../../assets/img/Logo.png";
import { useModal } from "../../../hooks";
import { BarraNotificacao, BarraMissoes } from "../";
import "./index.css";

export function Header({ paginaAtual }) {
  const [classOn, setClassOn] = useState(false);
  const navigate = useNavigate();
  const [isMissoesOpen, setMissoesIsOpen] = useState(false);
  const [isNotificacoesOpen, setNotificacoesIsOpen] = useState(false);
  const { openModal } = useModal();

  function handlePerfil(event) {
    event.preventDefault();
    navigate("/perfil");
  }

  function handleOpenNotificacoes() {
    openModal(setNotificacoesIsOpen);
  }

  function handleOpenMissoes() {
    openModal(setMissoesIsOpen);
  }

  function renderTarefas() {
    if (paginaAtual === "tarefas") {
      return (
        <Link className="pagina-atual" to={"/tarefas"}>
          Tarefas
        </Link>
      );
    }
    return (
      <Link className="links" to={"/tarefas"}>
        Tarefas
      </Link>
    );
  }

  function renderLoja() {
    if (paginaAtual === "loja") {
      return (
        <Link className="pagina-atual" to={"/loja"}>
          Loja
        </Link>
      );
    }
    return (
      <Link className="links" to={"/loja"}>
        Loja
      </Link>
    );
  }

  function renderRanking() {
    if (paginaAtual === "ranking") {
      return (
        <Link className="pagina-atual" to={"/ranking"}>
          Ranking
        </Link>
      );
    }
    return (
      <Link className="links" to={"/ranking"}>
        Ranking
      </Link>
    );
  }

  return (
    <header className="header">
      <div className="logo-header">
        <img src={Logo} alt="Logo Taskers" />
      </div>

      <ul className="botoes-header-mobile">
        <li>
          <span className="icon icon-missao" onClick={handleOpenMissoes} />
        </li>
        <li>
          <span
            className="icon icon-sininho"
            onClick={handleOpenNotificacoes}
          />
        </li>
        <li>
          <span className="icon icon-usuario" onClick={handlePerfil} />
        </li>
      </ul>

      <div
        className={classOn ? "menu-section on" : "menu-section"}
        onClick={() => setClassOn(!classOn)}
      >
        <div className="menu-toggle">
          <div className="one"></div>
          <div className="two"></div>
          <div className="three"></div>
        </div>

        <div className="menu">
          <ul>
            {renderTarefas()}
            {renderLoja()}
            {renderRanking()}
          </ul>
        </div>
      </div>
      <ul className="botoes-header">
        <li>
          <span className="icon icon-missao" onClick={handleOpenMissoes} />
        </li>
        <li>
          <span
            className="icon icon-sininho"
            onClick={handleOpenNotificacoes}
          />
        </li>
        <li>
          <span className="icon icon-usuario" onClick={handlePerfil} />
        </li>
      </ul>
      <BarraNotificacao
        isOpen={isNotificacoesOpen}
        setIsOpen={setNotificacoesIsOpen}
      />
      <BarraMissoes isOpen={isMissoesOpen} setIsOpen={setMissoesIsOpen} />
    </header>
  );
}
