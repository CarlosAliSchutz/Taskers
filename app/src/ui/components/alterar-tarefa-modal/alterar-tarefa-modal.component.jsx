import { useEffect } from "react";
import Modal from "react-modal";
import { useForm } from "../../../hooks";
import { useListarDias } from "../../../hooks/api";
import { ButtonPrimary, Input } from "../../components";

export function AlterarTarefaModal({
  id,
  nome,
  descricao,
  dificuldade,
  hora,
  oldDias,
  style,
  isOpen,
  close,
  onSubmit,
  tipo,
}) {
  const { dias, listarDias } = useListarDias();
  const { formData, handleChange, setFormData } = useForm({
    nome: { value: "", error: "" },
    descricao: { value: "", error: "" },
    dificuldade: { value: "", error: "" },
    hora: { value: "", error: "" },
    dias: { value: [], error: "" },
  });

  useEffect(() => {
    if (tipo === "DIARIA") {
      listarDias();
    }
    setFormData({
      nome: { value: nome, error: "" },
      descricao: { value: descricao, error: "" },
      dificuldade: { value: dificuldade, error: "" },
      hora: { value: hora, error: "" },
      dias: { value: [...oldDias], error: "" },
    });
  }, [nome, descricao, dificuldade, hora, oldDias]);

  function handleSelecionarDia(dia) {
    if (formData.dias.value.some((diaData) => diaData.id === dia.id)) {
      const novosDias = formData.dias.value.filter(
        (diaData) => diaData.id !== dia.id
      );
      setFormData((pastInfo) => ({
        ...pastInfo,
        dias: {
          value: novosDias,
          error: "",
        },
      }));
    } else {
      const novosDias = [...formData.dias.value, dia];
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
    onSubmit(id, formData);
    close();
  }

  function renderDiariaInputs() {
    const numeroLetras = 3;
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
                      formData.dias.value.some(
                        (diaData) => diaData.id === dia.id
                      )
                    ) {
                      return (
                        <button
                          key={dia?.id}
                          onClick={() => {
                            handleSelecionarDia(dia);
                          }}
                          className="box-dia-ativo"
                        >
                          {dia.nome.substring(0, numeroLetras)}
                        </button>
                      );
                    }
                    return (
                      <button
                        key={dia?.id}
                        onClick={() => {
                          handleSelecionarDia(dia);
                        }}
                        className="box-dia"
                      >
                        {dia.nome.substring(0, numeroLetras)}
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
      <div>
        {renderDificuldade("Fácil", "FACIL")}
        {renderDificuldade("Médio", "MEDIO")}
        {renderDificuldade("Difícil", "DIFICIL")}
      </div>
    );
  }
  return (
    <Modal isOpen={isOpen} onRequestClose={close} style={style}>
      <section className="tarefa-modal">
        <header>
          <h1>{nome}</h1>
          <button onClick={close} />
        </header>
        <section>
          <Input
            type="text"
            label="Título"
            name="nome"
            value={formData.nome.value}
            onChange={handleChange}
          />
          <Input
            type="text"
            label="Descrição"
            name="descricao"
            value={formData.descricao.value}
            onChange={handleChange}
          />
          <div className="custom-input">
            <label>Dificuldade</label>
            {renderSelectDificuldade()}
          </div>
          {renderDiariaInputs()}
          <ButtonPrimary onClick={handleSubmit}>Alterar</ButtonPrimary>
        </section>
      </section>
    </Modal>
  );
}

AlterarTarefaModal.defaultProps = {
  id: 0,
  nome: "",
  descricao: "",
  dificuldade: "",
  hora: "",
  oldDias: [],
  style: {},
  isOpen: () => {},
  close: () => {},
  onSubmit: () => {},
  tipo: "",
};
