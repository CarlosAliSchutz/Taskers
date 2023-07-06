import { useEffect, useState } from "react";
import { TAREFA_MODAL_STYLE } from "../../../constants";
import { useForm, useModal, usePagination } from "../../../hooks";
import { useIncluirAfazer, useListarAfazeres } from "../../../hooks/api";
import { AdicionarTarefaModal, Afazer, PesquisarModal } from "../../components";

export function BoxAfazeres({
  renderSetaDireita,
  renderSetaEsquerda,
  renderPage,
}) {
  const [adicionarAfazerModalIsOpen, adicionarAfazerModalSetIsOpen] =
    useState(false);
  const [pesquisarAfazerModalIsOpen, pesquisarAfazerModalSetIsOpen] =
    useState(false);
  const { incluirAfazer } = useIncluirAfazer();
  const { afazeres, listarAfazeres, refreshAfazeres } = useListarAfazeres();
  const [afazerPage, setAfazerPage] = useState(0);
  const { openModal, closeModal } = useModal();
  const { backPage, nextPage } = usePagination();
  const { formData, handleChange, setFormData } = useForm({
    afazer: { value: "", error: "" },
  });

  useEffect(() => {
    listarAfazeres(formData.afazer.value, afazerPage);
  }, [afazerPage, formData]);

  function handleRefresh() {
    refreshAfazeres(formData.afazer.value, afazerPage);
  }

  async function handleCriarAfazer(newFormData) {
    await incluirAfazer(
      newFormData.titulo.value,
      newFormData.descricao.value,
      newFormData.dificuldade.value
    );
    handleRefresh();
  }

  function renderPesquisarModal() {
    return (
      <PesquisarModal
        tipo={"AFAZER"}
        formData={formData}
        handleChange={handleChange}
      />
    );
  }

  function renderCriarTarefaModal() {
    return (
      <AdicionarTarefaModal
        titulo={"Criar Afazer"}
        style={TAREFA_MODAL_STYLE}
        isOpen={adicionarAfazerModalIsOpen}
        close={() => {
          closeModal(adicionarAfazerModalSetIsOpen);
        }}
        onSubmit={handleCriarAfazer}
        tipo={"AFAZER"}
      />
    );
  }

  function renderTarefasVazias() {
    return <div className="sem-tarefas">Não há nenhum afazer no momento</div>;
  }

  function renderTitulo() {
    if (pesquisarAfazerModalIsOpen) {
      return renderPesquisarModal();
    }
    return <h1>Afazeres</h1>;
  }

  function handleSetPesquisarModal() {
    if (pesquisarAfazerModalIsOpen) {
      closeModal(pesquisarAfazerModalSetIsOpen);
      setFormData({ afazer: { value: "", error: "" } });
    } else {
      openModal(pesquisarAfazerModalSetIsOpen);
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
        {afazeres?.length > 0
          ? afazeres.map((afazer) => {
              return (
                <Afazer
                  key={afazer.id}
                  id={afazer.id}
                  finalizado={afazer.finalizado}
                  titulo={afazer.nome}
                  handleRefresh={handleRefresh}
                />
              );
            })
          : renderTarefasVazias()}
      </section>
      <footer>
        {renderSetaEsquerda(afazerPage, () => {
          backPage(setAfazerPage);
        })}
        <button
          onClick={() => {
            openModal(adicionarAfazerModalSetIsOpen);
          }}
        >
          +
        </button>

        {renderSetaDireita(afazeres?.length, () => {
          nextPage(setAfazerPage);
        })}

        {renderPage(afazeres?.length, afazerPage)}
      </footer>
      {renderCriarTarefaModal()}
    </div>
  );
}
