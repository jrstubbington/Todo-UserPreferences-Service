package org.example.todo.preferences.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.common.util.ResponseUtils;
import org.example.todo.preferences.generated.dto.ResponseContainerUserPreferenceDto;
import org.example.todo.preferences.generated.dto.UserPreferenceDto;
import org.example.todo.preferences.model.UserPreference;
import org.example.todo.preferences.repository.UserPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserPreferenceService {

	private UserPreferenceRepository userPreferenceRepository;

	//TODO: Enable filtering and sorting
	public List<UserPreference> getAllUserPreferencesForUuid(UUID uuid) {
		return userPreferenceRepository.findAllByUserUuid(uuid);
	}

	//TODO: Enable filtering and sorting
	public ResponseContainerUserPreferenceDto getAllUserPreferencesForUuidResponse(UUID uuid) {
		return ResponseUtils.convertToDtoResponseContainer(getAllUserPreferencesForUuid(uuid), UserPreferenceDto.class, ResponseContainerUserPreferenceDto.class);
	}

	@Autowired
	public void setUserPreferenceRepository(UserPreferenceRepository userPreferenceRepository) {
		this.userPreferenceRepository = userPreferenceRepository;
	}
}
