const validador = new JustValidate("#cadastro-form");

const obrigatorioRule = {
  rule: "required",
  errorMessage: "Este campo é obrigatorio",
};

const sobreNomeRule = {
  validator: (value) => value.trim().split(" ").length >= 2,
  errorMessage: "Por favor insira seu nome completo",
};

const cpfRule = {
  validator: (value) => {
    return !/(\d)\1{10}/.test(value.replace(/\D/g, ""));
  },
  errorMessage: "Os números do CPF estão repetidos",
};

const idadeMinRule = {
  rule: "minNumber",
  value: 18,
  errorMessage: "vc deve ter mais de 18 anos para cadastrar",
};

const senhaRule = {
  rule: "strongPassword",
  errorMessage: "A senha precisa ter 8 digitos ... bla bla",
};

validador
  .addField("#nome", [obrigatorioRule, sobreNomeRule])
  .addField("#cpf", [obrigatorioRule, cpfRule])
  .addField("#idade", [obrigatorioRule, idadeMinRule])
  .addField("#senha", [obrigatorioRule, senhaRule]);
