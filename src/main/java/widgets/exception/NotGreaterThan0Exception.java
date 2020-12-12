package widgets.exception;

import static java.text.MessageFormat.format;

public class NotGreaterThan0Exception extends PrintableRuntimeException {

    public NotGreaterThan0Exception(String property) {
        super(format("Property {0} must be greater than 0.", property));
    }

}

