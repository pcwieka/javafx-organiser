package us.infinz.pawelcwieka.organiser.dao;

import java.util.List;

public interface IDatabase<T> {

    List<T> getListOfObjectsFromDatabase(String query);
    T getObjectFromDatabase(Long id);
    Long saveObjectToDatabase(T object);
    void deleteObjectFromDatabase(T object);
    void createCustomQueryUpdate(String query);
    T createCustomQueryGet(String query);


}
