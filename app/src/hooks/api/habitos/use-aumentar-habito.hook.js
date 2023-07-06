import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useAumentarHabito() {
  const { handleRequest } = useRequest();

  function aumentarHabito(id) {
    const response = handleRequest(
      axiosInstance.put(`${HABITOS_API_PREFIX}/${id}/aumentar`)
    );
    return response;
  }

  return { aumentarHabito };
}
