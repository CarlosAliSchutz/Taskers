import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { NOTIFICACOES_API_PREFIX } from "../../../helpers/constraints";

export function useListarNotificacoes() {
  const { handleRequest, data } = useRequest();

  function listarNotificacoes(page) {
    const response = handleRequest(
      axiosInstance.get(`${NOTIFICACOES_API_PREFIX}?&page=${page}&sort=id,desc`)
    );
    return response.data;
  }

  function refreshNotificacoes(page) {
    listarNotificacoes(page);
  }

  return {
    listarNotificacoes,
    refreshNotificacoes,
    notificacoes: data?.content,
  };
}
