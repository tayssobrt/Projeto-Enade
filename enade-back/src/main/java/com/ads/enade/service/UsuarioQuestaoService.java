package com.ads.enade.service;

import com.ads.enade.dto.questao.UsuarioQuestaoDTOResponse;
import com.ads.enade.entity.Alternativa;
import com.ads.enade.entity.Questao;
import com.ads.enade.entity.UsuarioQuestao;
import com.ads.enade.entity.UsuarioSimulado;
import com.ads.enade.exception.ResourceNotFoundException;
import com.ads.enade.mapper.UsuarioQuestaoMapper;
import com.ads.enade.repository.AlternativaRepository;
import com.ads.enade.repository.QuestaoRepository;
import com.ads.enade.repository.UsuarioQuestaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioQuestaoService {

    private final UsuarioQuestaoRepository usuarioQuestaoRepository;
    private final QuestaoRepository questaoRepository;
    private final AlternativaRepository alternativaRepository;
    private final UsuarioQuestaoMapper usuarioQuestaoMapper;

    @Transactional
    public UsuarioQuestaoDTOResponse registrarRespostaDoUsuario(UsuarioSimulado usuarioSimulado, Long idQuestao, Long idAlternativa){

        if (usuarioQuestaoRepository.verificarQuestaoJaRespondida(usuarioSimulado, idQuestao).isPresent()) {
            throw new IllegalArgumentException("Usuário não pode responder a mesma questão no mesmo simulado");
        }

        log.info("Iniciando processo de registro de resposta do usuário ao simulado com id: [{}]", usuarioSimulado.getId());

        Questao questao = questaoRepository.findById(idQuestao)
                .orElseThrow(() -> new ResourceNotFoundException("Questao com id: "+idQuestao+" não encontrada"));

        Alternativa alternativa = alternativaRepository.findById(idAlternativa)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa com id: "+idAlternativa+" não encontrada"));

        UsuarioQuestao usuarioQuestaoParaSalvar = UsuarioQuestao.builder()
                .usuarioSimulado(usuarioSimulado)
                .questao(questao)
                .alternativa(alternativa)
                .dataResposta(LocalDate.now())
                .isAcerto(alternativa.getIsCorreta())
                .build();

        UsuarioQuestao usuarioQuestaoSalva = usuarioQuestaoRepository.save(usuarioQuestaoParaSalvar);

        return usuarioQuestaoMapper.toDTO(usuarioQuestaoSalva);
    }
}
