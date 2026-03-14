package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario,
            LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

    Page<Consulta> findAllByDataGreaterThan(LocalDateTime data, Pageable paginacao);

    @Query("""
            SELECT new med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal(
                m.nome,
                m.crm,
                COUNT(c)
            )
            FROM Consulta c
            JOIN c.medico m
            WHERE c.data >= :inicio
            AND c.data < :fim
            GROUP BY m.nome, m.crm
            """)
    List<DadosRelatorioConsultaMensal> relatorioMensal(
            LocalDateTime inicio,
            LocalDateTime fim);

}
