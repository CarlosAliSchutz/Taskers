import { useState } from "react";
import IconeFinalizado from "../../../assets/icon/certinho.png";
import { DETALHAR_TAREFA_MODAL_STYLE } from "../../../constants";
import {
  useAlterarAfazer,
  useDeletarAfazer,
  useFinalizarAfazer,
} from "../../../hooks/api";
import { useModal } from "../../../hooks/use-modal.hook";
import { DetalharTarefaModal } from "../../components";
import "./index.css";

export function Afazer({ id, finalizado, titulo, handleRefresh }) {
  const { finalizarAfazer } = useFinalizarAfazer();
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const { openModal, closeModal } = useModal();
  const { deletarAfazer } = useDeletarAfazer();
  const { alterarAfazer } = useAlterarAfazer();

  async function handleFinalizar() {
    await finalizarAfazer(id);
    handleRefresh();
  }

  async function handleAlterarAfazer(id, formData) {
    await alterarAfazer(
      id,
      formData.nome.value,
      formData.descricao.value,
      formData.dificuldade.value
    );
    handleRefresh();
    closeModal(setModalIsOpen);
  }
  async function handleDeletarAfazer() {
    await deletarAfazer(id);
    handleRefresh();
  }

  function renderTitulo() {
    const tamanhoTexto = 14;
    if (titulo.length > tamanhoTexto) {
      return <h1>{titulo.substring(0, tamanhoTexto)}...</h1>;
    }
    return <h1>{titulo}</h1>;
  }

  function renderFinalizado() {
    if (finalizado) {
      return (
        <button onClick={handleFinalizar} className="caixa-finalizado">
          <img src={IconeFinalizado} alt="Ícone de finalizado" />
        </button>
      );
    }
    return (
      <button
        onClick={handleFinalizar}
        className="caixa-nao-finalizado"
      ></button>
    );
  }

  return (
    <div className="afazer">
      <div>{renderFinalizado()}</div>
      <label
        onClick={() => {
          openModal(setModalIsOpen);
        }}
      >
        {renderTitulo()}
      </label>
      <DetalharTarefaModal
        id={id}
        titulo={titulo}
        style={DETALHAR_TAREFA_MODAL_STYLE}
        isOpen={modalIsOpen}
        close={() => {
          closeModal(setModalIsOpen);
        }}
        editFunction={handleAlterarAfazer}
        deleteFunction={handleDeletarAfazer}
        tipo={"AFAZER"}
      />
    </div>
  );
}
