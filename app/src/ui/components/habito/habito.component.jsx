import { useState } from "react";
import { DETALHAR_TAREFA_MODAL_STYLE } from "../../../constants";
import {
  useAlterarHabito,
  useAumentarHabito,
  useDeletarHabito,
} from "../../../hooks/api";
import { useModal } from "../../../hooks/use-modal.hook";
import { DetalharTarefaModal } from "../../components";
import "./index.css";

export function Habito({ id, execucoes, titulo, handleRefresh }) {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const { openModal, closeModal } = useModal();
  const { aumentarHabito } = useAumentarHabito();
  const { deletarHabito } = useDeletarHabito();
  const { alterarHabito } = useAlterarHabito();

  async function handleAumentar() {
    await aumentarHabito(id);
    handleRefresh();
  }

  async function handleDeletarHabito() {
    await deletarHabito(id);
    handleRefresh();
  }

  async function handleAlterarHabito(id, formData) {
    await alterarHabito(
      id,
      formData.nome.value,
      formData.descricao.value,
      formData.dificuldade.value
    );
    handleRefresh();
    closeModal(setModalIsOpen);
  }

  function renderTitulo() {
    if (titulo.length > 12) {
      return <h1>{titulo.substring(0, 13)}...</h1>;
    }
    return <h1>{titulo}</h1>;
  }

  return (
    <div className="habito">
      <div>
        <h1>{execucoes}</h1>
      </div>
      <label
        onClick={() => {
          openModal(setModalIsOpen);
        }}
      >
        {renderTitulo()}
      </label>

      <button onClick={handleAumentar} className="botao-aumentar">
        +
      </button>
      <DetalharTarefaModal
        id={id}
        titulo={titulo}
        style={DETALHAR_TAREFA_MODAL_STYLE}
        isOpen={modalIsOpen}
        close={() => {
          closeModal(setModalIsOpen);
        }}
        editFunction={handleAlterarHabito}
        deleteFunction={handleDeletarHabito}
        tipo={"HABITO"}
      />
    </div>
  );
}
