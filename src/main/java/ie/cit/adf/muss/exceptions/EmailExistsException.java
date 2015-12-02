package ie.cit.adf.muss.exceptions;

@SuppressWarnings("serial")
public class EmailExistsException extends IllegalArgumentException  {

    public EmailExistsException(String s) {
        super(s);
    }
}
