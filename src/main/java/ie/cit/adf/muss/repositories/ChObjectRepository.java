package ie.cit.adf.muss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.ChObject;

public interface ChObjectRepository extends CrudRepository<ChObject, Integer> {

	ChObject findOneByOriginalId(int id);
	
	@Query("SELECT o FROM ChObject o JOIN o.tags t WHERE t.name = ?1")
	List<ChObject> findByTagName(String tagName);
	
	@Query("SELECT o FROM ChObject o WHERE LOWER(o.title) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',?1,'%'))")
	List<ChObject> findByTitleOrDescription(String search);
	
	@Query("SELECT o FROM ChObject o JOIN o.tags t WHERE (LOWER(o.title) LIKE LOWER(CONCAT('%',?1,'%')) OR LOWER(o.description) LIKE LOWER(CONCAT('%',?1,'%'))) AND t.name = ?2")
	List<ChObject> findByTitleOrDescriptionAndTagName(String search, String tagName);

}