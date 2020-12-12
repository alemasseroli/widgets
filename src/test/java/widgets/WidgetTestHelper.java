package widgets;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WidgetTestHelper {

    private static Random random = new Random();

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomPositiveInt() {
        return random.nextInt(50) + 1;
    }

    public static String createWidget(MockMvc mvc) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/widgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createWidgetBody()))
                .andExpect(status().isCreated())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        return JsonPath.parse(response).read("$[0].id");
    }

    public static String createWidgetBody() {
        return new JSONObject()
                .put("x", randomInt())
                .put("y", randomInt())
                .put("z", randomInt())
                .put("width", randomPositiveInt())
                .put("height", randomPositiveInt())
                .toString();
    }

}
