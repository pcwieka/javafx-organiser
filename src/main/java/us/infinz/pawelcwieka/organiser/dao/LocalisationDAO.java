package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localization;

import java.util.List;

public interface LocalisationDAO {

    public Localization findActiveLocalisation();
    public List<Localization> findAllLocalisations();
    public Long saveLocalisation(Localization event);
    public void updateLocalisationsActiveColumn(Boolean active);
    public void updateLocalisationActiveColumn(Localization localization, Boolean active);

}
