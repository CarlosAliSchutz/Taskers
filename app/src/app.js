import Modal from "react-modal";
import { RouterProvider } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { GlobalCosmeticoProvider } from "./context/cosmetico/cometico.context";
import { GlobalUserProvider } from "./context/user/user.context";
import { router } from "./router";

function App() {
  Modal.setAppElement("#root");

  return (
    <>
      <GlobalUserProvider>
        <GlobalCosmeticoProvider>
          <ToastContainer />
          <RouterProvider router={router} />
        </GlobalCosmeticoProvider>
      </GlobalUserProvider>
    </>
  );
}

export default App;
