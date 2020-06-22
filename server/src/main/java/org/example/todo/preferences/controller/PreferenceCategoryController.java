package org.example.todo.preferences.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.preferences.generated.controller.PreferenceCategoryManagementApi;
import org.example.todo.preferences.generated.dto.PreferenceCategoryDto;
import org.example.todo.preferences.generated.dto.ResponseContainerCategoryDto;
import org.example.todo.preferences.generated.dto.ResponseContainerPreferenceCategoryDto;
import org.example.todo.preferences.service.PreferenceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Api(tags = "Preference Category Management")
@Validated
@Slf4j
public class PreferenceCategoryController implements PreferenceCategoryManagementApi {

	private PreferenceCategoryService preferenceCategoryService;

	/**
	 * GET /v1/preferences/categories : Create a new Preference Category
	 *
	 * @param page     (optional, default to 0)
	 * @param pageSize (optional, default to 10)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerCategoryDto> getAllPreferenceCategories(@Min(0) @Valid Integer page, @Min(1) @Valid Integer pageSize) {
		return ResponseEntity.ok(preferenceCategoryService.getAllPreferencesResponse(PageRequest.of(page, pageSize)));
	}

	/**
	 * POST /v1/preferences/categories : Create a preference category
	 *
	 * @param preferenceCategoryDto (required)
	 * @return OK (status code 200)
	 * or Client Error (status code 400)
	 * or Not Found (status code 404)
	 * or Internal error has occurred (status code 500)
	 */
	@Override
	public ResponseEntity<ResponseContainerPreferenceCategoryDto> createPreferenceCategory(@Valid PreferenceCategoryDto preferenceCategoryDto) throws Exception {
		return ResponseEntity.ok(preferenceCategoryService.createPreferenceCategoryResponse(preferenceCategoryDto));
	}

	@Autowired
	public void setPreferenceCategoryService(PreferenceCategoryService preferenceCategoryService) {
		this.preferenceCategoryService = preferenceCategoryService;
	}
}
