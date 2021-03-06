package widgets.widget;

import widgets.exception.MissingPropertyException;
import widgets.exception.NotGreaterThan0Exception;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Widget {

    private String id;
    private int x;
    private int y;
    private Integer z;
    private int width;
    private int height;
    private Instant lastUpdated;

    public String getId() {
        return id;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
        this.lastUpdated = Instant.now();
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

    public Widget copy() {
        return new Widget(
                this.id,
                this.x,
                this.y,
                this.z,
                this.width,
                this.height,
                this.lastUpdated);
    }

    boolean isContainedBy(int x1, int y1, int x2, int y2) {
        return x1 <= (x - width / 2) &&
                y1 <= (y - height / 2) &&
                x2 >= (x + width / 2) &&
                y2 >= (y + height / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return id.equals(widget.id) &&
                x == widget.x &&
                y == widget.y &&
                z.equals(widget.z) &&
                width == widget.width &&
                height == widget.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getX(), getY(), getZ(), getWidth(), getHeight(), getLastUpdated());
    }

    private static int getPropertyOrCloned(String property, Map<String, Integer> attr, Integer clonedValue) {
        return Optional.ofNullable(attr.get(property)).orElse(clonedValue);
    }

    private static int getProperty(String property, Map<String, Integer> attr) {
        return Optional.ofNullable(attr.get(property))
                .orElseThrow(() -> new MissingPropertyException(property));
    }

    private Widget(WidgetBuilder builder) {
        this(UUID.randomUUID().toString(), builder);
    }

    private Widget(String id, WidgetBuilder builder) {
        this(id, builder.x, builder.y, builder.z, builder.width, builder.height, builder.lastUpdated);
    }

    private Widget(String id, int x, int y, Integer z, int width, int height, Instant lastUpdated) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.lastUpdated = lastUpdated;
    }

    public static class WidgetBuilder {
        private int x;
        private int y;
        private Integer z;
        private int width;
        private int height;
        private Instant lastUpdated;

        WidgetBuilder x(int x) {
            this.x = x;
            return this;
        }

        WidgetBuilder y(int y) {
            this.y = y;
            return this;
        }

        WidgetBuilder z(Integer z) {
            this.z = z;
            return this;
        }

        WidgetBuilder width(int width) {
            validateGreaterThan0("width", width);
            this.width = width;
            return this;
        }

        WidgetBuilder height(int height) {
            validateGreaterThan0("height", height);
            this.height = height;
            return this;
        }

        private void validateGreaterThan0(String property, int value) {
            if (value <= 0) throw new NotGreaterThan0Exception(property);
        }

        Widget build() {
            this.lastUpdated = Instant.now();
            return new Widget(this);
        }

    }


}
