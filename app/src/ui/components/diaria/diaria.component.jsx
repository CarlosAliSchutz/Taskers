import { useState } from "react";
import IconeFinalizado from "../../../assets/icon/certinho.png";
import { DETALHAR_TAREFA_MODAL_STYLE } from "../../../constants";
import {
  useAlterarDiaria,
  useDeletarDiaria,
  useRealizarDiaria,
} from "../../../hooks/api";
import { useModal } from "../../../hooks/use-modal.hook";
import { DetalharTarefaModal } from "../../components";
import "./index.css";

export function Diaria({ id, finalizado, titulo, hora, handleRefresh }) {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const { openModal, closeModal } = useModal();
  const { realizarDiaria } = useRealizarDiaria();
  const { alterarDiaria } = useAlterarDiaria();
  const { deletarDiaria } = useDeletarDiaria();

  async function handleRealizar() {
    await realizarDiaria(id);
    handleRefresh();
  }

  async function handleAlterarDiaria(id, formData) {
    const novosDias = formData.dias.value.map((dia) => dia.id);
    await alterarDiaria(
      id,
      formData.nome.value,
      formData.descricao.value,
      formData.dificuldade.value,
      formData.hora.value,
      novosDias
    );
    handleRefresh();
    closeModal(setModalIsOpen);
  }

  async function handleDeletarDiaria() {
    await deletarDiaria(id);
    handleRefresh();
  }

  function renderFinalizado() {
    if (finalizado) {
      return (
        <button onClick={handleRealizar} className="caixa-finalizado">
          <img src={IconeFinalizado} alt="Ícone de finalizado" />
        </button>
      );
    }
    return (
      <button
        onClick={handleRealizar}
        className="caixa-nao-finalizado"
      ></button>
    );
  }

  function renderTitulo() {
    if (titulo.length > 12) {
      return <h1>{titulo.substring(0, 13)}...</h1>;
    }
    return <h1>{titulo}</h1>;
  }

  return (
    <div className="diaria">
      <div>{renderFinalizado()}</div>
      <label
        onClick={() => {
          openModal(setModalIsOpen);
        }}
      >
        {renderTitulo()}
      </label>
      <h2>{hora}</h2>
      <DetalharTarefaModal
        id={id}
        titulo={titulo}
        style={DETALHAR_TAREFA_MODAL_STYLE}
        isOpen={modalIsOpen}
        close={() => {
          closeModal(setModalIsOpen);
        }}
        editFunction={handleAlterarDiaria}
        deleteFunction={handleDeletarDiaria}
        tipo={"DIARIA"}
      />
    </div>
  );
}
