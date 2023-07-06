import { useEffect, useState } from "react";
import Modal from "react-modal";
import { TAREFA_MODAL_STYLE } from "../../../constants";
import { useModal } from "../../../hooks";
import {
  useDetalharAfazer,
  useDetalharDiaria,
  useDetalharHabito,
} from "../../../hooks/api";
import { AlterarTarefaModal } from "../../components";
import "./index.css";

export function DetalharTarefaModal({
  id,
  tipo,
  titulo,
  style,
  isOpen,
  close,
  editFunction,
  deleteFunction,
}) {
  const [alterarModalIsOpen, alterarModalSetIsOpen] = useState(false);
  const { openModal, closeModal } = useModal();
  const { detalharDiaria } = useDetalharDiaria();
  const { detalharAfazer } = useDetalharAfazer();
  const { detalharHabito } = useDetalharHabito();
  const [tarefa, setTarefa] = useState({});

  useEffect(() => {
    if (isOpen) {
      fetchTarefa();
    }
  }, [tipo,isOpen]);

  async function fetchTarefa() {
    if (tipo === "HABITO") {
      const _habito = await detalharHabito(id);
      setTarefa(_habito);
    }
    if (tipo === "DIARIA") {
      const _diaria = await detalharDiaria(id);
      setTarefa(_diaria);
    }
    if (tipo === "AFAZER") {
      const _afazer = await detalharAfazer(id);
      setTarefa(_afazer);
    }
  }


  function renderDetalhes() {
    if (tipo === "HABITO") {
      return tarefa ? (
        <section>
          <div>
            <span>Título: </span> {tarefa.nome}
          </div>
          <div>
            <span>Descrição: </span> {tarefa.descricao}
          </div>
          <div>
            <span>Dificuldade: </span> {tarefa.dificuldade}
          </div>
          <div>
            <span>Execuções: </span> {tarefa.execucoes}{" "}
          </div>
        </section>
      ) : null;
    }
    if (tipo === "DIARIA") {
      return tarefa ? (
        <section>
          <div>
            <span>Título:</span> {tarefa.nome}
          </div>
          <div>
            <span>Descrição: </span> {tarefa.descricao}
          </div>
          <div>
            <span>Dificuldade: </span> {tarefa.dificuldade}
          </div>
          <div>
            <span>Finalizado:</span> {renderFinalizado(tarefa.finalizado)}
          </div>
          <div>
            <span>Horário:</span> {tarefa.hora}
          </div>
          <div>
            <span>Dias: </span>
            {renderDias(tarefa.dias)}
          </div>
        </section>
      ) : null;
    }
    if (tipo === "AFAZER") {
      return tarefa ? (
        <section>
          <div>
            <span>Título:</span> {tarefa.nome}
          </div>
          <div>
            <span>Descrição: </span> {tarefa.descricao}
          </div>
          <div>
            <span>Dificuldade: </span> {tarefa.dificuldade}
          </div>
          <div>
            <span>Finalizado:</span> {renderFinalizado(tarefa.finalizado)}
          </div>
        </section>
      ) : null;
    }
  }

  function renderFinalizado(isFinalizado) {
    if (isFinalizado) {
      return "Sim";
    } else {
      return "Não";
    }
  }
  function renderDias(dias) {
    if (dias ? dias.length > 0 : null) {
      return (
        <div className="dias">
          {dias.map((dia) => {
            return <h3 key={dia?.id}>{dia.nome.substring(0, 3)}</h3>;
          })}
        </div>
      );
    }
  }

  function renderTitulo() {
    if (titulo.length > 25) {
      return <h1>{titulo.substring(0, 25)}...</h1>;
    }
    return <h1>{titulo}</h1>;
  }

  function renderAlterarModal() {
    if (tipo === "DIARIA") {
      return (
        <AlterarTarefaModal
          id={tarefa.id}
          nome={tarefa.nome}
          descricao={tarefa.descricao}
          dificuldade={tarefa.dificuldade}
          hora={tarefa.hora}
          oldDias={tarefa.dias}
          style={TAREFA_MODAL_STYLE}
          isOpen={alterarModalIsOpen}
          close={() => {
            closeModal(alterarModalSetIsOpen);
          }}
          onSubmit={editFunction}
          tipo={tipo}
        />
      );
    }
    return (
      <AlterarTarefaModal
        id={tarefa.id}
        nome={tarefa.nome}
        descricao={tarefa.descricao}
        dificuldade={tarefa.dificuldade}
        style={TAREFA_MODAL_STYLE}
        isOpen={alterarModalIsOpen}
        close={() => {
          closeModal(alterarModalSetIsOpen);
        }}
        onSubmit={editFunction}
        tipo={tipo}
      />
    );
  }

  return tarefa ? (
    <Modal isOpen={isOpen} onRequestClose={close} style={style}>
      <section className="detalhar-tarefa-modal">
        <header>
          <section>
          <button onClick={close}></button>
            {renderTitulo()}
          </section>
          <div>
            <button
              onClick={() => {
                openModal(alterarModalSetIsOpen);
              }}
              className="editar-tarefa"
            ></button>
            <button
              onClick={deleteFunction}
              className="deletar-tarefa"
            ></button>
          </div>
        </header>
        {tarefa ? renderDetalhes() : null}
        {tarefa ? renderAlterarModal() : null}
      </section>
    </Modal>
  ) : null;
}
