package widgets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import widgets.exception.MissingPropertyException;
import widgets.exception.NotGreaterThan0Exception;
import widgets.exception.WidgetNotFoundException;
import widgets.widget.Widget;
import widgets.widget.WidgetService;

import java.util.List;
import java.util.Map;

@RestController
public class WidgetsController {

    private WidgetService service = new WidgetService();

    @PostMapping(value = "/widgets")
    public ResponseEntity<Object> createWidget(@RequestBody Map<String, Integer> attributes) {
        try {
            final List<Widget> widgets = service.createWidget(attributes);
            return new ResponseEntity<>(widgets, HttpStatus.CREATED);
        } catch (MissingPropertyException | NotGreaterThan0Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/widgets/{id}")
    public ResponseEntity<Object> updateWidget(@PathVariable("id") String id,
                                               @RequestBody Map<String, Integer> attributes) {
        try {
            final Widget widget = service.updateWidget(id, attributes);
            return new ResponseEntity<>(widget, HttpStatus.OK);
        } catch (WidgetNotFoundException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.NOT_FOUND);
        } catch (NotGreaterThan0Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/widgets/{id}")
    public ResponseEntity<Object> deleteWidget(@PathVariable("id") String id) {
        try {
            service.deleteWidget(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (WidgetNotFoundException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/widgets")
    public ResponseEntity<Object> getAllWidgets() {
        final List<Widget> widgets = service.getAllWidgets();
        return new ResponseEntity<>(widgets, HttpStatus.OK);
    }

    @GetMapping("/widgets/{id}")
    public ResponseEntity<Object> getWidget(@PathVariable("id") String id) {
        try {
            final Widget widget = service.getWidget(id);
            return new ResponseEntity<>(widget, HttpStatus.OK);
        } catch (WidgetNotFoundException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.NOT_FOUND);
        }
    }

}

