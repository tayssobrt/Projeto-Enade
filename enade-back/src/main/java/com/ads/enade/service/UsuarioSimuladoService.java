package com.ads.enade.service;

import com.ads.enade.dto.questao.UsuarioQuestaoDTOResponse;
import com.ads.enade.dto.simulado.RespostaUsuarioSimulado;
import com.ads.enade.dto.simulado.UsuarioSimuladoOverview;
import com.ads.enade.dto.user.UsuarioSimuladoDTOResponse;
import com.ads.enade.entity.Simulado;
import com.ads.enade.entity.Usuario;
import com.ads.enade.entity.UsuarioSimulado;
import com.ads.enade.exception.ResourceNotFoundException;
import com.ads.enade.mapper.UsuarioSimuladoMapper;
import com.ads.enade.repository.UsuarioSimuladoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioSimuladoService {

    private final UsuarioSimuladoRepository usuarioSimuladoRepository;
    private final AuthService authService;
    private final UsuarioSimuladoMapper usuarioSimuladoMapper;
    private final UsuarioQuestaoService usuarioQuestaoService;

    public UsuarioSimuladoDTOResponse registrarSimuladoAoUsuario(Usuario usuario, Simulado simulado){

        log.info("Iniciando processo de registro de um simulado para o usuário: [{}]", usuario.getUsername());

        UsuarioSimulado novoUsuarioSimulado = UsuarioSimulado.builder()
                .usuario(usuario)
                .simulado(simulado)
                .quantidadeDeQuestoes(simulado.getQuantidadeDeQuestoes())
                .build();

        UsuarioSimulado usuarioSimuladoSalvo = usuarioSimuladoRepository.save(novoUsuarioSimulado);

        log.info("Simulado do usuário salvo com sucesso...");

        return usuarioSimuladoMapper.toDTO(usuarioSimuladoSalvo);
    }

    public List<UsuarioSimuladoDTOResponse> buscarSimuladosUsuario(){

        Usuario usuarioAutenticado = authService.me();

        log.info("Iniciando buscar de simulados do usuário: [{}]", usuarioAutenticado.getUsername());

        List<UsuarioSimulado> simuladosUsuario = usuarioSimuladoRepository.buscarTodosOsSimuladosDoUsuario(usuarioAutenticado);

        if (simuladosUsuario.isEmpty()) return List.of();

        log.info("Simulados encontrados, com um total de [{}]", simuladosUsuario.size());

        return simuladosUsuario
                .stream()
                .map(usuarioSimuladoMapper::toDTO)
                .toList();
    }

    @Transactional
    public UsuarioQuestaoDTOResponse registrarRespostaDoUsuarioNoSimulado(RespostaUsuarioSimulado dto){

        log.info("Iniciando processo de registro de resposta do usuário...");

        UsuarioSimulado usuarioSimulado = buscarSimuladoDoUsuarioAutenticado(dto.idUsuarioSimulado());

        UsuarioQuestaoDTOResponse questaoRespondida = usuarioQuestaoService
                .registrarRespostaDoUsuario(usuarioSimulado, dto.idQuestao(), dto.idAlternativa());

        if (questaoRespondida.isAcerto()) usuarioSimulado.adicionarQuantidadeDeAcertos();
        usuarioSimulado.adicionarQuantidadeDeRespostas();

        if (usuarioSimulado.getQuantidadeDeRespostas().equals(usuarioSimulado.getQuantidadeDeQuestoes())){
            log.error("Erro: está é a ultima questão do usuário, use o método exclusivo para finalização!");
            throw new IllegalArgumentException("Processo interrompido, está é a ultima questão do simulado, utilize o endpoint de finalização");
        }

        usuarioSimuladoRepository.save(usuarioSimulado);

        log.info("Resposta salva com sucesso...");

        return questaoRespondida;
    }

    @Transactional
    public UsuarioSimuladoOverview finalizarSimuladoUsuario(RespostaUsuarioSimulado dto){

        log.info("Iniciando processo de finalização do simulado do usuário...");

        UsuarioSimulado usuarioSimulado = buscarSimuladoDoUsuarioAutenticado(dto.idUsuarioSimulado());

        UsuarioQuestaoDTOResponse questaoRespondida = usuarioQuestaoService
                .registrarRespostaDoUsuario(usuarioSimulado, dto.idQuestao(), dto.idAlternativa());

        if (questaoRespondida.isAcerto()) usuarioSimulado.adicionarQuantidadeDeAcertos();
        usuarioSimulado.adicionarQuantidadeDeRespostas();

        if (!usuarioSimulado.getQuantidadeDeRespostas().equals(usuarioSimulado.getQuantidadeDeQuestoes())){
            log.error("O usuário ainda não respondeu todas as questões da prova");
            throw new IllegalArgumentException("O usuário ainda não respondeu todas as questões da prova");
        }

        Double notaUsuario = calcularNotaUsuario(usuarioSimulado.getQuantidadeDeQuestoes(),usuarioSimulado.getQuantidadeAcertos());

        // Atualiza os dados do simulado do usuário para a conclusão
        usuarioSimulado.adicionarNota(notaUsuario);
        usuarioSimulado.setDataConclusao(LocalDate.now());
        usuarioSimulado.setFinalizado(true);

        usuarioSimuladoRepository.save(usuarioSimulado);

        log.info("Prova finalizada com sucesso...");

        return new UsuarioSimuladoOverview(
                usuarioSimulado.getId(),
                usuarioSimulado.getQuantidadeAcertos(),
                usuarioSimulado.getQuantidadeDeRespostas(),
                usuarioSimulado.getQuantidadeDeQuestoes(),
                notaUsuario,
                usuarioSimulado.getDataConclusao()
        );
    }

    private Double calcularNotaUsuario(Integer quantidadeDeQuestoes, Integer quantidadeDeAcertos){

        if (quantidadeDeQuestoes <= 0) {
            log.error("Quantidade de questões inválida para cálculo: [{}]", quantidadeDeQuestoes);
            throw new IllegalArgumentException("A prova deve possuir questões.");
        }

        if (quantidadeDeAcertos < 0 || quantidadeDeAcertos > quantidadeDeQuestoes) {
            log.error("Quantidade de acertos inválida. Quantidade de acertos: [{}] | Quantidade de questões: [{}]", quantidadeDeAcertos,quantidadeDeQuestoes);
            throw new IllegalArgumentException("Quantidade de acertos inválida.");
        }

        log.info("Iniciando cálculo de nota para o usuário...");

        Double notaUsuario = BigDecimal
                .valueOf(((double) quantidadeDeAcertos / quantidadeDeQuestoes) * 10)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        log.info("Nota calculada com sucesso = [{}]", notaUsuario);

        return notaUsuario;
    }

    private UsuarioSimulado buscarSimuladoDoUsuarioAutenticado(Long idUsuarioSimulado){

        Usuario usuarioAutenticado = authService.me();

        UsuarioSimulado usuarioSimulado = usuarioSimuladoRepository.buscarSimuladoDoUsuario(idUsuarioSimulado, usuarioAutenticado)
                .orElseThrow(() -> new ResourceNotFoundException("Simulado do usuário: "+usuarioAutenticado.getUsername()+" não encontrado"));

        return usuarioSimulado;
    }
}
