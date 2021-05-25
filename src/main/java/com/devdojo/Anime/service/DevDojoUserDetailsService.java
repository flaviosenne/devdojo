package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.exception.BadRequestException;
import com.devdojo.Anime.repository.AnimeRepository;
import com.devdojo.Anime.repository.DevDojoUserRepository;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DevDojoUserDetailsService implements UserDetailsService {
    private final DevDojoUserRepository devDojoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(devDojoUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found"));
    }
}
