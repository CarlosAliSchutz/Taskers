import { useEffect, useState } from "react";
import Flecha from "../../../assets/icon/arrow.svg";
import Filtro from "../../../assets/icon/filtro.svg";
import { COSMETICOS_KEY, USER_KEY } from "../../../constants";
import useGlobalUser from "../../../context/user/user.context";
import { useUsuario } from "../../../hooks/api";
import { Header, ItemLoja, TaskCoin } from "../../components";
import "./index.css";

export function LojaScreen() {
  const { listarUsuario, usuario, error } = useUsuario();
  const [filtroAberto, setFiltroAberto] = useState(false);
  const [filtro, setFiltro] = useState("TODOS");
  const [user, setUser] = useGlobalUser();

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

  useEffect(() => {
    if (usuario?.id) {
      setUser(usuario);
    }
  }, [usuario]);

  return (
    <>
      <Header paginaAtual={"loja"} />

      <section className="loja">
        <div className="loja-text">
          <h1>Compre skins e customize seu avatar!</h1>
          <div className="loja-align">
            <div className="loja-taskcoin">
              <h2>Suas moedas:</h2>
              <TaskCoin extraClassImg="coin-rotate" valor={user?.taskcoin} />
            </div>
            <div
              onClick={() => setFiltroAberto(!filtroAberto)}
              className="loja-filtro"
            >
              <img src={Filtro} alt="Ícone de filtro" /> Filtros
              <img src={Flecha} alt="Ícone de flecha apontado para baixo" />
            </div>
          </div>
          <div>
            {filtroAberto && (
              <div className="opcoes-filtro">
                <ul>
                  <li onClick={() => setFiltro("TODOS")}>Todos</li>
                  <span className="opcoes-divisao" />
                  <li onClick={() => setFiltro("PLANTA")}>Plantas</li>
                  <span className="opcoes-divisao" />
                  <li onClick={() => setFiltro("PET")}>Pets</li>
                  <span className="opcoes-divisao" />
                  <li onClick={() => setFiltro("ROUPA")}>Roupas</li>
                  <span className="opcoes-divisao" />
                  <li onClick={() => setFiltro("CENARIO")}>Cenários</li>
                </ul>
              </div>
            )}
          </div>
        </div>
        <div className="loja-itens">
          <ItemLoja filtro={filtro} />
        </div>
      </section>
    </>
  );
}
