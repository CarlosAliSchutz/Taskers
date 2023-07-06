import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { DIARIAS_API_PREFIX } from "../../../helpers/constraints";

export function useDetalharDiaria() {
  const { handleRequest, data } = useRequest();

  function detalharDiaria(id) {
    const response = handleRequest(
      axiosInstance.get(`${DIARIAS_API_PREFIX}/${id}`)
    );
    return response;
  }

  return { detalharDiaria, diaria: data };
}
