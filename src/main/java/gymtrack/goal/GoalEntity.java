package gymtrack.goal;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import gymtrack.objetives.ObjetiveEntity;
import jakarta.annotation.Nonnull;
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
public class GoalEntity {

	@Id
	@GeneratedValue
	private UUID id;

	@Nonnull
	private String name;

	private String description;

	@Nonnull
	private Boolean done;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "objetive_id")
	private ObjetiveEntity objetive;
	
	@CurrentTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime upated;
}
