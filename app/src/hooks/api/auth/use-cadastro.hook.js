import { useState } from "react";
import { toast } from "react-toastify";
import { axiosInstance } from "../../../api/_base/axios-instance";
import { AUTH_API_PREFIX } from "../../../helpers/constraints";

export function useCadastro() {
  const [error, setError] = useState(null);

  async function cadastro(nomeCompleto, email, imagemPerfil, senha) {
    let cadastrado = false;
    try {
      setError(null);
      await axiosInstance.post(`${AUTH_API_PREFIX}/registrar`, {
        nomeCompleto,
        email,
        imagemPerfil,
        senha,
      });
      cadastrado = true;
    } catch (error) {
      setError(error.response.data.message);
      toast.error(error.response.data.message, {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored",
      });
    }
    return cadastrado;
  }

  return { cadastro, error };
}
