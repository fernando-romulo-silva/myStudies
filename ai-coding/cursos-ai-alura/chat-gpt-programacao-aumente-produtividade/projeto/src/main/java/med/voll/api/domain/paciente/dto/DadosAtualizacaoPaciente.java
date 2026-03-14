package med.voll.api.domain.paciente.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.dto.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
