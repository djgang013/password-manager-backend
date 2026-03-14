package com.example.backend.vault.repository;

import com.example.backend.vault.entity.PasswordEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PasswordEntryRepository extends JpaRepository<PasswordEntry,Long >{
    List<PasswordEntry> findAllByUserId(UUID userId);
}
