package gymtrack.excercice;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import gymtrack.workout.WorkoutEntity;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExcercieEntity {

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
	private WorkoutEntity workout;

	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
