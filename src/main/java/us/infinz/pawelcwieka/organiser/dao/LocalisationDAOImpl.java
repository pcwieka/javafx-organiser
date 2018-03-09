package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localization;

import java.util.List;

public class LocalisationDAOImpl implements LocalisationDAO{

    private Database<Localization> database = new DatabaseImpl<>();

    @Override
    public Localization findActiveLocalisation() {

        String query = "from LOCALIZATION where LOCALIZATION = TRUE";

        return database.getObjectFromDatabase(query);

    }

    @Override
    public List<Localization> findAllLocalisations() {

        String query = "from LOCALIZATION ";

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public Long saveLocalisation(Localization localization) {

        return database.saveObjectToDatabase(localization);

    }

    @Override
    public void updateLocalisationsActiveColumn(Boolean active) {

        String query = "update LOCALIZATION set LOCALIZATION_ACTIVE = " + active.toString();

        System.out.println(query);

        database.createCustomQuery(query);

    }

    @Override
    public void updateLocalisationActiveColumn(Localization localization, Boolean active) {

        String query = "update LOCALIZATION set LOCALIZATION_ACTIVE = " + active.toString()
                + " WHERE LOCALIZATION_USER_TYPED_NAME = '" + localization.getUserTypedName()+ "'";

        System.out.println(query);

        database.createCustomQuery(query);

    }
}
