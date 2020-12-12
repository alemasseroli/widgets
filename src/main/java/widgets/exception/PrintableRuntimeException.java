package widgets.exception;

import org.json.JSONObject;

public class PrintableRuntimeException extends RuntimeException {

    PrintableRuntimeException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return new JSONObject()
                .put("message", this.getMessage())
                .toString();
    }

}
