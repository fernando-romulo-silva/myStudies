package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.dto.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosDetalhamentoConsulta;
import med.voll.api.domain.consulta.dto.DadosListagemConsulta;
import med.voll.api.domain.consulta.dto.DadosRelatorioConsultaMensal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaConsultaService agendaConsultas;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var detalhesAgendamento = agendaConsultas.agendar(dados);
        return ResponseEntity.ok(detalhesAgendamento);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsulta>> listarProximasConsultas(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var proximasConsultas = consultaRepository.findAllByDataGreaterThan(LocalDateTime.now(), paginacao).map(DadosListagemConsulta::new);
        return ResponseEntity.ok(proximasConsultas);
    }

    @GetMapping("/relatorio-mensal/{mes}")
    public ResponseEntity<List<DadosRelatorioConsultaMensal>> gerarRelatorioConsultaMensal(@PathVariable YearMonth mes) {
        //var relatorio = consultaRepository.?
        return ResponseEntity.ok(null);
    }

}
