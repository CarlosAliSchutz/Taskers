import "./index.css";

export function Input({
  label,
  name,
  value,
  onChange,
  link,
  to,
  extraClassInput,
  ...props
}) {
  return (
    <div>
      <div className={`text-input ${extraClassInput}`}>
        <label>{label}</label>
        <a onClick={to} className="link">
          {link}
        </a>
      </div>
      <input
        className="input"
        name={name}
        value={value}
        onChange={onChange}
        {...props}
      />

    </div>
  );
}
