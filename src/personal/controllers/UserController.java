package personal.controllers;

import personal.model.Fields;
import personal.model.Repository;
import personal.model.User;
import personal.utils.Validate;

import java.util.List;

public class UserController {
    private Repository repository;
    private Validate validate;

    public UserController(Repository repository, Validate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public void saveUser(User user) throws Exception {
        validate.checkNumber(user.getPhone());
        repository.CreateUser(user);
    }

    public void updateUser(User user, Fields field, String param) throws Exception {
        if (field == Fields.TELEPHONE) {
            validate.checkNumber(param);
        }
        System.out.println("Complete");
        repository.UpdateUser(user, field, param);
    }

    public User readUser(String userId) throws Exception {
        List<User> users = repository.getAllUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        throw new Exception("User not found");
    }

    public void deleteUser(User user) throws Exception {
        repository.DeleteUser(user);
    }

    public List<User> getUsers() {
        return repository.getAllUsers();
    }
}
