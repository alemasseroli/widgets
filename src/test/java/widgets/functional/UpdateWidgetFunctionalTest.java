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

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static widgets.WidgetTestHelper.createWidget;
import static widgets.WidgetTestHelper.randomInt;

@RunWith(SpringRunner.class)
@WebMvcTest(WidgetsController.class)
@AutoConfigureMockMvc
public class UpdateWidgetFunctionalTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessUpdateWidget() throws Exception {
        String id = createWidget(mvc);
        String body = new JSONObject()
                .put("x", randomInt())
                .put("y", randomInt())
                .toString();

        mvc.perform(MockMvcRequestBuilders
                .put(MessageFormat.format("/widgets/{0}", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWithInvalidValueIsBadRequest() throws Exception {
        String id = createWidget(mvc);
        String body = new JSONObject()
                .put("width", 0)
                .put("height", -1)
                .toString();

        mvc.perform(MockMvcRequestBuilders
                .put(MessageFormat.format("/widgets/{0}", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWithInvalidIdIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put(MessageFormat.format("/widgets/{0}", "invalidId"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject().toString()))
                .andExpect(status().isNotFound());
    }

}