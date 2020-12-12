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
public class DeleteWidgetFunctionalTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessDeleteWidget() throws Exception {
        String id = createWidget(mvc);
        mvc.perform(MockMvcRequestBuilders
                .delete(format("/widgets/{0}", id)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetWidgetWithInvalidIdIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete(format("/widgets/{0}", "invalidId")))
                .andExpect(status().isNotFound());
    }

}
