package org.example.todo.preferences.config;

import org.example.todo.preferences.ApiClient;
import org.example.todo.preferences.generated.controller.GlobalPreferencesManagementApi;
import org.example.todo.preferences.generated.controller.PreferenceCategoryManagementApi;
import org.example.todo.preferences.generated.controller.UserPreferencesManagementApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PreferencesApiClientConfig {

	@Bean
	public UserPreferencesManagementApi userPreferencesManagementApi(RestTemplate restTemplate) {
		UserPreferencesManagementApi userPreferencesManagementApi = new UserPreferencesManagementApi();
		userPreferencesManagementApi.setApiClient(new ApiClient(restTemplate));
		return userPreferencesManagementApi;
	}

	@Bean
	public PreferenceCategoryManagementApi preferenceCategoryManagementApi(RestTemplate restTemplate) {
		PreferenceCategoryManagementApi preferenceCategoryManagementApi = new PreferenceCategoryManagementApi();
		preferenceCategoryManagementApi.setApiClient(new ApiClient(restTemplate));
		return preferenceCategoryManagementApi;
	}

	@Bean
	public GlobalPreferencesManagementApi globalPreferencesManagementApi(RestTemplate restTemplate) {
		GlobalPreferencesManagementApi globalPreferencesManagementApi = new GlobalPreferencesManagementApi();
		globalPreferencesManagementApi.setApiClient(new ApiClient(restTemplate));
		return globalPreferencesManagementApi;
	}
}
