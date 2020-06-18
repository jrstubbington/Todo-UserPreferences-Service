package org.example.todo.preferences.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.common.util.ResponseUtils;
import org.example.todo.preferences.generated.dto.PreferenceDto;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceDto;
import org.example.todo.preferences.model.Preference;
import org.example.todo.preferences.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PreferenceService {

	private PreferenceRepository preferenceRepository;

	private UserPreferenceService userPreferenceService;

	//TODO: Enable filtering and sorting
	public List<Preference> getAllPreferences() {
		return preferenceRepository.findAll();
	}

	//TODO: Enable filtering and sorting
	public ResponseContainerPreferenceDto getAllPreferencesResponse(PageRequest pageRequest) {
		return ResponseUtils.convertToDtoResponseContainer(preferenceRepository.findAll(pageRequest), PreferenceDto.class, ResponseContainerPreferenceDto.class);
	}

	public Preference getPreferenceByUuid() {
		return null;
	}

	@Transactional
	public void deletePreferenceByUuid(UUID uuid) {
		userPreferenceService.deleteAllUserPreferencesWithPreferenceUuid(uuid);
		preferenceRepository.deleteByUuid(uuid);
	}

	@Autowired
	public void setPreferenceRepository(PreferenceRepository preferenceRepository) {
		this.preferenceRepository = preferenceRepository;
	}

	@Autowired
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
}
