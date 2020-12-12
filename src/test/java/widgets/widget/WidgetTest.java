package widgets.widget;

import org.junit.jupiter.api.Test;
import widgets.exception.MissingPropertyException;
import widgets.exception.NotGreaterThan0Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WidgetTest {

    private Random random = new Random();

    @Test
    void testOfWithFullParameters() {
        int x = random.nextInt();
        int y = random.nextInt();
        int z = random.nextInt();
        int width = randomPositiveInt();
        int height = randomPositiveInt();

        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", x);
        attrs.put("y", y);
        attrs.put("z", z);
        attrs.put("width", width);
        attrs.put("height", height);
        Widget widget = Widget.of(attrs);

        assertThat(widget.getId()).isInstanceOf(String.class);
        assertThat(widget.getX()).isEqualTo(x);
        assertThat(widget.getY()).isEqualTo(y);
        assertThat(widget.getZ()).isEqualTo(z);
        assertThat(widget.getWidth()).isEqualTo(width);
        assertThat(widget.getHeight()).isEqualTo(height);
    }

    @Test
    void testOfWithInvalidWidthOrHeightFails() {
        int x = random.nextInt();
        int y = random.nextInt();
        int z = random.nextInt();
        int width = 0;
        int height = randomPositiveInt();

        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", x);
        attrs.put("y", y);
        attrs.put("z", z);
        attrs.put("width", width);
        attrs.put("height", height);
        assertThrows(NotGreaterThan0Exception.class, () -> Widget.of(attrs));

        width = randomPositiveInt();
        height = -1;
        attrs.put("width", width);
        attrs.put("height", height);
        assertThrows(NotGreaterThan0Exception.class, () -> Widget.of(attrs));
    }

    @Test
    void testOfWithMissingPropertiesFails() {
        int x = random.nextInt();
        int z = random.nextInt();
        int height = randomPositiveInt();
        int width = randomPositiveInt();

        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", x);
        attrs.put("z", z);
        attrs.put("width", width);
        attrs.put("height", height);
        assertThrows(MissingPropertyException.class, () -> Widget.of(attrs));
    }

    @Test
    void testCorrectClone() {
        int x = random.nextInt();
        int y = random.nextInt();
        int z = random.nextInt();
        int width = randomPositiveInt();
        int height = randomPositiveInt();

        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", x);
        attrs.put("y", y);
        attrs.put("z", z);
        attrs.put("width", width);
        attrs.put("height", height);
        Widget widget = Widget.of(attrs);

        int modifiedWidth = width + randomPositiveInt();
        int modifiedHeight = height + randomPositiveInt();

        Map<String, Integer> modifications = new HashMap<>();
        modifications.put("width", modifiedWidth);
        modifications.put("height", modifiedHeight);
        Widget cloned = widget.cloneWith(modifications);

        assertThat(cloned.getId()).isInstanceOf(String.class);
        assertThat(cloned.getX()).isEqualTo(x);
        assertThat(cloned.getY()).isEqualTo(y);
        assertThat(cloned.getZ()).isEqualTo(z);
        assertThat(cloned.getWidth()).isEqualTo(modifiedWidth);
        assertThat(cloned.getHeight()).isEqualTo(modifiedHeight);
    }

    @Test
    void testCloneWithInvalidParamsFails() {
        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", random.nextInt());
        attrs.put("y", random.nextInt());
        attrs.put("z", random.nextInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget = Widget.of(attrs);

        Map<String, Integer> modifications = new HashMap<>();
        modifications.put("width", 0);
        assertThrows(NotGreaterThan0Exception.class, () -> widget.cloneWith(modifications));
    }

    private int randomPositiveInt() {
        return random.nextInt(50) + 1;
    }
}