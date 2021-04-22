package net.yakclient.opengl.api.gui;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;

public class GUIProperties {
    private final Map<String, Object> props;

    public GUIProperties(Map<String, Object> props) {
        this.props = Collections.unmodifiableMap(props);
    }

    /**
     * Attempts to retrieve the property stored
     * given the value passed.
     * <p>
     * Note: The way that this works you will receive it as whatever
     * you expect, for example you could use this to cast a String to a
     * LinkedHashMap, however it would cause cast errors when you call
     * any of its methods. This is almost a "hack" to get past the compiler
     * so you should be careful!
     *
     * @param value The value to search parameters by.
     * @param <T>   The expected parameter type, this can cause errors if malformed.
     * @return The value found.
     * @throws IllegalPropertyException If the value cannot be found.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T get(String value) throws IllegalPropertyException {
        return (T) this.props.get(value);
    }

    /**
     * Allows you to iterate through all of the current props.
     * <p>
     * Note: Properties will NOT be ordered from least to greatest based
     * on ID.
     *
     * @param iterator the consumer that will be called over iterations.
     */
    public void iterateProps(BiConsumer<String, Object> iterator) {
        this.props.forEach(iterator);
    }

    /**
     * Determines if the given name of a value is stored
     * in the current properties.
     *
     * @param name the name of the property.
     * @return if the property is found.
     */
    public boolean hasProperty(String name) {
        return this.get(name) != null;
    }

//    static class PropertyNode {
//        private final String name;
//        private final int id;
//
//        public PropertyNode(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            PropertyNode that = (PropertyNode) o;
//            return id == that.id && Objects.equals(name, that.name);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(name, id);
//        }
//
//
//        @Override
//        public String toString() {
//            return "PropertyNode{" +
//                    "name='" + name + '\'' +
//                    ", id=" + id +
//                    '}';
//        }
//    }
//
//    private interface PartialNode {
//        boolean isSame(PropertyNode node);
//
//        String partialValue();
//    }
//
//    private static class IntPartialNode implements PartialNode {
//        private final int partial;
//
//        public IntPartialNode(int partial) {
//            this.partial = partial;
//        }
//
//        @Override
//        public boolean isSame(PropertyNode node) {
//            return this.partial == node.getId();
//        }
//
//        @Override
//        public String partialValue() {
//            return String.valueOf(this.partial);
//        }
//    }
//
//    private static class NamePartialNode implements PartialNode {
//        private final String partial;
//
//        public NamePartialNode(String partial) {
//            this.partial = partial;
//        }
//
//        @Override
//        public boolean isSame(PropertyNode node) {
//            return this.partial.equalsIgnoreCase(node.getName());
//        }
//
//        @Override
//        public String partialValue() {
//            return this.partial;
//        }
//    }
}
