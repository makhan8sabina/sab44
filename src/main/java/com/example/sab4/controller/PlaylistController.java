package com.example.sab4.controller;

import com.example.sab4.domain.Playlist;
import com.example.sab4.domain.Song;
import com.example.sab4.repository.PlaylistRepository;
import com.example.sab4.repository.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistController(PlaylistRepository playlistRepository,
                              SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    // GET /playlists
    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    // POST /playlists
    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    // POST /playlists/{playlistId}/songs/{songId}
    @PostMapping("/{playlistId}/songs/{songId}")
    public Playlist addSongToPlaylist(@PathVariable Long playlistId,
                                      @PathVariable Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        playlist.getSongs().add(song);
        return playlistRepository.save(playlist);
    }
}
