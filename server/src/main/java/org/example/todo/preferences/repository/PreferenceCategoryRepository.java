package org.example.todo.preferences.repository;

import org.example.todo.preferences.model.PreferenceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PreferenceCategoryRepository extends JpaRepository<PreferenceCategory, Long> {

	Optional<PreferenceCategory> findByUuid(UUID uuid);
}
