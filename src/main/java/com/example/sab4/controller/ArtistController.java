package com.example.sab4.controller;

import com.example.sab4.domain.Artist;
import com.example.sab4.repository.ArtistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;

    // Spring автоматически подставит репозиторий через конструктор
    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // GET /artists
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // POST /artists
    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistRepository.save(artist);
    }
}
