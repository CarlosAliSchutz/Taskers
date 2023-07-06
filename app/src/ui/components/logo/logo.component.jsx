import Logo from "../../../assets/img/Logo.png";

export function LogoTaskers({ ...props }) {
  return <img {...props} src={Logo} alt="Logo do Taskers" />;
}
