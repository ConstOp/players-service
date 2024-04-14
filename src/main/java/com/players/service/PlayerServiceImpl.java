package com.players.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.players.balldontlie.BalldontlieGetAllPlayers;
import com.players.balldontlie.dto.PlayerDto;
import com.players.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    final BalldontlieGetAllPlayers balldontlieGetAllPlayers;

    @Override
    public ResponseEntity<?> getPlayers()  {
        try {
            File csvFile = new ClassPathResource("allPlayers.csv").getFile();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", csvFile.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(csvFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
