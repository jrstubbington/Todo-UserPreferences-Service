package org.example.todo.preferences.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.generated.controller.GlobalPreferencesManagementApi;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceDto;
import org.example.todo.preferences.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Api(tags = "Global Preferences Management")
@Validated
@Slf4j
public class PreferencesController implements GlobalPreferencesManagementApi {

	private PreferenceService preferenceService;

	@Override
	public ResponseEntity<ResponseContainerPreferenceDto> getAllPreferences(@Min(0) @Valid Integer page, @Min(1) @Valid Integer pageSize) {
		return ResponseEntity.ok(preferenceService.getAllPreferencesResponse(PageRequest.of(page, pageSize)));
	}

	@Autowired
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
}
