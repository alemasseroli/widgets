package widgets.widget;

import widgets.exception.MissingPropertyException;
import widgets.exception.NotGreaterThan0Exception;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Widget {

    private String id;
    private int x;
    private int y;
    private Integer z;
    private int width;
    private int height;
    private final Instant lastUpdated;

    public String getId() {
        return id;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public static Widget of(Map<String, Integer> attr) {
        return new WidgetBuilder()
                .x(getProperty("x", attr))
                .y(getProperty("y", attr))
                .z(attr.get("z"))
                .width(getProperty("width", attr))
                .height(getProperty("height", attr))
                .build();
    }

    public Widget cloneWith(Map<String, Integer> attr) {
        Widget widget = new WidgetBuilder()
                .x(getPropertyOrCloned("x", attr, x))
                .y(getPropertyOrCloned("y", attr, y))
                .z(getPropertyOrCloned("z", attr, z))
                .width(getPropertyOrCloned("width", attr, width))
                .height(getPropertyOrCloned("height", attr, height))
                .build();
        widget.id = this.id;
        return widget;
    }

    private static int getPropertyOrCloned(String property, Map<String, Integer> attr, Integer clonedValue) {
        return Optional.ofNullable(attr.get(property)).orElse(clonedValue);
    }

    private static int getProperty(String property, Map<String, Integer> attr) {
        return Optional.ofNullable(attr.get(property))
                .orElseThrow(() -> new MissingPropertyException(property));
    }

    private Widget(WidgetBuilder builder) {
        this.id = UUID.randomUUID().toString();
        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.width = builder.width;
        this.height = builder.height;
        this.lastUpdated = builder.lastUpdated;
    }

    public static class WidgetBuilder {
        private int x;
        private int y;
        private int z;
        private int width;
        private int height;
        private Instant lastUpdated;

        public WidgetBuilder x(int x) {
            this.x = x;
            return this;
        }

        public WidgetBuilder y(int y) {
            this.y = y;
            return this;
        }

        public WidgetBuilder z(int z) {
            this.z = z;
            return this;
        }

        public WidgetBuilder width(int width) {
            validateGreaterThan0("width", width);
            this.width = width;
            return this;
        }

        public WidgetBuilder height(int height) {
            validateGreaterThan0("height", height);
            this.height = height;
            return this;
        }

        private void validateGreaterThan0(String property, int value) {
            if (value <= 0) throw new NotGreaterThan0Exception(property);
        }

        public Widget build() {
            this.lastUpdated = Instant.now();
            return new Widget(this);
        }

    }


}
