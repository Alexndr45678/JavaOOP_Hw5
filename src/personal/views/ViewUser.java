package personal.views;

import jdk.jshell.EvalException;
import personal.controllers.UserController;
import personal.model.Fields;
import personal.model.User;
import personal.utils.PhoneException;
import personal.utils.Validate;

import java.util.Scanner;

public class ViewUser {
    private final UserController userController;
    private final Validate validate;

    public ViewUser(UserController userController, Validate validate) {
        this.userController = userController;
        this.validate = validate;
    }

    public void run() {
        Commands com;
        showHelp();
        while (true) {
            try {
                String command = prompt("Enter command");
                com = Commands.valueOf(command.toUpperCase());
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE -> createUser();
                    case READ -> readUser();
                    case UPDATE -> updateUser();
                    case LIST -> listUser();
                    case HELP -> showHelp();
                    case DELETE -> delUser();
                }
            } catch (Exception ex) {
                System.out.printf("ERROR:\n%s\n", ex.toString());
            }
        }
    }

    private void showHelp() {
        System.out.println("Commands: ");
        for (Commands c : Commands.values()) {
            System.out.println(c);
        }
    }

    private void createUser() throws Exception {
        String firstName = prompt("Name: ");
        String lastName = prompt("LastName: ");
        String phone = null;
        phone = catchNumPhone(phone);
        if (phone == null) {
            return;
        }

        userController.saveUser(new User(firstName, lastName, phone));
    }

    private void readUser() throws Exception {
        String id = prompt("ID: ");
        User user_ = userController.readUser(id);
        userController.deleteUser(user_);
    }

    private void delUser() throws Exception{
        String userId = prompt("Enter ID user which you want delete: ");
        User user = userController.readUser(userId);
        userController.deleteUser(user);
    }

    private void updateUser() throws Exception {
        String userId = prompt("ID user: ");
        String fieldName = prompt("Select field(NAME, SURNAME, TELEPHONE: ");
        String param = null;
        if (Fields.valueOf(fieldName.toUpperCase()) == Fields.TELEPHONE) {
            param = catchNumPhone(param);
            if (param == null) {
                return;
            } else {
                param = prompt("Enter which field you want to change.\n");
            }
        }
        User _user = userController.readUser(userId);
        userController.updateUser(_user, Fields.valueOf(fieldName.toUpperCase()), param);
    }

    public String catchNumPhone(String telephone) throws Exception {
        while (true) {
            try {
                telephone = prompt("Enter number phone (Enter '0' to refuse ): ");
                if (telephone.equals("0")) {
                    System.out.println("You refused to make user change");
                    return null;
                }
                validate.checkNumber(telephone);
                return telephone;
            } catch (PhoneException e) {
                System.out.printf("ERROR:\n%s\n", e.toString());
            }
        }
    }

    private void listUser() {
        for (User user : userController.getUsers()) {
            System.out.println(user);
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        return in.nextLine();
    }
}

