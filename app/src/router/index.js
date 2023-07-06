import { createBrowserRouter } from "react-router-dom";
import {
  CadastroScreen,
  EditarCosmeticosScreen,
  EditarPerfilScreen,
  LoginScreen,
  PerfilScreen,
  RankingScreen,
  TarefasScreen,
  MudarSenhaScreen,
  ConquistasScreen,
  VerificacaoGoogle
} from "../ui/screens";
import { LojaScreen } from "../ui/screens/loja/loja.screen";
import { PrivateRoute } from "./private-route.component";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginScreen />,
  },
  {
    path: "/cadastro",
    element: <CadastroScreen />,
  },
  {
    path: "/verificacao",
    element: <VerificacaoGoogle />,
  },
  {
    path: "/loja",
    element: <PrivateRoute Screen={LojaScreen} />,
  },
  {
    path: "/perfil",
    element: <PrivateRoute Screen={PerfilScreen} />,
  },
  {
    path: "/editar-perfil",
    element: <PrivateRoute Screen={EditarPerfilScreen} />,
  },
  {
    path: "/editar-cosmeticos",
    element: <PrivateRoute Screen={EditarCosmeticosScreen} />,
  },
  {
    path: "/tarefas",
    element: <PrivateRoute Screen={TarefasScreen} />,
  },
  {
    path: "/ranking",
    element: <PrivateRoute Screen={RankingScreen} />,
  },
  {
    path: "/redefinir-senha/:token",
    element: <PrivateRoute Screen={MudarSenhaScreen} />,
  },
  {
    path: "/conquistas",
    element: <PrivateRoute Screen={ConquistasScreen} />,
  },
]);
