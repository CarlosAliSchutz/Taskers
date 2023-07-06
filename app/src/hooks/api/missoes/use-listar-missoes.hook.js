import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { MISSOES_API_PREFIX } from "../../../helpers/constraints";

export function useListarMissoes() {
  const { handleRequest, data } = useRequest();

  function listarMissoes() {
    const response = handleRequest(axiosInstance.get(`${MISSOES_API_PREFIX}`));
    return response.data;
  }

  return { listarMissoes, missoes: data };
}
