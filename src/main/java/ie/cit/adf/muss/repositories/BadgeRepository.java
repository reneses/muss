package ie.cit.adf.muss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.Badge;

public interface BadgeRepository extends CrudRepository<Badge, Integer> {

	@Query("select b from Badges b where b.type=?1 and b.quantity <= ?2")
	List<Badge> findAssignableBadges(String type, Integer quantity);

	List<Badge> findByName(String name);
}
