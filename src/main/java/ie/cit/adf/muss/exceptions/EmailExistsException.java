package ie.cit.adf.muss.exceptions;

public class EmailExistsException extends IllegalArgumentException  {

    public EmailExistsException(String s) {
        super(s);
    }
}
