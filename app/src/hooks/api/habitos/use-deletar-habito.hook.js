import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useDeletarHabito() {
  const { handleRequest } = useRequest();

  function deletarHabito(id) {
    const response = handleRequest(
      axiosInstance.delete(`${HABITOS_API_PREFIX}/${id}`)
    );
    return response;
  }

  return { deletarHabito };
}
