import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { COSMETICOS_API_PREFIX } from "../../../helpers/constraints";

export function useEquiparCosmetico() {
  const { handleRequest, error } = useRequest();

  function equiparItem(idItem) {
    const response = handleRequest(
      axiosInstance.put(`${COSMETICOS_API_PREFIX}/${idItem}/equipar`)
    );
    return response
  }

  return { equiparItem, error };
}
