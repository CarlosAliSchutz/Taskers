import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { DIARIAS_API_PREFIX } from "../../../helpers/constraints";

export function useRealizarDiaria() {
  const { handleRequest } = useRequest();

  function realizarDiaria(id) {
    const response = handleRequest(
      axiosInstance.put(`${DIARIAS_API_PREFIX}/${id}/realizar`)
    );
    return response;
  }

  return { realizarDiaria };
}
