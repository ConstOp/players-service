package com.players.balldontlie.dto;

import lombok.Data;

@Data
public class TeamDto {
    Long id;
    String conference;
    String division;
    String city;
    String name;
    String full_name;
    String abbreviation;
}
