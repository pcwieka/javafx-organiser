package us.infinz.pawelcwieka.organiser.dao;


import us.infinz.pawelcwieka.organiser.resource.User;

import java.util.List;

public class UserDAO implements IUserDAO {

    private Database<User> database = new Database<>(User.class);

    @Override
    public User findUser(Long userId) {

        return database.getObjectFromDatabase(userId);
    }

    @Override
    public User findUserByLogin(String login) {

        String query = " select u from USER u where u.userLogin = '"+ login  + "'";

        return database.createCustomQueryGet(query);
    }

    @Override
    public List<User> findAllUsers() {

        String query = "from USER ";

        return database.getListOfObjectsFromDatabase(query);
    }

    @Override
    public Long saveUser(User user) {

        return database.saveObjectToDatabase(user);
    }

    @Override
    public void deleteUser(User user) {

        database.deleteObjectFromDatabase(user);

    }
}
