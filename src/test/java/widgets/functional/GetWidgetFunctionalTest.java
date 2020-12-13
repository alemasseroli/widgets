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

import static java.text.MessageFormat.format;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static widgets.WidgetTestHelper.createWidget;
import static widgets.WidgetTestHelper.randomInt;

@RunWith(SpringRunner.class)
@WebMvcTest(WidgetsController.class)
@AutoConfigureMockMvc
public class GetWidgetFunctionalTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessGetWidget() throws Exception {
        String id = createWidget(mvc);
        mvc.perform(MockMvcRequestBuilders
                .get(format("/widgets/{0}", id)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWidgetWithInvalidIdIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get(format("/widgets/{0}", "invalidId")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSuccessGetAllWidgets() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/widgets"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSuccessSearchWidgets() throws Exception {
        String body = new JSONObject()
                .put("x1", randomInt())
                .put("y1", randomInt())
                .put("x2", randomInt())
                .put("y2", randomInt())
                .toString();
        mvc.perform(MockMvcRequestBuilders
                .post("/widgets/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());
    }

    @Test
    public void testMissingPropertiesSearchIsBadRequest() throws Exception {
        String body = new JSONObject()
                .put("x1", randomInt())
                .put("y1", randomInt())
                .toString();
        mvc.perform(MockMvcRequestBuilders
                .post("/widgets/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

}
