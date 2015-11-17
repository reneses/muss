package ie.cit.adf.muss.services;


import ie.cit.adf.muss.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

abstract class Service<E> {

    @Autowired
    Repository<E> repository;


    /**
     * Default constructor
     */
    public Service() {
    }

    /**
     * Constructor specifying a custom repository
     *
     * @param repository
     */
    public Service(Repository<E> repository) {
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
        return repository.get(id);
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
    public void save(E model) {
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
        return repository.remove(model);
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
    public List<E> findAll() {
        return repository.findAll();
    }

}
