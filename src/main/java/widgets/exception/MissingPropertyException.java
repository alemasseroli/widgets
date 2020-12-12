package widgets.exception;

import static java.text.MessageFormat.format;

public class MissingPropertyException extends PrintableRuntimeException {

    public MissingPropertyException(String property) {
        super(format("Missing property: {0}.", property));
    }

}
