package widgets.functional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import widgets.controller.WidgetsController;

import static java.text.MessageFormat.format;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static widgets.WidgetTestHelper.createWidget;

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

}
