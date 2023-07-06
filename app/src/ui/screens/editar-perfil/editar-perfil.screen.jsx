import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import { useForm } from "../../../hooks";
import { useEditarUsuario, useUsuario } from "../../../hooks/api";
import { ButtonSecondary, Input, LogoTaskers } from "../../components";
import "./index.css";

export function EditarPerfilScreen() {
  const { listarUsuario } = useUsuario();
  const { editarUsuario } = useEditarUsuario();
  const navigate = useNavigate();
  const { formData, handleChange, setFormData } = useForm({
    nome: { value: "", error: "" },
    email: { value: "", error: "" },
    foto: { value: "", error: "" },
  });

  useEffect(() => {
    fetchUsuario();
  }, []);

  async function fetchUsuario() {
    const _usuario = await listarUsuario();
    setFormData({
      nome: { value: _usuario.nomeCompleto, error: "" },
      email: { value: _usuario.email, error: "" },
      foto: { value: _usuario.imagemPerfil, error: "" },
    });
  }

  function handleSubmit(event) {
    event.preventDefault();
    editarUsuario(
      formData.nome.value,
      formData.email.value,
      formData.foto.value
    );
  }

  function handleBack() {
    navigate(-1);
  }

  return (
    <section className="editar-perfil">
      <button onClick={handleBack} className={"botao-voltar"}></button>
      <form onSubmit={handleSubmit}>
        <LogoTaskers className="image-logo" />
        <div className="input-editar-perfil">
          <Input
            type="text"
            label="Nome"
            name="nome"
            value={formData.nome.value}
            onChange={handleChange}
          />
        </div>
        <div className="input-editar-perfil">
          <Input
            type="text"
            label="E-mail"
            name="email"
            value={formData.email.value}
            onChange={handleChange}
          />
        </div>
        <div className="input-editar-perfil">
          <Input
            type="text"
            label="Foto"
            name="foto"
            value={formData.foto.value}
            onChange={handleChange}
          />
        </div>
        <div className="input-editar-perfil button-editar-perfil">
          <ButtonSecondary extraClasses={"button-editar-google"} type="submit">
            Editar Perfil
          </ButtonSecondary>
        </div>
      </form>
    </section>
  );
}
