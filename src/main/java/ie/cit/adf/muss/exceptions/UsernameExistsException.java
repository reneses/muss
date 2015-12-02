package ie.cit.adf.muss.exceptions;

@SuppressWarnings("serial")
public class UsernameExistsException extends IllegalArgumentException  {

    public UsernameExistsException(String s) {
        super(s);
    }
}
