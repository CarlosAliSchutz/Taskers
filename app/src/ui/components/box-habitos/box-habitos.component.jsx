import { useEffect, useState } from "react";
import { AdicionarTarefaModal, Habito, PesquisarModal } from "..";
import { TAREFA_MODAL_STYLE } from "../../../constants";
import { useForm, useModal, usePagination } from "../../../hooks";
import { useIncluirHabito, useListarHabitos } from "../../../hooks/api";

export function BoxHabitos({
  renderSetaDireita,
  renderSetaEsquerda,
  renderPage,
}) {
  const [adicionarHabitoModalIsOpen, adicionarHabitoModalSetIsOpen] =
    useState(false);
  const [pesquisarHabitoModalIsOpen, pesquisarHabitoModalSetIsOpen] =
    useState(false);
  const { incluirHabito } = useIncluirHabito();
  const { habitos, listarHabitos, refreshHabitos } = useListarHabitos();
  const [habitoPage, setHabitoPage] = useState(0);
  const { openModal, closeModal } = useModal();
  const { backPage, nextPage } = usePagination();
  const { formData, handleChange, setFormData } = useForm({
    habito: { value: "", error: "" },
  });

  useEffect(() => {
    listarHabitos(formData?.habito?.value, habitoPage);
  }, [habitoPage, formData]);

  function handleRefresh() {
    refreshHabitos(formData?.habito?.value, habitoPage);
  }

  async function handleCriarHabito(formData) {
    await incluirHabito(
      formData?.titulo?.value,
      formData?.descricao?.value,
      formData?.dificuldade?.value
    );
    handleRefresh();
  }

  function renderPesquisarModal() {
    return (
      <PesquisarModal
        tipo={"HABITO"}
        formData={formData}
        handleChange={handleChange}
      />
    );
  }

  function renderCriarTarefaModal() {
    return (
      <AdicionarTarefaModal
        titulo={"Criar Hábito"}
        style={TAREFA_MODAL_STYLE}
        isOpen={adicionarHabitoModalIsOpen}
        close={() => {
          closeModal(adicionarHabitoModalSetIsOpen);
        }}
        onSubmit={handleCriarHabito}
        tipo={"HABITO"}
      />
    );
  }

  function renderTarefasVazias() {
    return <div className="sem-tarefas">Não há nenhum hábito no momento</div>;
  }

  function renderTitulo() {
    if (pesquisarHabitoModalIsOpen) {
      return renderPesquisarModal();
    }
    return <h1>Hábitos</h1>;
  }

  function handleSetPesquisarModal() {
    if (pesquisarHabitoModalIsOpen) {
      closeModal(pesquisarHabitoModalSetIsOpen);
      setFormData({ habito: { value: "", error: "" } });
    } else {
      openModal(pesquisarHabitoModalSetIsOpen);
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
        {habitos?.length > 0
          ? habitos.map((habito) => {
              return (
                <Habito
                  key={habito?.id}
                  id={habito?.id}
                  execucoes={habito?.execucoes}
                  titulo={habito?.nome}
                  handleRefresh={handleRefresh}
                />
              );
            })
          : renderTarefasVazias()}
      </section>
      <footer>
        {renderSetaEsquerda(habitoPage, () => {
          backPage(setHabitoPage);
        })}
        <button
          onClick={() => {
            openModal(adicionarHabitoModalSetIsOpen);
          }}
        >
          +
        </button>

        {renderSetaDireita(habitos?.length, () => {
          nextPage(setHabitoPage);
        })}

        {renderPage(habitos?.length, habitoPage)}
      </footer>
      {renderCriarTarefaModal()}
    </div>
  );
}
