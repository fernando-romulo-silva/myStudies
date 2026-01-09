package com.alura.pix.consumidor;

import com.alura.pix.dto.PixDTO;
import com.alura.pix.dto.PixStatus;
import com.alura.pix.model.Key;
import com.alura.pix.model.Pix;
import com.alura.pix.repository.KeyRepository;
import com.alura.pix.repository.PixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PixValidator {

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic", groupId = "grupo")
    public void processaPix(PixDTO pixDTO, Acknowledgment acknowledgment) {
        System.out.println("Pix  recebido: " + pixDTO.getIdentifier());

        Pix pix = pixRepository.findByIdentifier(pixDTO.getIdentifier());

        Key origem = keyRepository.findByChave(pixDTO.getChaveOrigem());
        Key destino = keyRepository.findByChave(pixDTO.getChaveDestino());

        if (origem == null || destino == null) {
            pix.setStatus(PixStatus.ERRO);
        } else {
            pix.setStatus(PixStatus.PROCESSADO);
        }
        pixRepository.save(pix);
        // acknowledgment.acknowledge();
    }

}
