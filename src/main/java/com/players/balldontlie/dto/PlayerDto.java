package com.players.balldontlie.dto;

import lombok.Data;

@Data
public class PlayerDto {
    Long id;
    String first_name;
    String last_name;
    String position;
    String height;
    String weight;
    String jersey_number;
    String college;
    String country;
    String draft_year;
    String draft_round;
    String draft_number;
    TeamDto team;
}

