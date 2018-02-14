package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.Localisation;

import java.util.List;

public interface LocalisationDAO {

    public Localisation findActiveLocalisation();
    public List<Localisation> findAllLocalisations();
    public Long saveLocalisation(Localisation event);
    public void updateLocalisationsActiveColumn(Boolean active);
    public void updateLocalisationActiveColumn(Localisation localisation, Boolean active);

}
