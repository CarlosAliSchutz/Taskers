import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { HABITOS_API_PREFIX } from "../../../helpers/constraints";

export function useListarHabitos() {
  const { handleRequest, data } = useRequest();

  function listarHabitos(text, page) {
    const response = handleRequest(
      axiosInstance.get(
        `${HABITOS_API_PREFIX}?text=${text}&page=${page}&sort=id,desc`
      )
    );
    return response.data;
  }

  function refreshHabitos(filter, page) {
    listarHabitos(filter, page);
  }

  return { listarHabitos, habitos: data?.content, refreshHabitos };
}
