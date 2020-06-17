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

import java.util.List;

@Service
@Slf4j
public class PreferenceService {

	private PreferenceRepository preferenceRepository;

	//TODO: Enable filtering and sorting
	public List<Preference> getAllPreferences() {
		return preferenceRepository.findAll();
	}

	//TODO: Enable filtering and sorting
	public ResponseContainerPreferenceDto getAllPreferencesResponse(PageRequest pageRequest) {
		return ResponseUtils.convertToDtoResponseContainer(preferenceRepository.findAll(pageRequest), PreferenceDto.class, ResponseContainerPreferenceDto.class);
	}

	@Autowired
	public void setPreferenceRepository(PreferenceRepository preferenceRepository) {
		this.preferenceRepository = preferenceRepository;
	}
}
