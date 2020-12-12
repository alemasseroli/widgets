package widgets.widget;

import widgets.storage.WidgetStorageService;

import java.util.List;
import java.util.Map;

public class WidgetService {

    private WidgetStorageService widgetStorageService = new WidgetStorageService();

    public List<Widget> createWidget(Map<String, Integer> attributes) {
        Widget widget = Widget.of(attributes);
        return widgetStorageService.save(widget);
    }

    public Widget updateWidget(String id, Map<String, Integer> attributes) {
        return widgetStorageService.update(id, attributes);
    }

    public void deleteWidget(String id) {
        widgetStorageService.remove(id);
    }

    public List<Widget> getAllWidgets() {
        return widgetStorageService.getAll();
    }

    public Widget getWidget(String id) {
        return widgetStorageService.get(id);
    }
}
