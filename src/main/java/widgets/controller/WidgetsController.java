package widgets.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WidgetsController {

    @PostMapping(value = "/widgets")
    public ResponseEntity<Object> createWidget(@RequestBody Map<String, Integer> attributes) {
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @GetMapping("/widgets/{id}")
    public ResponseEntity<Object> getWidget(@PathVariable("id") Integer id) {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}

