package org.example.todo.preferences.service;

import lombok.extern.slf4j.Slf4j;
import org.example.todo.common.exceptions.ResourceNotFoundException;
import org.example.todo.common.util.ResponseUtils;
import org.example.todo.preferences.generated.dto.PreferenceCategoryDto;
import org.example.todo.preferences.generated.dto.PreferenceDto;
import org.example.todo.preferences.generated.dto.ResponseContainerCategoryDto;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceCategoryDto;
import org.example.todo.preferences.model.PreferenceCategory;
import org.example.todo.preferences.repository.PreferenceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PreferenceCategoryService {

	private PreferenceCategoryRepository preferenceCategoryRepository;

	//TODO: Enable filtering and sorting
	public List<PreferenceCategory> getAllPreferences() {
		return preferenceCategoryRepository.findAll();
	}

	//TODO: Enable filtering and sorting
	@Transactional
	public ResponseContainerCategoryDto getAllPreferencesResponse(PageRequest pageRequest) {
		return ResponseUtils.convertToDtoResponseContainer(preferenceCategoryRepository.findAll(pageRequest), PreferenceCategoryDto.class, 	ResponseContainerCategoryDto.class);
	}

	public PreferenceCategory getPreferenceCategoryByUuid(UUID uuid) {
		return preferenceCategoryRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException(String.format("Preference Category not found with id: %s", uuid)));
	}

	@Transactional
	public PreferenceCategory createPreferenceCategory(PreferenceCategoryDto preferenceCategoryDto) {
		PreferenceCategory preferenceCategory = PreferenceCategory.builder()
				.name(preferenceCategoryDto.getName())
				.build();

		return preferenceCategoryRepository.saveAndFlush(preferenceCategory);
	}

	@Transactional
	public ResponseContainerPreferenceCategoryDto createPreferenceCategoryResponse(PreferenceCategoryDto preferenceCategoryDto) {
		return ResponseUtils.convertToDtoResponseContainer(createPreferenceCategory(preferenceCategoryDto), PreferenceDto.class, ResponseContainerPreferenceCategoryDto.class);
	}

	@Autowired
	public void setPreferenceCategoryRepository(PreferenceCategoryRepository preferenceCategoryRepository) {
		this.preferenceCategoryRepository = preferenceCategoryRepository;
	}
}
