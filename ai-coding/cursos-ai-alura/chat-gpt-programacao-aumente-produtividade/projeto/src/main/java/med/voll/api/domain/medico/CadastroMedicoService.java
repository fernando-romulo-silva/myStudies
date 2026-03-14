package med.voll.api.domain.medico;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.dto.DadosCadastroMedico;
import med.voll.api.domain.medico.dto.DadosDetalhamentoMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroMedicoService {

    @Autowired
    private MedicoRepository repository;

    public DadosDetalhamentoMedico cadastrar(DadosCadastroMedico dados) {
        var jaCadastrado = repository.existsByEmailOrCrm(dados.email(), dados.crm());
        if (jaCadastrado) {
            throw new ValidacaoException("Já existe outro médico com o email ou crm informado!");
        }

        var medico = new Medico(dados);
        repository.save(medico);
        return new DadosDetalhamentoMedico(medico);
    }

}
