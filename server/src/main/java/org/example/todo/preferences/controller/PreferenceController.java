package org.example.todo.preferences.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.generated.controller.GlobalPreferencesManagementApi;
import org.example.todo.preferences.generated.dto.PreferenceDto;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceDto;
import org.example.todo.preferences.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@Api(tags = "Global Preferences Management")
@Validated
@Slf4j
public class PreferenceController implements GlobalPreferencesManagementApi {

	private PreferenceService preferenceService;

	@Override
	public ResponseEntity<ResponseContainerPreferenceDto> getAllPreferences(@Min(0) @Valid Integer page, @Min(1) @Valid Integer pageSize) {
		return ResponseEntity.ok(preferenceService.getAllPreferencesResponse(PageRequest.of(page, pageSize)));
	}

	/**
	 * DELETE /v1/preferences/{uuid} : Get a specific preference
	 *
	 * @param uuid Preference uuid to delete preference for (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<Void> deletePreferenceByUuid(UUID uuid) {
		preferenceService.deletePreferenceByUuid(uuid);
		return ResponseEntity.ok().build();
	}

	/**
	 * POST /v1/preferences/ : Create a new preference
	 *
	 * @param preferenceDto (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Internal Server Error (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerPreferenceDto> addNewPreference(@Valid PreferenceDto preferenceDto) throws Exception {
		return ResponseEntity.ok(preferenceService.addNewPreferenceResponse(preferenceDto));
	}

	/**
	 * GET /v1/preferences/name/{name} : Get a specific preference by name
	 *
	 * @param name Preference name to get preference of (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerPreferenceDto> getPreferenceByName(String name) {
		return null;
	}

	@Autowired
	public void setPreferenceService(PreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}
}
