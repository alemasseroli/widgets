package widgets.storage;

import org.junit.jupiter.api.Test;
import widgets.exception.NotGreaterThan0Exception;
import widgets.exception.WidgetNotFoundException;
import widgets.widget.Widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static widgets.WidgetTestHelper.randomInt;
import static widgets.WidgetTestHelper.randomPositiveInt;

class WidgetStorageServiceTest {

    private WidgetStorageService service;

    @Test
    void testSaveWithCorrectOrder() {
        service = new WidgetStorageService();

        Map<String, Integer> attrs = new HashMap<>();
        int z = randomInt();
        attrs.put("z", z);
        attrs.put("x", randomInt());
        attrs.put("y", randomInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget1 = Widget.of(attrs);

        service.save(widget1);
        assertThat(service.getAll()).isEqualTo(asList(widget1));

        attrs.put("z", z + 1);
        Widget widget2 = Widget.of(attrs);
        service.save(widget2);
        assertThat(service.getAll()).isEqualTo(asList(widget1, widget2));

        attrs.put("z", z - 1);
        Widget widget3 = Widget.of(attrs);
        service.save(widget3);
        assertThat(service.getAll()).isEqualTo(asList(widget3, widget1, widget2));

        attrs.put("z", z);
        Widget widget4 = Widget.of(attrs);
        service.save(widget4);
        assertThat(service.getAll()).isEqualTo(asList(widget3, widget4, widget1, widget2));

        attrs.remove("z");
        Widget widget5 = Widget.of(attrs);
        service.save(widget5);
        assertThat(service.getAll()).isEqualTo(asList(widget3, widget4, widget1, widget2, widget5));
    }

    @Test
    void testCorrectRemove() {
        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", randomInt());
        attrs.put("y", randomInt());
        attrs.put("z", randomInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget = Widget.of(attrs);
        service = new WidgetStorageService(asList(widget));

        service.remove(widget.getId());
        assertThat(service.getAll()).isEmpty();
    }

    @Test
    void testRemoveWithInvalidIdFails() {
        service = new WidgetStorageService();
        assertThrows(WidgetNotFoundException.class, () -> service.remove("invalidId"));
    }


    @Test
    void testCorrectUpdate() {
        Map<String, Integer> attrs = new HashMap<>();
        int x = randomInt();
        int y = randomInt();
        attrs.put("x", x);
        attrs.put("y", y);
        attrs.put("z", randomInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget = Widget.of(attrs);
        service = new WidgetStorageService(asList(widget));

        int modifiedX = x + randomPositiveInt();
        int modifiedY = y + randomPositiveInt();
        Map<String, Integer> modifications = new HashMap<>();
        modifications.put("x", modifiedX);
        modifications.put("y", modifiedY);

        Widget modified = service.update(widget.getId(), modifications);
        assertThat(modified.getId()).isEqualTo(widget.getId());
        assertThat(modified.getX()).isEqualTo(modifiedX);
        assertThat(modified.getY()).isEqualTo(modifiedY);
        assertThat(modified.getZ()).isEqualTo(widget.getZ());
        assertThat(modified.getWidth()).isEqualTo(widget.getWidth());
        assertThat(modified.getHeight()).isEqualTo(widget.getHeight());
        assertThat(modified.getLastUpdated()).isAfterOrEqualTo(widget.getLastUpdated());
    }

    @Test
    void testCorrectUpdateChangingOrder() {
        Map<String, Integer> attrs = new HashMap<>();
        int z = randomInt();
        attrs.put("x", randomInt());
        attrs.put("y", randomInt());
        attrs.put("z", z);
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget1 = Widget.of(attrs);

        attrs.put("z", z + 1);
        Widget widget2 = Widget.of(attrs);
        service = new WidgetStorageService(asList(widget1, widget2));


        Map<String, Integer> modifications = new HashMap<>();
        modifications.put("z", z + 2);
        service.update(widget1.getId(), modifications);

        List<Widget> widgets = service.getAll();
        assertThat(widgets.size()).isEqualTo(2);
        assertThat(widgets.get(0)).isEqualTo(widget2);

        Widget modified = widgets.get(1);
        assertThat(modified.getId()).isEqualTo(widget1.getId());
        assertThat(modified.getX()).isEqualTo(widget1.getX());
        assertThat(modified.getY()).isEqualTo(widget1.getY());
        assertThat(modified.getZ()).isEqualTo(widget1.getZ() + 2);
        assertThat(modified.getWidth()).isEqualTo(widget1.getWidth());
        assertThat(modified.getHeight()).isEqualTo(widget1.getHeight());
        assertThat(modified.getLastUpdated()).isAfterOrEqualTo(widget1.getLastUpdated());
    }

    @Test
    void testUpdateWithInvalidPropertyFails() {
        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", randomInt());
        attrs.put("y", randomInt());
        attrs.put("z", randomInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget = Widget.of(attrs);
        service = new WidgetStorageService(asList(widget));

        Map<String, Integer> modifications = new HashMap<>();
        modifications.put("width", 0);
        modifications.put("height", -10);
        assertThrows(NotGreaterThan0Exception.class, () -> service.update(widget.getId(), modifications));
    }

    @Test
    void testUpdateWithInvalidIdFails() {
        service = new WidgetStorageService();
        assertThrows(WidgetNotFoundException.class, () -> service.update("invalidId", new HashMap<>()));
    }

    @Test
    void testGet() {
        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", randomInt());
        attrs.put("y", randomInt());
        attrs.put("z", randomInt());
        attrs.put("width", randomPositiveInt());
        attrs.put("height", randomPositiveInt());
        Widget widget = Widget.of(attrs);
        Widget widget2 = Widget.of(attrs);
        service = new WidgetStorageService(asList(widget, widget2));

        assertThat(service.get(widget.getId())).isEqualTo(widget);
    }

    @Test
    void testGetWithInvalidIdFails() {
        service = new WidgetStorageService();
        assertThrows(WidgetNotFoundException.class, () -> service.get("invalidId"));
    }

}