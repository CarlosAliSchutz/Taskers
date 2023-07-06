import { useEffect, useState } from "react";
import { TAREFA_MODAL_STYLE } from "../../../constants";
import { useForm, useModal, usePagination } from "../../../hooks";
import { useIncluirDiaria, useListarDiarias } from "../../../hooks/api";
import { AdicionarTarefaModal, Diaria, PesquisarModal } from "../../components";

export function BoxDiarias({
  renderSetaDireita,
  renderSetaEsquerda,
  renderPage,
}) {
  const [adicionarDiariaModalIsOpen, adicionarDiariaModalSetIsOpen] =
    useState(false);
  const [pesquisarDiariaModalIsOpen, pesquisarDiariaModalSetIsOpen] =
    useState(false);
  const { incluirDiaria } = useIncluirDiaria();
  const { diarias, listarDiarias, refreshDiarias } = useListarDiarias();
  const [diariaPage, setDiariaPage] = useState(0);
  const { openModal, closeModal } = useModal();
  const { backPage, nextPage } = usePagination();
  const { formData, handleChange, setFormData } = useForm({
    diaria: { value: "", error: "" },
  });

  useEffect(() => {
    listarDiarias(formData.diaria.value, diariaPage);
  }, [diariaPage, formData]);

  function handleRefresh() {
    refreshDiarias(formData.diaria.value, diariaPage);
  }

  async function handleCriarDiaria(newFormData) {
    await incluirDiaria(
      newFormData.titulo.value,
      newFormData.descricao.value,
      newFormData.dificuldade.value,
      newFormData.hora.value,
      newFormData.dias.value
    );
    refreshDiarias(formData.diaria.value, diariaPage);
  }

  function renderPesquisarModal() {
    return (
      <PesquisarModal
        tipo={"DIARIA"}
        formData={formData}
        handleChange={handleChange}
      />
    );
  }

  function renderCriarTarefaModal() {
    return (
      <AdicionarTarefaModal
        titulo={"Criar Diária"}
        style={TAREFA_MODAL_STYLE}
        isOpen={adicionarDiariaModalIsOpen}
        close={() => {
          closeModal(adicionarDiariaModalSetIsOpen);
        }}
        onSubmit={handleCriarDiaria}
        tipo={"DIARIA"}
      />
    );
  }

  function renderTarefasVazias() {
    return <div className="sem-tarefas">Não há nenhuma diária no momento</div>;
  }

  function renderTitulo() {
    if (pesquisarDiariaModalIsOpen) {
      return renderPesquisarModal();
    }
    return <h1>Diárias</h1>;
  }

  function handleSetPesquisarModal() {
    if (pesquisarDiariaModalIsOpen) {
      closeModal(pesquisarDiariaModalSetIsOpen);
      setFormData({ diaria: { value: "", error: "" } });
    } else {
      openModal(pesquisarDiariaModalSetIsOpen);
    }
  }

  return (
    <div className="caixa-tarefas">
      <header>
        <button onClick={handleSetPesquisarModal}></button>
        {renderTitulo()}
        <span />
      </header>
      <section>
        {diarias?.length > 0
          ? diarias.map((diaria) => {
              return (
                <Diaria
                  key={diaria.id}
                  id={diaria.id}
                  finalizado={diaria.finalizado}
                  titulo={diaria.nome}
                  hora={diaria.hora}
                  handleRefresh={handleRefresh}
                />
              );
            })
          : renderTarefasVazias()}
      </section>
      <footer>
        {renderSetaEsquerda(diariaPage, () => {
          backPage(setDiariaPage);
        })}
        <button
          onClick={() => {
            openModal(adicionarDiariaModalSetIsOpen);
          }}
        >
          +
        </button>

        {renderSetaDireita(diarias?.length, () => {
          nextPage(setDiariaPage);
        })}
        
        {renderPage(diarias?.length, diariaPage)}
      </footer>
      {renderCriarTarefaModal()}
    </div>
  );
}
