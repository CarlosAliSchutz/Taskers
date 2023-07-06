import { useState } from "react";

const errorMessage = "Campo obrigatório";
export function useForm(initialFormData) {
  const [formData, setFormData] = useState(initialFormData);

  function handleChange({ target }) {
    const fieldValue = target.value;
    const fieldName = target.name;
    setFormData((pastInfo) => ({
      ...pastInfo,
      [fieldName]: {
        value: fieldValue,
        error: fieldValue ? "" : errorMessage,
      },
    }));
  }

  function validateForm() {
    const validFormData = Object.entries(formData).map(
      ([fieldName, fieldValue]) => {
        const warningMessage =
          fieldValue.value.length === 0 ? errorMessage : "";
        return [
          fieldName,
          {
            value: fieldValue.value,
            error: warningMessage,
          },
        ];
      }
    );

    const isEveryFieldValid = validFormData.every(([, field]) => {
      return field.error === "";
    });
    const formDataObj = Object.fromEntries(validFormData);
    setFormData(formDataObj);

    return isEveryFieldValid;
  }

  return { formData, handleChange, validateForm, setFormData };
}
