package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.AuditColumns;

import java.util.List;

public interface IDatabase<T> {

    List<T> getListOfObjectsFromDatabase(String query);
    T getObjectFromDatabase(Long id);
    <T extends AuditColumns> Long saveObjectToDatabase(T object);
    void deleteObjectFromDatabase(T object);
    void createCustomQueryUpdate(String query);
    T createCustomQueryGet(String query);


}
