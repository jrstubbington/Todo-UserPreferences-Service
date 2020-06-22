package org.example.todo.preferences.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.common.util.ResponseUtils;
import org.example.todo.preferences.generated.dto.ResponseContainerUserPreferenceDto;
import org.example.todo.preferences.generated.dto.UserPreferenceDto;
import org.example.todo.preferences.model.Preference;
import org.example.todo.preferences.model.UserPreference;
import org.example.todo.preferences.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserPreferenceService {

	private UserPreferenceRepository userPreferenceRepository;

	private PreferenceService preferenceService;

	//TODO: Enable filtering and sorting
	public List<UserPreference> getAllUserPreferencesForUuid(UUID uuid) {
		List<UserPreference> userPreferences = userPreferenceRepository.findAllByUserUuid(uuid);

/*		List<Preference> globalPreferences = preferenceService.getAllPreferences();

		globalPreferences.stream().map(preference -> {
			UserPreferenceDto userPreference = new UserPreferenceDto();
			userPreference.setValue(preference.isDefaultValue());
			userPreference.setPreferenceUuid(preference.getUuid());
		})*/

		return userPreferences;
	}

	//TODO: Enable filtering and sorting

	public ResponseContainerUserPreferenceDto getAllUserPreferencesForUuidResponse(UUID uuid) {
		return ResponseUtils.convertToDtoResponseContainer(getAllUserPreferencesForUuid(uuid), UserPreferenceDto.class, ResponseContainerUserPreferenceDto.class);
	}


	@Transactional
	public List<UserPreference> updateUserPreferences(UUID userUuid, List<UserPreferenceDto> userPreferenceDtoList) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Map<UUID, Boolean> updatedUserPreferences = userPreferenceDtoList.stream().collect(Collectors.toMap(UserPreferenceDto::getPreferenceUuid, UserPreferenceDto::getUserSelection));
		Map<UUID, Preference> preferenceMap = preferenceService.getAllPreferences().stream().collect(Collectors.toMap(Preference::getUuid, preference -> preference));
		Map<UUID, UserPreference> currentUserPreferences = getAllUserPreferencesForUuid(userUuid).stream()
				.collect(Collectors.toMap(userPreference -> userPreference.getPreference().getUuid(), userPreference -> userPreference));

		for (Map.Entry<UUID, Boolean> updatedUserPreference : updatedUserPreferences.entrySet()) {
			//Check to see if the preference is default
			boolean currentDefault = preferenceMap.get(updatedUserPreference.getKey()).isDefaultValue();
			boolean isRequestingDefault = updatedUserPreference.getValue() == currentDefault;
			boolean currentlyExists = Objects.nonNull(currentUserPreferences.get(updatedUserPreference.getKey()));

			log.trace("\nCURRENT DEFAULT:{} \nREQUESTING DEFAULT:{} \nCURRENTLY EXISTS:{}", currentDefault, isRequestingDefault, currentlyExists);

			if (isRequestingDefault && currentlyExists) {
				log.trace("EXISTS AND REQUESTING DEFAULT. DELETING");
				userPreferenceRepository.delete(currentUserPreferences.get(updatedUserPreference.getKey()));
			}
			else if (!isRequestingDefault && currentlyExists) {
				log.trace("EXISTS AND REQUESTING CHANGE. UPDATING");
				UserPreference userPreference = currentUserPreferences.get(updatedUserPreference.getKey());
				userPreference.setUserValue(updatedUserPreference.getValue());
				userPreferenceRepository.save(userPreference);
			}
			else if (!isRequestingDefault) {
				log.trace("DOESN'T EXIST AND ISN'T DEFAULT. CREATING");
				UserPreference userPreference = UserPreference.builder()
						.userUuid(userUuid)
						.userValue(updatedUserPreference.getValue())
						.preference(preferenceMap.get(updatedUserPreference.getKey()))
						.build();
				userPreferenceRepository.save(userPreference);
			}
			else {
				log.trace("DOESN'T EXIST AND IS DEFAULT. IGNORING");
				// Do nothing
				// User is requesting the default on a property that is already default
			}
		}
		userPreferenceRepository.flush();
		stopWatch.stop();
		log.debug("Saving user preferences took {} seconds", stopWatch.getTotalTimeSeconds());

		return getAllUserPreferencesForUuid(userUuid);
	}

	@Transactional
	public ResponseContainerUserPreferenceDto updateUserPreferencesResponse(UUID userUuid, List<UserPreferenceDto> userPreferenceDtoList) {
		return ResponseUtils.convertToDtoResponseContainer(updateUserPreferences(userUuid, userPreferenceDtoList), UserPreferenceDto.class, ResponseContainerUserPreferenceDto.class);
	}

	@Transactional
	public void deleteAllUserPreferencesWithPreferenceUuid(UUID uuid) {
		userPreferenceRepository.deleteAllByPreference_Uuid(uuid);
		userPreferenceRepository.flush();
	}

	@Transactional
	public UserPreference getUserPreferenceWithPreferenceName(UUID userUuid, String preferenceName) {
		List<UserPreference> userPreferences = getAllUserPreferencesForUuid(userUuid);
		Optional<UserPreference> selectedUserPreference = userPreferences.stream().filter(userPreference -> userPreference.getPreference().getName().equalsIgnoreCase(preferenceName)).findFirst();
		if (!selectedUserPreference.isPresent()) {
			Preference preference = preferenceService.getPreferenceByName(preferenceName);
			return UserPreference.builder()
					.preference(preference)
					.userValue(preference.isDefaultValue())
					.userUuid(userUuid)
					.build();
		}
		else {
			return selectedUserPreference.get();
		}
	}

	@Transactional
	public ResponseContainerUserPreferenceDto getUserPreferenceWithPreferenceNameResponse(UUID userUuid, String preferenceName) {
		return ResponseUtils.convertToDtoResponseContainer(getUserPreferenceWithPreferenceName(userUuid, preferenceName), UserPreferenceDto.class, ResponseContainerUserPreferenceDto.class);

	}


	@Autowired
	public void setUserPreferenceRepository(UserPreferenceRepository userPreferenceRepository) {
		this.userPreferenceRepository = userPreferenceRepository;
	}

	@Autowired
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
}
