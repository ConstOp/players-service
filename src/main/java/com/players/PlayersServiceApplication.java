package com.players;

import com.players.balldontlie.BalldontlieGetAllPlayers;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class PlayersServiceApplication {
    final BalldontlieGetAllPlayers balldontlieGetAllPlayers;
    private static final long INITIAL_BACKOFF_DELAY = 15 * 1000;
    private long backoffDelay = INITIAL_BACKOFF_DELAY;

    public static void main(String[] args) {

        SpringApplication.run(PlayersServiceApplication.class, args);
    }

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void fetchAndSaveData() {
        try {
            balldontlieGetAllPlayers.saveDataToFile();
        } catch (HttpClientErrorException.TooManyRequests | ResourceAccessException e) {
            try {
                Thread.sleep(backoffDelay);
                backoffDelay *= 2;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
