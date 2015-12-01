package ie.cit.adf.muss.repositories;

import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.ChObject;

public interface ChObjectRepository extends CrudRepository<ChObject, Integer> {

	ChObject findOneByOriginalId(int id);

}