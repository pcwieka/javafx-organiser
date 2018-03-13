package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Configuration;

import java.util.List;

public interface IConfigurationDAO {

    public Configuration findConfiguration(Long onfigurationId);
    public List<Configuration> findAllConfigurations();
    public Long saveConfiguration(Configuration configuration);
    public void deleteConfiguration(Configuration configuration);

}
