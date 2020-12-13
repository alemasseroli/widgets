package widgets.widget;

import widgets.exception.MissingPropertyException;
import widgets.storage.WidgetStorageService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WidgetService {

    private WidgetStorageService widgetStorageService;

    public WidgetService() {
        this(new WidgetStorageService());
    }

    WidgetService(WidgetStorageService widgetStorageService) {
        this.widgetStorageService = widgetStorageService;
    }

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

    public List<Widget> getAllWidgetsInside(Map<String, Integer> limits) {
        int x1 = limitValue("x1", limits);
        int y1 = limitValue("y1", limits);
        int x2 = limitValue("x2", limits);
        int y2 = limitValue("y2", limits);
        return getAllWidgets().stream()
                .filter(widget -> widget.isContainedBy(x1, y1, x2, y2))
                .collect(Collectors.toList());
    }

    private int limitValue(String name, Map<String, Integer> limits) {
        return Optional.ofNullable(limits.get(name))
                .orElseThrow(() -> new MissingPropertyException(name));
    }

}
