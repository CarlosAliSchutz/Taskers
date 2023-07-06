import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { DIAS_API_PREFIX } from "../../../helpers/constraints";

export function useListarDias() {
  const { handleRequest, data } = useRequest();

  function listarDias() {
    const response = handleRequest(axiosInstance.get(`${DIAS_API_PREFIX}`));
    return response.data;
  }

  return { listarDias, dias: data?.content };
}
