package ie.cit.adf.muss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.ChObject;

public interface ChObjectRepository extends CrudRepository<ChObject, Integer> {

	ChObject findOneByOriginalId(int id);
	
	@Query("SELECT o FROM ChObject o JOIN o.tags t WHERE t.name = ?1")
	List<ChObject> findByTagName(String name);

}