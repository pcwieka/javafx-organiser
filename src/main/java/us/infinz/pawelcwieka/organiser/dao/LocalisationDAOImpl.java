package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localisation;

import java.util.List;

public class LocalisationDAOImpl implements LocalisationDAO{

    private Database<Localisation> database = new DatabaseImpl<>();

    @Override
    public Localisation findActiveLocalisation() {

        String query = "from LOCALISATION where LOCALISATION_ACTIVE = TRUE";

        return database.getObjectFromDatabase(query);

    }

    @Override
    public List<Localisation> findAllLocalisations() {

        String query = "from LOCALISATION ";

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public Long saveLocalisation(Localisation localisation) {

        return database.saveObjectToDatabase(localisation);

    }

    @Override
    public void updateLocalisationsActiveColumn(Boolean active) {

        String query = "update LOCALISATION set LOCALISATION_ACTIVE = " + active.toString();

        System.out.println(query);

        database.createCustomQuery(query);

    }

    @Override
    public void updateLocalisationActiveColumn(Localisation localisation, Boolean active) {

        String query = "update LOCALISATION set LOCALISATION_ACTIVE = " + active.toString()
                + " WHERE LOCALISATION_USER_TYPED_NAME = '" + localisation.getUserTypedName()+ "'";

        System.out.println(query);

        database.createCustomQuery(query);

    }
}
