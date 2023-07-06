import { useEffect } from "react";
import Modal from "react-modal";
import { ADICIONAR_TAREFAS_FORM_DATA } from "../../../constants";
import { useForm } from "../../../hooks";
import { useListarDias } from "../../../hooks/api";
import { ButtonSecondary, Input } from "../../components";
import "./index.css";

export function AdicionarTarefaModal({
  titulo,
  style,
  isOpen,
  close,
  onSubmit,
  tipo,
}) {
  const { dias, listarDias } = useListarDias();
  const { formData, handleChange, setFormData } = useForm(
    ADICIONAR_TAREFAS_FORM_DATA
  );

  useEffect(() => {
    if (tipo === "DIARIA") {
      listarDias();
    }
  }, []);

  function handleClose() {
    close();
    setFormData(ADICIONAR_TAREFAS_FORM_DATA);
  }

  function handleSelecionarDia(dia) {
    if (formData.dias.value.some((diaData) => diaData === dia.id)) {
      const novosDias = formData.dias.value.filter(
        (diaData) => diaData !== dia.id
      );
      setFormData((pastInfo) => ({
        ...pastInfo,
        dias: {
          value: novosDias,
          error: "",
        },
      }));
    } else {
      const novosDias = [...formData.dias.value, dia.id];
      setFormData((pastInfo) => ({
        ...pastInfo,
        dias: {
          value: novosDias,
          error: "",
        },
      }));
    }
  }

  function handleChangeDificuldade(dificuldadeEscolhida) {
    setFormData((pastInfo) => ({
      ...pastInfo,
      dificuldade: {
        value: dificuldadeEscolhida,
        error: dificuldadeEscolhida ? "" : "Campo obrigatório",
      },
    }));
  }

  function handleSubmit() {
    onSubmit(formData);
    handleClose();
  }

  function renderDiariaInputs() {
    if (tipo === "DIARIA") {
      return (
        <>
          <div className="custom-input">
            <label>Horário</label>
            <input
              type="time"
              name="hora"
              value={formData.hora.value}
              onChange={handleChange}
            />
          </div>
          <div className="container-dias">
            Dias:
            <section>
              {dias
                ? dias?.map((dia) => {
                    if (
                      formData.dias.value.some((diaData) => diaData === dia.id)
                    ) {
                      return (
                        <button
                          key={dia?.id}
                          onClick={() => {
                            handleSelecionarDia(dia);
                          }}
                          className="box-dia-ativo"
                        >
                          {dia.nome.substring(0, 3)}
                        </button>
                      );
                    }
                    return (
                      <button
                        key={dia.id}
                        onClick={() => {
                          handleSelecionarDia(dia);
                        }}
                        className="box-dia"
                      >
                        {dia.nome.substring(0, 3)}
                      </button>
                    );
                  })
                : null}
            </section>
          </div>
        </>
      );
    }
    return;
  }

  function renderDificuldade(texto, tipo) {
    if (formData.dificuldade.value === tipo) {
      return <button className="select-dificuldade-ativa">{texto}</button>;
    }
    return (
      <button
        onClick={() => {
          handleChangeDificuldade(tipo);
        }}
        className="select-dificuldade"
      >
        {texto}
      </button>
    );
  }

  function renderSelectDificuldade() {
    return (
      <section>
        {renderDificuldade("Fácil", "FACIL")}
        {renderDificuldade("Médio", "MEDIO")}
        {renderDificuldade("Difícil", "DIFICIL")}
      </section>
    );
  }

  return (
    <Modal isOpen={isOpen} onRequestClose={handleClose} style={style}>
      <section className="tarefa-modal">
        <header>
          <h1>{titulo}</h1>
          <button onClick={handleClose}></button>
        </header>
        <section>
          <Input
            type="text"
            label="Título"
            name="titulo"
            autoComplete="off"
            extraClassInput="tarefa-class-extra"
            value={formData.titulo.value}
            onChange={handleChange}
          />
          <Input
            type="text"
            label="Descrição"
            name="descricao"
            autoComplete="off"
            value={formData.descricao.value}
            onChange={handleChange}
          />
          <div className="custom-input">
            <label>Dificuldade</label>
            {renderSelectDificuldade()}
          </div>
          {renderDiariaInputs()}
          <ButtonSecondary onClick={handleSubmit}>Criar</ButtonSecondary>
        </section>
      </section>
    </Modal>
  );
}
