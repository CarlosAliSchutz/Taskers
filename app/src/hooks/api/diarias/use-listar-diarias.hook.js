import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { DIARIAS_API_PREFIX } from "../../../helpers/constraints";

export function useListarDiarias() {
  const { handleRequest, data } = useRequest();

  function listarDiarias(text, page) {
    const response = handleRequest(
      axiosInstance.get(
        `${DIARIAS_API_PREFIX}?text=${text}&page=${page}&sort=id,desc`
      )
    );
    return response.data;
  }

  function refreshDiarias(filter, page) {
    listarDiarias(filter, page);
  }

  return { listarDiarias, diarias: data?.content, refreshDiarias };
}
