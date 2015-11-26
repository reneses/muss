package ie.cit.adf.muss.repositories;

import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {

    public Tag findByName(String name);

}
