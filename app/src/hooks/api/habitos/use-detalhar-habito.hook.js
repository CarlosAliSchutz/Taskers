import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useDetalharHabito() {
  const { handleRequest, data } = useRequest();

  function detalharHabito(id) {
    const response = handleRequest(
      axiosInstance.get(`${HABITOS_API_PREFIX}/${id}`)
    );
    return response;
  }

  return { detalharHabito, habito: data };
}
