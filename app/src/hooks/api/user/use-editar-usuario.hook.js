import { useNavigate } from "react-router-dom";
import { axiosInstance } from "../../../api/_base/axios-instance";
import { useRequest } from "../../../api/_base/use-request.js";
import { USUARIO_API_PREFIX } from "../../../helpers/constraints";

export function useEditarUsuario() {
  const navigate = useNavigate();
  const { handleRequest } = useRequest();

  function editarUsuario(nomeCompleto, email, imagemPerfil) {
    handleRequest(
      axiosInstance.put(`${USUARIO_API_PREFIX}`, {
        nomeCompleto: nomeCompleto,
        email: email,
        imagemPerfil: imagemPerfil,
      })
    );
    navigate("/perfil");
  }

  return { editarUsuario };
}
