package com.players.service;

import com.players.balldontlie.dto.PlayerDto;
import com.players.model.Player;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PlayerService {

    ResponseEntity<?> getPlayers();
}
