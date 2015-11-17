package ie.cit.adf.muss.repositories;


import java.util.List;

public interface Repository<E> {


    /**
     * Retrieve a model given its id
     *
     * @throws IllegalArgumentException if the id is not a positive integer
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     */
    E get(int id);


    /**
     * Save a model into the repository.
     *
     * - If the model does not exist, it's inserted
     * - If the model already exists, it's updated
     *
     * @param model
     * @throws IllegalArgumentException if the model is null
     */
    void save(E model);


    /**
     * Remove a model from the repository
     *
     * @param model
     * @return true if removed, false otherwise
     * @throws IllegalArgumentException if the model is null
     */
    boolean remove(E model);


    /**
     * Retrieve all the models stored in the repository
     *
     * @return Stored models
     */
    List<E> findAll();


}
