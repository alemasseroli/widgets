package widgets.widget;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import widgets.exception.MissingPropertyException;
import widgets.storage.WidgetStorageService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WidgetServiceTest {

    private WidgetService widgetService;

    @Mock
    private WidgetStorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = mock(WidgetStorageService.class);
        widgetService = new WidgetService(storageService);
    }

    @Test
    void testCorrectGetAllWidgetsInside() {
        Map<String, Integer> attrs = new HashMap<>();
        attrs.put("x", 50);
        attrs.put("y", 50);
        attrs.put("z", 1);
        attrs.put("width", 100);
        attrs.put("height", 100);
        Widget widget1 = Widget.of(attrs);

        attrs.put("x", 50);
        attrs.put("y", 100);
        attrs.put("z", 2);
        Widget widget2 = Widget.of(attrs);

        attrs.put("x", 100);
        attrs.put("y", 100);
        attrs.put("z", 3);
        Widget widget3 = Widget.of(attrs);

        Map<String, Integer> limits = new HashMap<>();
        limits.put("x1", 0);
        limits.put("y1", 0);
        limits.put("x2", 100);
        limits.put("y2", 150);
        when(storageService.getAll()).thenReturn(asList(widget1, widget2, widget3));
        assertThat(widgetService.getAllWidgetsInside(limits)).isEqualTo(asList(widget1, widget2));
    }

    @Test
    void testGetAllWidgetsInsideUndefinedLimitIsBadRequest() {
        Map<String, Integer> limits = new HashMap<>();
        limits.put("x2", 100);
        limits.put("y2", 150);
        when(storageService.getAll()).thenReturn(emptyList());
        assertThrows(MissingPropertyException.class, () -> widgetService.getAllWidgetsInside(limits));
    }

}