package personal.utils;

public class Validate {
    public void checkNumber (String telephone) throws Exception {
        if(!telephone.substring(0,1).equals("+")) {
            throw new PhoneException("ERROR! Number start with '+'");
        } else if (telephone.length() != 12) {
            throw new PhoneException("ERROR! Number length = 11!");
        }
    }
}
