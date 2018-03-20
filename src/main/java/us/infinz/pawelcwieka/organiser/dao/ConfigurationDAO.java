package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Configuration;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public class ConfigurationDAO implements IConfigurationDAO {

    private IDatabase<Configuration> database = new Database<>(Configuration.class);

    @Override
    public Configuration findConfiguration(Long onfigurationId) {
        return database.getObjectFromDatabase(onfigurationId);
    }

    @Override
    public Configuration findUserConfiguration(User user) {

        String query = " select c from CONFIGURATION c where c.user = "+ user.getId()  + "";

        return database.createCustomQueryGet(query);
    }

    @Override
    public List<Configuration> findAllConfigurations() {

        String query = "from CONFIGURATION ";

        return database.getListOfObjectsFromDatabase(query);
    }

    @Override
    public Long saveConfiguration(Configuration configuration) {

        return database.saveObjectToDatabase(configuration);
    }

    @Override
    public void deleteConfiguration(Configuration configuration) {

        database.deleteObjectFromDatabase(configuration);

    }
}
