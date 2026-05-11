package gymtrack.gymday;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class GymDayWorkout {

	@Id
	@GeneratedValue
	private UUID id;

	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gymday_id")
	private GymDayEntity gymday;

	@OneToMany(mappedBy = "workout",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GymDayWorkoutExcercice> excercices;
	
	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
