package com.devdojo.Anime.repository;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.domain.DevDojoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {
    DevDojoUser findByUsername(String username);
}
