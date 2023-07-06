import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { COSMETICOS_API_PREFIX } from "../../../helpers/constraints";

export function useListarCosmeticosEquipados() {
  const { handleRequest, data } = useRequest();

  function listarCosmeticosEquipados() {
    handleRequest(axiosInstance.get(`${COSMETICOS_API_PREFIX}/equipados`));
  }

  function refreshCosmeticosEquipados() {
    listarCosmeticosEquipados();
  }

  return {
    cosmeticosEquipados: data,
    listarCosmeticosEquipados,
    refreshCosmeticosEquipados,
  };
}
