package com.players.balldontlie.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
   public List<PlayerDto> data;
   public MetaDto meta;
}
