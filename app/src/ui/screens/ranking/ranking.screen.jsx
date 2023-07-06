import { useEffect, useState } from "react";
import UsuarioPadrao from "../../../assets/icon/usuario-padrao.svg";
import { COSMETICOS_KEY, USER_KEY } from "../../../constants";
import useGlobalUser from "../../../context/user/user.context";
import { useListarRanking, useUsuario } from "../../../hooks/api";
import { Header } from "../../components";
import "./index.css";

export function RankingScreen() {
  const { listarUsuario, usuario, error } = useUsuario();
  const [, setUser] = useGlobalUser();
  const { listarRanking, ranking } = useListarRanking();
  const [pesquisarNome, setPesquisarNome] = useState("");
  const [pesquisaAberto, setPesquisaAberto] = useState(false);
  const USUARIOS_NAO_ENCONTRADOS = 0;

  useEffect(() => {
    listarRanking(pesquisarNome);
  }, [pesquisarNome]);

  useEffect(() => {
    listarUsuario();
  }, []);

  useEffect(() => {
    if (error === 504 || error === 500) {
      setUser(null);
      localStorage.removeItem(USER_KEY);
      localStorage.removeItem(COSMETICOS_KEY);
    }
  }, [error]);

  function renderImageUsuario(image) {
    if (image) {
      return <img src={image} alt="Foto de perfil do usuário" />;
    } else {
      return (
        <img
          src={UsuarioPadrao}
          alt="Foto padrão, caso o usuário não tenha foto de perfil"
        />
      );
    }
  }

  return (
    <>
      <Header paginaAtual={"ranking"} />
      <section className="ranking">
        <h1>Ranking geral de usuários!</h1>
        <h2>Classificado por experiências adquiridas</h2>
        <p>Sua Experiência: {usuario?.experiencia} XP</p>
        <div className="pesquisa-ranking">
          <span
            className="lupa-ranking"
            onClick={() => setPesquisaAberto(!pesquisaAberto)}
          />
          {pesquisaAberto && (
            <input
              className="input-pesquisa-ranking"
              type="text"
              placeholder="Procurar pelo nome..."
              value={pesquisarNome}
              onChange={(event) => setPesquisarNome(event.target.value)}
            />
          )}
        </div>
        {ranking &&
          ranking?.map((usuario) => (
            <div key={usuario?.posicaoRanking} className="ranking-usuarios">
              <div className="usuario-experiencia">
                {renderImageUsuario(usuario?.imagemPerfil)}
                <span className="ranking-posicao">
                  {usuario?.posicaoRanking}º
                </span>
                <h1>{usuario?.nomeCompleto}</h1>
              </div>
              <div className="experiencia-ranking">
                <h1>{usuario?.experiencia} XP</h1>
              </div>
            </div>
          ))}
        {ranking?.length === USUARIOS_NAO_ENCONTRADOS && (
          <div className="ranking-sem-usuarios">
            <h1>Usuário não encontrado</h1>
          </div>
        )}
      </section>
    </>
  );
}
