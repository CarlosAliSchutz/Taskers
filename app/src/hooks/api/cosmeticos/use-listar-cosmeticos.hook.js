import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { COSMETICOS_API_PREFIX } from "../../../helpers/constraints";

export function useListarCosmeticos() {
  const { handleRequest, data, isLoading } = useRequest();

  function listarCosmeticosAdquiridos() {
    handleRequest(axiosInstance.get(`${COSMETICOS_API_PREFIX}/adquiridos?size=44`));
  }

  return { listarCosmeticosAdquiridos, cosmeticos: data?.content, loadingItem: isLoading };
}


