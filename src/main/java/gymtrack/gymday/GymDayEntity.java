package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import gymtrack.objetives.ObjetiveEntity;
import gymtrack.workout.WorkoutEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class GymDayEntity {

	@Id
	@GeneratedValue
	private UUID id;

	@OneToMany(mappedBy = "gymday",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GymDayWorkout> workouts;
	
	@OneToMany(mappedBy = "gymday",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GymDayExcerciceDone> excercicesDone;
	
	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
