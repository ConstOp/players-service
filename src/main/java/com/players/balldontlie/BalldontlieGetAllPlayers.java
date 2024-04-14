package com.players.balldontlie;

import com.opencsv.CSVWriter;
import com.players.balldontlie.dto.PlayerDto;
import com.players.balldontlie.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalldontlieGetAllPlayers {

    final RestTemplate restTemplate;
    String apiKey = "2df56578-74ca-4f8e-9f30-c66390ad5ee6";

    public List<PlayerDto> fetchDataFromAPI() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", apiKey);
            URI url = new URI("https://api.balldontlie.io/v1/players");
            RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, url);
            ResponseEntity<ResponseDto> response = restTemplate.exchange(request, ResponseDto.class);
            return Objects.requireNonNull(response.getBody()).getData();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDataToFile() {
        String csvFilePath = "src/main/resources/allPlayers.csv";
        try (FileWriter fileWriter = new FileWriter(csvFilePath);
             CSVWriter csvWriter = new CSVWriter(fileWriter, ',', '\'')) {
            List<PlayerDto> players = fetchDataFromAPI();
            List<String[]> data = getStrings(players);
            csvWriter.writeAll(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String[]> getStrings(List<PlayerDto> plrs) {
        List<String[]> records = new ArrayList<>();

        records.add(new String[]{"ID", "First Name", "Last Name",
                "Position", "Height", "Weight", "Jersey Number", "College",
                "Country", "Draft Year", "Draft Round", "Draft Number",
                "Team ID", "Team Conference", "Team Division",
                "Team City", "Team Name", "Team Full Name", "Team Abbreviation"});

        records.addAll(plrs.stream()
                .map(pl -> new String[]{
                        String.valueOf(pl.getId()),
                        pl.getFirst_name(),
                        pl.getLast_name(),
                        pl.getPosition(),
                        pl.getHeight(),
                        pl.getWeight(),
                        pl.getJersey_number(),
                        pl.getCollege(),
                        pl.getCountry(),
                        pl.getDraft_year(),
                        pl.getDraft_round(),
                        pl.getDraft_number(),
                        String.valueOf(pl.getTeam().getId()),
                        pl.getTeam().getConference(),
                        pl.getTeam().getDivision(),
                        pl.getTeam().getCity(),
                        pl.getTeam().getName(),
                        pl.getTeam().getFull_name(),
                        pl.getTeam().getAbbreviation()
                })
                .toList());

        return records;
    }
}
