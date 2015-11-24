package ie.cit.adf.muss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

abstract class CrudService<E> {

    @Autowired
    CrudRepository<E, Integer> repository;

    /**
     * Default constructor
     */
    public CrudService() {
    }

    /**
     * Constructor specifying a custom repository
     *
     * @param repository
     */
    public CrudService(CrudRepository<E, Integer> repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a model given its id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    public E get(int id) {
        return repository.findOne(id);
    }


    /**
     * Save a model into the repository.
     *
     * - If the model does not exist, it's inserted
     * - If the model already exists, it's updated
     *
     * @param model
     * @throws IllegalArgumentException if the model is null
     */
    public E save(E model) {
        repository.save(model);
    }


    /**
     * Save a collection of models into the repository.
     *
     * - If the model does not exist, it's inserted
     * - If the model already exists, it's updated
     *
     * @param models
     * @throws IllegalArgumentException if the model is null
     */
    public void save(Collection<E> models) {
        models.forEach(repository::save);
    }


    /**
     * Remove a model from the repository
     *
     * @param model
     * @return true if removed, false otherwise
     * @throws IllegalArgumentException if the model is null
     */
    public boolean remove(E model) {
        repository.delete(model);
        return true;
    }

    
    /**
     * Remove a collection of models from the repository
     *
     * @param models
     * @throws IllegalArgumentException if the model is null
     */
    public void remove(Collection<E> models) {
        models.forEach(repository::save);
    }


    /**
     * Retrieve all the models stored in the repository
     *
     * @return Stored models
     */
    public Iterable<E> findAll() {
        return repository.findAll();
    }

}
