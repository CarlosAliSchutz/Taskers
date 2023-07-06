import { useEffect } from "react";
import Modal from "react-modal";
import { BoxMissao } from "..";
import { MISSAO_MODAL_STYLE } from "../../../constants";
import { useModal } from "../../../hooks";
import { useListarMissoes } from "../../../hooks/api";
import "./index.css";

export function BarraMissoes({ isOpen, setIsOpen }) {
  const { missoes, listarMissoes } = useListarMissoes();
  const { closeModal } = useModal();

  useEffect(() => {
    listarMissoes();
  }, []);

  function handleClose() {
    closeModal(setIsOpen);
  }

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={handleClose}
      style={MISSAO_MODAL_STYLE}
    >
      <section className="barra-missoes">
        <header>
          <h1>Missões</h1>
        </header>
        <section>
          {missoes
            ? missoes?.map((missao, index) => {
                return (
                  <BoxMissao
                    key={index}
                    id={missao.id}
                    titulo={missao.nome}
                    descricao={missao.descricao}
                    concluida={missao.concluida}
                    execucoesRealizadas={missao.execucoesRealizadas}
                    execucoesNecessarias={missao.execucoesNecessarias}
                  />
                );
              })
            : null}
        </section>
      </section>
    </Modal>
  );
}
