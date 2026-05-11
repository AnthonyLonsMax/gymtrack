package gymtrack.objetives;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import gymtrack.goal.GoalEntity;
import gymtrack.user.UserEntity;
import gymtrack.workout.WorkoutEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjetiveEntity {

	@Id
	@GeneratedValue
	private UUID id;

	@Nonnull
	private String name;

	private String description;

	@OneToMany(mappedBy = "objetive", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<GoalEntity> goals;
	
	@ManyToOne
	private UserEntity user;

	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
