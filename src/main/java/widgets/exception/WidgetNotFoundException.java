package widgets.exception;

import java.text.MessageFormat;

public class WidgetNotFoundException extends PrintableRuntimeException {

    public WidgetNotFoundException(String id) {
        super(MessageFormat.format("Widget not found for id: {0}.", id));
    }
}
