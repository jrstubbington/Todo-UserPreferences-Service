package org.example.todo.preferences.repository;

import org.example.todo.preferences.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//Suppressing warning for Sonarqube function naming convention as JPA defines rules that don't comply
@SuppressWarnings("squid:S00100")
@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
	List<UserPreference> findAllByUserUuid(UUID uuid);
}
