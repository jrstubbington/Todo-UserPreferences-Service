package org.example.todo.preferences.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.common.exceptions.ResourceNotFoundException;
import org.example.todo.common.util.ResponseUtils;
import org.example.todo.preferences.generated.dto.PreferenceDto;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceDto;
import org.example.todo.preferences.model.Preference;
import org.example.todo.preferences.model.PreferenceCategory;
import org.example.todo.preferences.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PreferenceService {

	private PreferenceRepository preferenceRepository;

	private UserPreferenceService userPreferenceService;

	private PreferenceCategoryService preferenceCategoryService;

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

	@Transactional
	public Preference getPreferenceByName(String name) {
		List<Preference> userPreferences = getAllPreferences();
		Optional<Preference> selectedUserPreference = userPreferences.stream().filter(preference -> preference.getName().equalsIgnoreCase(name)).findFirst();
		return selectedUserPreference.orElseThrow(() -> new ResourceNotFoundException(String.format("Could not find preference with name %s", name)));

	}

	@Transactional
	public Preference addNewPreference(PreferenceDto preferenceDto) {
		log.debug("{}", preferenceDto);
		PreferenceCategory preferenceCategory = preferenceCategoryService.getPreferenceCategoryByUuid(preferenceDto.getPreferenceCategoryUuid());
		Preference preference = Preference.builder()
				.name(preferenceDto.getName())
				.defaultValue(preferenceDto.getDefaultSelection())
				.preferenceCategory(preferenceCategory)
				.description(preferenceDto.getDescription())
				.build();
		return preferenceRepository.saveAndFlush(preference);
	}

	@Transactional
	public ResponseContainerPreferenceDto addNewPreferenceResponse(PreferenceDto preferenceDto) {
		return ResponseUtils.convertToDtoResponseContainer(addNewPreference(preferenceDto), PreferenceDto.class, ResponseContainerPreferenceDto.class);
	}

	@Autowired
	public void setPreferenceRepository(PreferenceRepository preferenceRepository) {
		this.preferenceRepository = preferenceRepository;
	}

	@Autowired
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}

	@Autowired
	public void setPreferenceCategoryService(PreferenceCategoryService preferenceCategoryService) {
		this.preferenceCategoryService = preferenceCategoryService;
	}
}
