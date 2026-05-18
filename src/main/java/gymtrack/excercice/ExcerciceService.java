package gymtrack.excercice;

import java.util.List;
import java.util.UUID;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gymtrack.gen.tables.ExcercieEntity;

@Repository
public class ExcerciceService {

	@Autowired
	private DSLContext context;

	@Autowired
	private ExcerciceMapper mapper;

	private final ExcercieEntity EXCERCICE = ExcercieEntity.EXCERCIE_ENTITY;

	public List<ExcerciceGetDTO> top5() {
		var result = context
				.select(EXCERCICE.ID, EXCERCICE.NAME, EXCERCICE.REPS,
						EXCERCICE.SETS, EXCERCICE.WEIGTH, EXCERCICE.CREATED,
						EXCERCICE.UPATED)
				.where(EXCERCICE.ID.between(UUID.randomUUID(),
						UUID.randomUUID()))
				.limit(5).fetch();
		return result.map(record -> {
			return new ExcerciceGetDTO(record.component1(), record.component2(),
					record.component3(), record.component4(),
					record.component5(), record.component6(),
					record.component7());
		});
	}
}
