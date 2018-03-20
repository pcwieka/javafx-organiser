package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public class LocalisationDAO implements ILocalisationDAO {

    private IDatabase<Localization> database = new Database<>(Localization.class);

    @Override
    public Localization findActiveLocalisation(User user) {

        String query = " select l from LOCALIZATION l where l.user = "+ user.getId() + " AND l.active = 1";

        return database.createCustomQueryGet(query);

    }

    @Override
    public List<Localization> findAllLocalisations(User user) {

        String query = "select l from LOCALIZATION l WHERE l.user = " + user.getId();

        return database.getListOfObjectsFromDatabase(query);

    }

    @Override
    public Long saveLocalisation(Localization localization) {

        return database.saveObjectToDatabase(localization);

    }

    @Override
    public void updateLocalisationsActiveColumn(User user, Boolean active) {

        String query = "update LOCALIZATION set LOCALIZATION_ACTIVE = " + active.toString() + " WHERE USER_ID = " + user.getId();

        database.createCustomQueryUpdate(query);

    }

    @Override
    public void updateLocalisationActiveColumn(User user,Localization localization, Boolean active) {

        String query = "update LOCALIZATION set LOCALIZATION_ACTIVE = " + active.toString()
                + " WHERE LOCALIZATION_USER_TYPED_NAME = '" + localization.getUserTypedName()+ "' AND USER_ID = " + user.getId();


        database.createCustomQueryUpdate(query);

    }
}
