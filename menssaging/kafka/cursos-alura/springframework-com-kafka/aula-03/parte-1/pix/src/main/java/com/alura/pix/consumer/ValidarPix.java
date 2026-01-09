package com.alura.pix.consumer;

import com.alura.pix.dto.PixDTO;
import com.alura.pix.dto.PixStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ValidarPix {
/*
    @KafkaListener(topics = "pix-topic", groupId = "group-1")
    public void process(PixDTO pixDTO) {
        System.out.println(pixDTO);

        if (pixDTO.getValor() > 0) {
            pixDTO.setStatus(PixStatus.PROCESSADO);
        } else  {
            pixDTO.setStatus(PixStatus.ERRO);
        }

    }


    @KafkaListener(topics = "pix-topic", groupId = "group-1")
    public void process2(PixDTO pixDTO) {
        System.out.println(pixDTO);

        if (pixDTO.getValor() > 0) {
            pixDTO.setStatus(PixStatus.PROCESSADO);
        } else  {
            pixDTO.setStatus(PixStatus.ERRO);
        }

    }

    @KafkaListener(topics = "pix-topic", groupId = "group-1")
    public void process3(PixDTO pixDTO) {
        System.out.println(pixDTO);

        if (pixDTO.getValor() > 0) {
            pixDTO.setStatus(PixStatus.PROCESSADO);
        } else  {
            pixDTO.setStatus(PixStatus.ERRO);
        }

    }

*/
}
