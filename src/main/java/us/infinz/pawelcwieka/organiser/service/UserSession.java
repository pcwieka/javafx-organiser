package us.infinz.pawelcwieka.organiser.service;

import us.infinz.pawelcwieka.organiser.dao.UserDAO;
import us.infinz.pawelcwieka.organiser.resource.User;

public class UserSession {

    public static void setUserSessionActive(User user, Boolean active){

        UserDAO userDAO = new UserDAO();

        user = userDAO.findUser(user.getId());

        user.setSessionActive(active);

        userDAO.saveUser(user);

    }

}
