package widgets.storage;

import widgets.exception.WidgetNotFoundException;
import widgets.widget.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WidgetStorageService {

    private List<Widget> widgets;

    public WidgetStorageService() {
        this(new ArrayList<>());
    }

    public WidgetStorageService(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public List<Widget> save(Widget widget) {
        Integer widgetZ = widget.getZ();
        if (widgets.isEmpty()) {
            if (widgetZ == null) widget.setZ(1);
            widgets.add(widget);
            return widgets;
        }
        if (widgetZ == null) {
            addLast(widget);
            return widgets;
        }
        boolean added = false;
        int i = 0;
        while (i < widgets.size()) {
            Widget current = widgets.get(i);
            if (current.getZ().equals(widgetZ)) {
                current.setZ(++widgetZ);
                if (!added) {
                    widgets.add(i, widget);
                    added = true;
                    i++;
                }
            } else if (current.getZ() > widgetZ && !added) {
                widgets.add(i, widget);
                added = true;
                break;
            }
            i++;
        }
        if (!added) {
            addLast(widget);
        }
        return widgets;
    }

    private void addLast(Widget widget) {
        if (widget.getZ() == null) {
            Widget lastWidget = widgets.get(widgets.size() - 1);
            widget.setZ(lastWidget.getZ() + 1);
        }
        widgets.add(widget);
    }

    public void remove(String id) {
        int previousSize = widgets.size();
        widgets = widgets.stream()
                .filter(w -> !w.getId().equals(id))
                .collect(Collectors.toList());
        if (widgets.size() == previousSize) throw new WidgetNotFoundException(id);
    }

    public Widget update(String id, Map<String, Integer> attributes) {
        Widget widget = get(id);
        remove(id);
        Widget updated = widget.cloneWith(attributes);
        save(updated);
        return updated;
    }

    public List<Widget> getAll() {
        return widgets;
    }

    public Widget get(String id) {
        return widgets.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new WidgetNotFoundException(id));
    }

}
