package com.example.sab4.controller;

import com.example.sab4.domain.Artist;
import com.example.sab4.domain.Song;
import com.example.sab4.repository.ArtistRepository;
import com.example.sab4.repository.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongController(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    // GET /songs - получить все песни
    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // POST /songs - создать песню с привязкой к артисту
    @PostMapping
    public Song createSong(@RequestBody SongRequest request) {
        Optional<Artist> artistOpt = artistRepository.findById(request.getArtistId());
        if (artistOpt.isEmpty()) {
            throw new RuntimeException("Artist not found with id: " + request.getArtistId());
        }

        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setDuration(request.getDuration());
        song.setArtist(artistOpt.get());

        return songRepository.save(song);
    }

    // Вспомогательный класс для запроса JSON
    public static class SongRequest {
        private String title;
        private int duration;
        private Long artistId;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public int getDuration() { return duration; }
        public void setDuration(int duration) { this.duration = duration; }

        public Long getArtistId() { return artistId; }
        public void setArtistId(Long artistId) { this.artistId = artistId; }
    }
}
