package com.example.exam.folder.controller;

import com.example.exam.folder.model.Playlist;
import com.example.exam.folder.model.User;
import com.example.exam.folder.model.UserRole;
import com.example.exam.folder.service.PlaylistService;
import com.example.exam.folder.service.SingService;
import com.example.exam.folder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/playlist")
public class PlaylistMvcController {
    private final PlaylistService playlistService;
    private final UserService userService;
    private final SingService singService;

    public PlaylistMvcController(PlaylistService playlistService, UserService userService, SingService singService) {
        this.playlistService = playlistService;
        this.userService = userService;
        this.singService = singService;
    }

    @GetMapping
    @Secured({UserRole.AsString.USER})
    public String getPlaylists(Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        model.addAttribute("playlists", playlistService.findAllPlaylistsByUser(user.getId()).stream()
                .map(PlaylistDto::new)
                .toList());
        return "playlist";
    }

    @GetMapping("/{id}")
    @Secured({UserRole.AsString.USER})
    public String getSings(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Playlist playlist = playlistService.findPlaylist(id);
        if (!Objects.equals(user.getId(), playlist.getUser().getId())) {
            return "playlist";
        }
        List<SingDto> sings = singService.findAllSings().stream().map(SingDto::new).toList();
        model.addAttribute("singsForSelect", sings);
        model.addAttribute("sings", singService.findSingsByPlaylist(id).stream()
                .map(SingDto::new)
                .toList());
        model.addAttribute("singDto", new SingDto());
        return "playlist-one";
    }

    @PostMapping(value = "/{id}/add")
    @Secured({UserRole.AsString.USER})
    public String addSing(@PathVariable Long id,
                          @RequestParam Long singId) {
        playlistService.addSing(id, singId);
        return "redirect:/playlist/" + id;
    }

    @PostMapping(value = "/{id}/remove/{singId}")
    @Secured({UserRole.AsString.USER})
    public String removeSing(@PathVariable Long id,
                             @PathVariable Long singId) {
        playlistService.deleteSing(id, singId);
        return "redirect:/playlist/" + id;
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    @Secured({UserRole.AsString.USER})
    public String editPlaylist(@PathVariable(required = false) Long id,
                               Model model) {
        if (id == null || id <= 0) {
            List<SingDto> sings = singService.findAllSings().stream()
                    .map(SingDto::new)
                    .toList();
            model.addAttribute("sings", sings);
            model.addAttribute("playlistDto", new PlaylistDto());
        } else {
            model.addAttribute("playlistDto", new PlaylistDto(playlistService.findPlaylist(id)));
        }
        return "playlist-edit";
    }

    @PostMapping(value = "/{id}")
    @Secured({UserRole.AsString.USER})
    public String editPlaylist(@PathVariable Long id,
                               @ModelAttribute @Valid PlaylistDto playlistDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "playlist-edit";
        }
        playlistDto.setId(id);
        playlistService.updatePlaylist(playlistDto);

        return "redirect:/playlist";
    }

    @PostMapping(value = "/")
    @Secured({UserRole.AsString.USER})
    public String savePlaylist(@ModelAttribute @Valid PlaylistDto playlistDto, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        playlistService.addPlaylist(user.getId(), playlistDto.getName());
        return "redirect:/playlist";
    }

    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.USER})
    public String deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return "redirect:/playlist";
    }
}
