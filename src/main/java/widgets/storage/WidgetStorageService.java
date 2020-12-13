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

    WidgetStorageService(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public List<Widget> save(Widget widget) {
        List<Widget> newWidgets = widgets.stream()
                .map(Widget::copy)
                .collect(Collectors.toList());
        insertToWidgetList(widget, newWidgets);
        widgets = newWidgets;
        return widgets;
    }

    private void insertToWidgetList(Widget widget, List<Widget> newWidgets) {
        Integer widgetZ = widget.getZ();
        if (newWidgets.isEmpty()) {
            if (widgetZ == null) widget.setZ(1);
            newWidgets.add(widget);
            return;
        }
        if (widgetZ == null) {
            addLast(widget, newWidgets);
            return;
        }
        int i = 0;
        boolean added = false;
        while (i < newWidgets.size()) {
            Widget current = newWidgets.get(i);
            if (current.getZ().equals(widgetZ)) {
                current.setZ(++widgetZ);
                if (!added) {
                    newWidgets.add(i, widget);
                    added = true;
                    i++;
                }
            } else if (current.getZ() > widgetZ && !added) {
                newWidgets.add(i, widget);
                added = true;
                break;
            }
            i++;
        }
        if (!added) {
            addLast(widget, newWidgets);
        }
    }

    private void addLast(Widget widget, List<Widget> widgetList) {
        if (widget.getZ() == null) {
            Widget lastWidget = widgetList.get(widgetList.size() - 1);
            widget.setZ(lastWidget.getZ() + 1);
        }
        widgetList.add(widget);
    }

    public void remove(String id) {
        widgets = removeFromWidgetList(id);
    }

    private List<Widget> removeFromWidgetList(String id) {
        List<Widget> updatedWidgets = widgets.stream()
                .filter(w -> !w.getId().equals(id))
                .collect(Collectors.toList());
        if (updatedWidgets.size() == widgets.size()) throw new WidgetNotFoundException(id);
        return updatedWidgets;
    }

    public Widget update(String id, Map<String, Integer> attributes) {
        Widget widget = get(id);
        List<Widget> updatedWidgets = removeFromWidgetList(id);
        Widget updated = widget.cloneWith(attributes);
        insertToWidgetList(updated, updatedWidgets);
        widgets = updatedWidgets;
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
