import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { AFAZERES_API_PREFIX } from "../../../helpers/constraints";

export function useFinalizarAfazer() {
  const { handleRequest } = useRequest();

  function finalizarAfazer(id) {
    const response = handleRequest(
      axiosInstance.put(`${AFAZERES_API_PREFIX}/${id}/finalizar`)
    );
    return response;
  }

  return { finalizarAfazer };
}
