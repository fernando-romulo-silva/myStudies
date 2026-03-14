package med.voll.api.domain.medico.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.dto.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
