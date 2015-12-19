package ie.cit.adf.muss.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ie.cit.adf.muss.utility.Utils;

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
    public E find(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("The ID cannot must be a positive integer");
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
        if (model == null)
            throw new IllegalArgumentException("The model to save cannot be null");
        return repository.save(model);
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
        if (model == null)
            throw new IllegalArgumentException("The model to remove cannot be null");
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
    public List<E> findAll() {
        return Utils.toList(repository.findAll());
    }

}
