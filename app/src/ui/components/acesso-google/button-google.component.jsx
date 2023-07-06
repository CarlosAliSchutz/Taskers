import GoogleIcon from "../../../assets/icon/Google.png";
import "./index.css";

export function ButtonGoogle() {
  const LINK_GOOGLE = "http://localhost:8080/oauth2/authorization/google";

  return (
    <a className="button-google" href={LINK_GOOGLE}>
      <img className="icon-google" src={GoogleIcon} alt="icone do Google" />
      Logar com o Google
    </a>
  );
}
