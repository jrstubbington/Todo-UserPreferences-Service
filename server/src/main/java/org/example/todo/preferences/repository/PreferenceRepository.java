package org.example.todo.preferences.repository;

import org.example.todo.preferences.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Suppressing warning for Sonarqube function naming convention as JPA defines rules that don't comply
@SuppressWarnings("squid:S00100")
@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
