package org.example.todo.preferences.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_preferences")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID userUuid;

	@ManyToOne
	@JoinColumn(name = "preference_uuid", referencedColumnName = "uuid")
	private Preference preference;

	private boolean userValue;
}
