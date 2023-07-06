import { useState } from "react";
import { axiosInstance } from "../../../api/_base/axios-instance";
import { USUARIO_API_PREFIX } from "../../../helpers/constraints";

export function useEditarSenha() {
  const [error, setError] = useState(null);

  async function editarSenha(senhaAtual, novaSenha) {
    let alterado = false;
    try {
      await axiosInstance.put(`${USUARIO_API_PREFIX}/editar-senha`, {
        senhaAtual: senhaAtual,
        novaSenha: novaSenha,
      });
      alterado = true;
    } catch (error) {
      setError(error.response.data.message);
      alterado = false;
    }
    return alterado;
  }

  return { editarSenha, error };
}
