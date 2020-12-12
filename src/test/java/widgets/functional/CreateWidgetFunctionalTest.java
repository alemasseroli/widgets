package widgets.functional;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import widgets.controller.WidgetsController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static widgets.WidgetTestHelper.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WidgetsController.class)
@AutoConfigureMockMvc
public class CreateWidgetFunctionalTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessCreateWidget() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/widgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createWidgetBody()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateWithMissingPropertyIsBadRequest() throws Exception {
        String body = new JSONObject()
                .put("x", randomInt())
                .put("y", randomInt())
                .toString();

        mvc.perform(MockMvcRequestBuilders
                .post("/widgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWithInvalidValueIsBadRequest() throws Exception {
        String body = new JSONObject()
                .put("x", randomInt())
                .put("y", randomInt())
                .put("z", randomInt())
                .put("width", 0)
                .put("height", -1)
                .toString();

        mvc.perform(MockMvcRequestBuilders
                .post("/widgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

}