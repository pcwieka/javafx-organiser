package us.infinz.pawelcwieka.organiser.dao;

import us.infinz.pawelcwieka.organiser.resource.User;
import java.util.List;

public interface IUserDAO {

    public User findUser(Long userId);
    public User findUserByLogin(String login);
    public List<User> findAllUsers();
    public Long saveUser(User user);
    public void deleteUser(User user);

}
