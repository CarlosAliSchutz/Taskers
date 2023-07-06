import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import editarInventario from "../../../assets/icon/editar-inventario.png";
import editarUsuario from "../../../assets/icon/editar-perfil.png";
import editarSenha from "../../../assets/icon/editar-senha.png";
import logoutIcon from "../../../assets/icon/logout-icon.png";
import notificadaoAtivada from "../../../assets/icon/notificacao-ativada.png";
import notificadaoDesativada from "../../../assets/icon/notificacao-desativada.png";
import UsuarioPadrao from "../../../assets/icon/usuario-padrao.svg";
import xp from "../../../assets/icon/xp-icon.png";
import medalha from "../../../assets/img/medalha-ouro.png";
import { COSMETICOS_KEY, COSMETICOS, USER_KEY } from "../../../constants";
import useGlobalUser from "../../../context/user/user.context";
import { useModal } from "../../../hooks";
import {
  useAlterarNotificacao,
  useLogout,
  useUsuario,
} from "../../../hooks/api";
import {
  CosmeticosEquipados,
  EditarSenha,
  Header,
  TaskCoin,
} from "../../components";
import "./index.css";

export function PerfilScreen() {
  const { listarUsuario, usuario, loadingUsuario, error, refreshUsuario } =
    useUsuario();
  const navigate = useNavigate();
  const { logout } = useLogout();
  const [editarSenhaAberto, setEditarSenhaAberto] = useState(false);
  const { openModal, closeModal } = useModal();
  const [, setUser] = useGlobalUser();
  const [notificacaoAtiva, setNotificacaoAtiva] = useState(
    usuario?.notificacoesPorEmail
  );
  const { alterarNotificacao } = useAlterarNotificacao();

  useEffect(() => {
    listarUsuario();
  }, []);

  useEffect(() => {
    setNotificacaoAtiva(usuario?.notificacoesPorEmail);
  }, [usuario]);

  useEffect(() => {
    if (error === 504 || error === 500) {
      setUser(null);
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(COSMETICOS_KEY);
    }
  }, [error]);

  async function handleNotificacao() {
    await alterarNotificacao();
    refreshUsuario();
  }

  function handleLogout() {
    logout();
    setUser(null);
    localStorage.removeItem(USER_KEY);
    localStorage.removeItem(COSMETICOS_KEY);
    navigate("/");
  }

  function handleNavigateConquistas() {
    navigate("/conquistas");
  }

  function handleEditarPerfil() {
    navigate("/editar-perfil");
  }

  function handleEditarCosmeticos() {
    navigate("/editar-cosmeticos");
  }

  function renderCosmeticosEquipados() {
    const cosmeticoVazio = { nome: "" };

    const fundo = usuario.cosmeticosEquipados.some(
      (cosmetico) => cosmetico.tipo === "CENARIO"
    )
      ? usuario.cosmeticosEquipados.find(
          (cosmetico) => cosmetico.tipo === "CENARIO"
        )
      : cosmeticoVazio;

    const planta = usuario.cosmeticosEquipados.some(
      (cosmetico) => cosmetico.tipo === "PLANTA"
    )
      ? usuario.cosmeticosEquipados.find(
          (cosmetico) => cosmetico.tipo === "PLANTA"
        )
      : cosmeticoVazio;

    const roupa = usuario.cosmeticosEquipados.some(
      (cosmetico) => cosmetico.tipo === "ROUPA"
    )
      ? usuario.cosmeticosEquipados.find(
          (cosmetico) => cosmetico.tipo === "ROUPA"
        )
      : cosmeticoVazio;

    const pet = usuario.cosmeticosEquipados.some(
      (cosmetico) => cosmetico.tipo === "PET"
    )
      ? usuario.cosmeticosEquipados.find(
          (cosmetico) => cosmetico.tipo === "PET"
        )
      : cosmeticoVazio;

    return fundo ? (
      <CosmeticosEquipados
        fundo={COSMETICOS[fundo.nome]}
        planta={COSMETICOS[planta.nome]}
        roupa={COSMETICOS[roupa.nome]}
        pet={COSMETICOS[pet.nome]}
      />
    ) : null;
  }

  function renderImageUsuario(image) {
    if (image) {
      return <img src={image} />;
    } else {
      return (
        <img
          style={{ backgroundColor: "#ffff", border: "solid 1px white" }}
          src={UsuarioPadrao}
        />
      );
    }
  }

  function renderInfoUsuario() {
    return usuario ? (
      <section className="info-perfil">
        <div className="foto-perfil">
          {renderImageUsuario(usuario?.imagemPerfil)}
        </div>
        <h1>{usuario.nomeCompleto}</h1>
        <h2>
          {usuario.experiencia} <img src={xp} alt="Símbolo de xp" />
        </h2>
        <h2>
          {usuario.taskcoin} <TaskCoin />
        </h2>
        <label
          onClick={() => {
            handleNavigateConquistas();
          }}
        >
          <img src={medalha} alt="Medalha" />
          Visualizar conquistas
        </label>
      </section>
    ) : null;
  }

  return (
    <>
      <Header />
      <main className="tela-perfil">
        <section className="menu-perfil">
          <section className="opcoes-perfil-mobile">
            {usuario?.provedor == "LOCAL" && (
              <label onClick={handleEditarPerfil}>
                <img src={editarUsuario} alt="Usuário com lápis ao lado" />
                <h1>Editar Perfil</h1>
              </label>
            )}
            {usuario?.provedor == "LOCAL" && (
              <label
                onClick={() => {
                  openModal(setEditarSenhaAberto);
                }}
              >
                <img src={editarSenha} alt="Usuário com lápis ao lado" />
                <h1>Alterar Senha</h1>
              </label>
            )}
            <EditarSenha
              isOpen={editarSenhaAberto}
              close={() => {
                closeModal(setEditarSenhaAberto);
              }}
            />
            <label onClick={handleEditarCosmeticos}>
              <img src={editarInventario} alt="Usuário com lápis ao lado" />
              <h1>Alterar Cosméticos</h1>
            </label>
            <label onClick={handleNotificacao}>
              {notificacaoAtiva && (
                <>
                  <img
                    src={notificadaoDesativada}
                    alt="Sininho com traço indicando desativado"
                  />
                  <h1>Desativar Notificações por E-mail</h1>
                </>
              )}
              {!notificacaoAtiva && (
                <>
                  <img
                    src={notificadaoAtivada}
                    alt="Sininho com traço indicando desativado"
                  />
                  <h1>Ativar Notificações por E-mail</h1>
                </>
              )}
            </label>
          </section>
          <label className="perfil-logout" onClick={handleLogout}>
            <img src={logoutIcon} alt="Porta de saída" />
            <h1>Logout</h1>
          </label>
        </section>

        {usuario ? renderCosmeticosEquipados() : null}

        {renderInfoUsuario()}
      </main>
    </>
  );
}
