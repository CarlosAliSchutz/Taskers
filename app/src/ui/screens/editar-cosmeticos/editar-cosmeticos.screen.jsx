import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Flecha from "../../../assets/icon/arrow.svg";
import Filtro from "../../../assets/icon/filtro.svg";
import { COSMETICOS } from "../../../constants";
import {
  useListarCosmeticos,
  useListarCosmeticosEquipados,
} from "../../../hooks/api";
import { ButtonPrimary, CosmeticosEquipados, Header } from "../../components";
import { BoxItem } from "../../components/box-item/box-item.component.jsx";
import "./index.css";

export function EditarCosmeticosScreen() {
  const [filtroAberto, setFiltroAberto] = useState(false);
  const { cosmeticos, listarCosmeticosAdquiridos } = useListarCosmeticos();
  const {
    cosmeticosEquipados,
    listarCosmeticosEquipados,
    refreshCosmeticosEquipados,
  } = useListarCosmeticosEquipados();
  const [filtro, setFiltro] = useState("TODOS");
  const [itemFiltrado, setItemFiltrado] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    listarCosmeticosAdquiridos();
    listarCosmeticosEquipados();
  }, []);

  useEffect(() => {
    if (cosmeticos) {
      if (filtro === "TODOS") {
        setItemFiltrado(cosmeticos);
      }
      if (filtro !== "TODOS") {
        const filtrandoItem = cosmeticos?.filter((item) =>
          filtro?.includes(item.tipo)
        );
        setItemFiltrado(filtrandoItem);
      }
    }
  }, [cosmeticos, filtro]);

  function renderFundo() {
    return cosmeticosEquipados.find(
      (cosmetico) => cosmetico?.tipo === "CENARIO"
    )?.nome;
  }

  function renderPlanta() {
    return cosmeticosEquipados.find((cosmetico) => cosmetico?.tipo === "PLANTA")
      ?.nome;
  }

  function renderRoupa() {
    return cosmeticosEquipados.find((cosmetico) => cosmetico?.tipo === "ROUPA")
      ?.nome;
  }

  function renderPet() {
    return cosmeticosEquipados.find((cosmetico) => cosmetico?.tipo === "PET")
      ?.nome;
  }

  function handleBack() {
    navigate(-1);
  }

  function renderCosmeticos() {
    return itemFiltrado.map((cosmetico) => {
      const desabilitarCosmeticosEquipados = cosmeticosEquipados?.filter(
        (equipado) => cosmetico.nome?.includes(equipado?.nome)
      );
      return (
        <BoxItem
          key={cosmetico.id}
          id={cosmetico.id}
          nome={cosmetico.nome}
          handleRefresh={refreshCosmeticosEquipados}
          disabled={
            desabilitarCosmeticosEquipados?.length
              ? desabilitarCosmeticosEquipados?.length > 0 && true
              : false
          }
        />
      );
    });
  }

  return (
    <>
      <Header paginaAtual={"cosmeticos"} />
      <main className="tela-cosmeticos">
        <section className="cosmeticos">
          <div className="cosmeticos-align">
            <div>
              <header
                onClick={() => setFiltroAberto(!filtroAberto)}
                className="cosmeticos-filtro"
              >
                <h1>Cosméticos</h1>
                <div>
                  <img src={Filtro} alt="filtro" /> Filtros{" "}
                  <img src={Flecha} alt="flecha" />
                </div>
              </header>
            </div>
          </div>
          <div className="cosmeticos-itens">{renderCosmeticos()}</div>
          <footer>
            <ButtonPrimary onClick={handleBack}>Voltar</ButtonPrimary>
          </footer>
        </section>
        {cosmeticosEquipados ? (
          cosmeticosEquipados.length > 0 ? (
            <CosmeticosEquipados
              fundo={COSMETICOS[renderFundo()]}
              planta={COSMETICOS[renderPlanta()]}
              roupa={COSMETICOS[renderRoupa()]}
              pet={COSMETICOS[renderPet()]}
            />
          ) : null
        ) : null}
        {filtroAberto && (
          <div className="editar-opcoes-filtro">
            <ul>
              <li onClick={() => setFiltro("TODOS")}>Todos</li>
              <span className="editar-opcoes-divisao" />
              <li onClick={() => setFiltro("PLANTA")}>Plantas</li>
              <span className="editar-opcoes-divisao" />
              <li onClick={() => setFiltro("PET")}>Pets</li>
              <span className="editar-opcoes-divisao" />
              <li onClick={() => setFiltro("ROUPA")}>Roupas</li>
              <span className="editar-opcoes-divisao" />
              <li onClick={() => setFiltro("CENARIO")}>Cenários</li>
            </ul>
          </div>
        )}
      </main>
    </>
  );
}
