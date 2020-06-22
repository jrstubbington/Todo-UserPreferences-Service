package org.example.todo.preferences;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.model.Preference;
import org.example.todo.preferences.model.PreferenceCategory;
import org.example.todo.preferences.model.UserPreference;
import org.example.todo.preferences.repository.PreferenceCategoryRepository;
import org.example.todo.preferences.repository.PreferenceRepository;
import org.example.todo.preferences.repository.UserPreferenceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

@Component
@Slf4j
@Profile("local")
public class Setup {

	@Bean
	@Transactional
	public CommandLineRunner demo(PreferenceRepository preferenceRepository, UserPreferenceRepository userPreferenceRepository, PreferenceCategoryRepository preferenceCategoryRepository) {
		return args -> {
			try {

				PreferenceCategory communicationCategory = PreferenceCategory.builder()
						.name("Receive Notifications/Reminders Via")
//						.displayField("Receive Notifications/Reminders Via")
						.build();

				Preference preference = Preference.builder()
						.defaultValue(true)
						.name("email")
						.description("Receive notifications and reminders via email")
						.preferenceCategory(communicationCategory)
						.build();

				Preference preference2 = Preference.builder()
						.defaultValue(true)
						.name("phone")
						.description("Receive notifications and reminders via SMS message")
						.preferenceCategory(communicationCategory)
						.build();

				communicationCategory.setPreferences(Arrays.asList(preference, preference2));

				preferenceCategoryRepository.save(communicationCategory);

				Preference savedPreference = preferenceRepository.saveAndFlush(preference);
				Preference savedPreference2 = preferenceRepository.saveAndFlush(preference2);
				log.info("{}", savedPreference);

				UserPreference userPreference = UserPreference.builder()
						.preference(savedPreference)
						.userUuid(UUID.randomUUID())
						.userValue(false)
						.build();

				UserPreference savedUsedPreference = userPreferenceRepository.save(userPreference);

				log.info("{}", savedUsedPreference);
			}
			catch (DataIntegrityViolationException e) {
				log.trace("Failed to add data", e);
			}
			catch (Exception e) {
				log.error("failed to add data", e);
			}
			log.info("{}", userPreferenceRepository.findAll());
		};
	}
}
