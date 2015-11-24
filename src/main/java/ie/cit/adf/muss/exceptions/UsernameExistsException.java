package ie.cit.adf.muss.exceptions;

public class UsernameExistsException extends IllegalArgumentException  {

    public UsernameExistsException(String s) {
        super(s);
    }
}
