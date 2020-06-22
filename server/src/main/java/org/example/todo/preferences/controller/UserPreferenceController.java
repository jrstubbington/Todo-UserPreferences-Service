package org.example.todo.preferences.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.generated.controller.UserPreferencesManagementApi;
import org.example.todo.preferences.generated.dto.ResponseContainerUserPreferenceDto;
import org.example.todo.preferences.generated.dto.UserPreferenceDto;
import org.example.todo.preferences.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@Api(tags = "User Preferences Management")
@Validated
@Slf4j
public class UserPreferenceController implements UserPreferencesManagementApi {

	private UserPreferenceService userPreferenceService;


	/**
	 * GET /v1/preferences/user/{uuid} : Get a specific user's preferences
	 *
	 * @param uuid User uuid to get preferences for (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerUserPreferenceDto> getPreferencesForUserUuid(UUID uuid) {
		return ResponseEntity.ok(userPreferenceService.getAllUserPreferencesForUuidResponse(uuid));
	}


	/**
	 * PUT /v1/preferences/user/{uuid} : Update all of a specific user's preferences
	 *
	 * @param uuid User uuid to set preferences list for (required)
	 * @param userPreferenceDto (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerUserPreferenceDto> updatePreferencesForUserUuid(UUID uuid, @Valid List<UserPreferenceDto> userPreferenceDto) {
		userPreferenceService.updateUserPreferencesResponse(uuid, userPreferenceDto);
		return ResponseEntity.ok(new ResponseContainerUserPreferenceDto()); //TODO
	}

	/**
	 * GET /v1/preferences/name/{preference_name}/user/{user_uuid} : Get a specific user's preference by the preference name
	 *
	 * @param userUuid User uuid to get preferences for (required)
	 * @param preferenceName Preference name to get preferences for (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerUserPreferenceDto> getSpecificPreferenceForUserUuidByName(UUID userUuid, String preferenceName) {
		return ResponseEntity.ok(userPreferenceService.getUserPreferenceWithPreferenceNameResponse(userUuid, preferenceName));
	}

	@Autowired
	public void setUserPreferenceService(UserPreferenceService userPreferenceService) {
		this.userPreferenceService = userPreferenceService;
	}
}
