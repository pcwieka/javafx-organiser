package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localization;
import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public interface ILocalisationDAO {

    public Localization findActiveLocalisation(User user);
    public List<Localization> findAllLocalisations(User user);
    public Long saveLocalisation(Localization event);
    public void updateLocalisationsActiveColumn(User user, Boolean active);
    public void updateLocalisationActiveColumn(User user, Localization localization, Boolean active);

}
