package med.voll.api.domain.consulta.dto;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, LocalDateTime data, Especialidade especialidade, String medico, String paciente) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getData(), consulta.getMedico().getEspecialidade(), consulta.getMedico().getNome(), consulta.getPaciente().getNome());
    }

}
