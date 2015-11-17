package ie.cit.adf.muss.repositories;

import ie.cit.adf.muss.domain.ChObject;

public interface ChObjectRepository extends Repository<ChObject> {


    /**
     * Retrieve a model given its original id
     *
     * @param originalId Original ID of the model
     * @return Model with the given original id, null if it does not exist
     */
    ChObject getByOriginalId(int originalId);


}