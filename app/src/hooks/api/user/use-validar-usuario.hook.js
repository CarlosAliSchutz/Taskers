import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request";
import { USUARIO_API_PREFIX } from "../../../helpers/constraints";

export function useValidarUsuario() {
  const { handleRequest, data } = useRequest();

  function validarUsuario() {
    const response = handleRequest(
      axiosInstance.get(`${USUARIO_API_PREFIX}/validar`)
    );
    return response;
  }

  return { validarUsuario, usuario: data };
}
