import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { USUARIO_API_PREFIX } from "../../../helpers/constraints";

export function useUsuario() {
  const { handleRequest, data, error, isLoading } = useRequest();

  function listarUsuario() {
    const response = handleRequest(
      axiosInstance.get(`${USUARIO_API_PREFIX}/me`)
    );
    return response;
  }

  function refreshUsuario() {
    listarUsuario()
  }

  return { listarUsuario, refreshUsuario, usuario: data, error: error?.status, loadingUsuario: isLoading };
}
