import { useEffect, useState } from "react";
import Modal from "react-modal";
import { toast } from "react-toastify";
import Stomp from "stompjs";
import notificacaoLogo from "../../../assets/icon/taskcoin.svg";
import { NOTIFICACAO_MODAL_STYLE } from "../../../constants";
import { useModal, useNotification, usePagination } from "../../../hooks";
import { useListarNotificacoes } from "../../../hooks/api";
import { BoxNotificacao } from "../../components";
import "./index.css";

export function BarraNotificacao({ isOpen, setIsOpen }) {
  const { backPage, nextPage } = usePagination();
  const [page, setPage] = useState(0);
  const { notificacoes, listarNotificacoes, refreshNotificacoes } =
    useListarNotificacoes();
  const { closeModal } = useModal();
  const socket = new WebSocket("ws://localhost:8080/ws");
  const stompClient = Stomp.over(socket);
  const { usarNotificacao } = useNotification();

  useEffect(() => {
    listarNotificacoes(page);
  }, [page, isOpen]);

  useEffect(() => {
    stompClient.connect({}, function () {
      stompClient.subscribe("/user/notificacao", function (message) {
        handlePushNotificacao(message.body);
      });
    });
  }, []);

  async function handlePushNotificacao(message) {
    await usarNotificacao({
      title: "Taskers",
      body: message,
      icon: notificacaoLogo,
    });
    toast(message, {
      position: "top-right",
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "dark",
      icon: "🔔",
    });
    refreshNotificacoes(page);
  }

  function handleClose() {
    closeModal(setIsOpen);
  }

  function handleRefresh() {
    refreshNotificacoes(page);
  }

  function renderSetaEsquerda(page, backFunction) {
    if (page > 0) {
      return (
        <span className="notificacao-seta-esquerda">
          <button onClick={backFunction}></button>
        </span>
      );
    }
    return (
      <span className="notificacao-seta-vazia">
        <button></button>
      </span>
    );
  }

  function renderSetaDireita(size, nextFunction) {
    if (size === 20) {
      return (
        <span className="notificacao-seta-direita">
          <button onClick={nextFunction}></button>
        </span>
      );
    }
    return (
      <span className="notificacao-seta-vazia">
        <button></button>
      </span>
    );
  }

  function renderPage(size, page) {
    const numeroMaximoNotificacoesPorPagina = 20;
    if (size === numeroMaximoNotificacoesPorPagina || page > 0) {
      const actualPage = Number(page) + 1;
      return <span>{actualPage}</span>;
    }
  }

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={handleClose}
      style={NOTIFICACAO_MODAL_STYLE}
    >
      <section className="barra-notificacao">
        <header>
          {renderSetaEsquerda(page, () => {
            backPage(setPage);
          })}
          <h1>
            Notificações
            {renderPage(notificacoes?.length, page)}
          </h1>
          {renderSetaDireita(notificacoes?.length, () => {
            nextPage(setPage);
          })}
        </header>
        <section>
          {notificacoes
            ? notificacoes.map((notificacao, index) => {
                return (
                  <BoxNotificacao
                    key={index}
                    id={notificacao.id}
                    titulo={notificacao.titulo}
                    descricao={notificacao.texto}
                    handleRefresh={handleRefresh}
                  />
                );
              })
            : null}
        </section>
      </section>
    </Modal>
  );
}
