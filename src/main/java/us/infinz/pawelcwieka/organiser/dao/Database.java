package us.infinz.pawelcwieka.organiser.dao;

import java.util.List;

public interface Database<T> {

    List<T> getListOfObjectsFromDatabase(String query);
    T getObjectFromDatabase(String query);
    Long saveObjectToDatabase(T object);
    void deleteObjectFromDatabase(T object);
    void createCustomQuery(String query);


}
