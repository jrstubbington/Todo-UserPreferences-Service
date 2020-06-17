package org.example.todo.preferences.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.generated.controller.UserPreferencesManagementApi;
import org.example.todo.preferences.generated.dto.ResponseContainerUserPreferenceDto;
import org.example.todo.preferences.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Controller
@Api(tags = "User Preferences Management")
@Validated
@Slf4j
public class UserPreferencesController implements UserPreferencesManagementApi {

	private UserPreferenceService userPreferenceService;


	@Override
	public ResponseEntity<ResponseContainerUserPreferenceDto> getPreferencesForUserUuid(UUID uuid) {
		return ResponseEntity.ok(userPreferenceService.getAllUserPreferencesForUuidResponse(uuid));
	}

	@Autowired
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
}
