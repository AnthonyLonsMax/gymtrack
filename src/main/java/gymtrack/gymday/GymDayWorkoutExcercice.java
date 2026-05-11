package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class GymDayWorkoutExcercice {

	@Id
	@GeneratedValue
	private UUID id;

	@Nonnull
	private String name;

	@Nonnull
	private Integer sets;

	@Nonnull
	private Integer reps;

	@Nullable
	private String weigth;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "workout_id")
	private GymDayWorkout workout;
	
	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
