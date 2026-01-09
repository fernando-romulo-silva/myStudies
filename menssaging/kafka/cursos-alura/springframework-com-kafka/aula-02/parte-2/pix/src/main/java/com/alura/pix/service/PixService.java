package com.alura.pix.service;

import com.alura.pix.dto.PixDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixService {

    private final KafkaTemplate<String, PixDTO>  kafkaTemplate;

    public PixDTO salvarPix(PixDTO pixDTO) {
        kafkaTemplate.send("pix-topic", pixDTO);
        return pixDTO;
    }

}
