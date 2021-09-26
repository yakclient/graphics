package net.yakclient.graphics.util;

import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.Iterator;

/**
 * The Aggregation interface specifies a iterable amount of nodes that are
 * grouped into {@code AggregationNodes}. A Aggregation MUST implement
 * the Iterable interface and provide context by which groups of values
 * can be sorted.
 *
 * @param <T> The node that values will be grouped by.
 *
 * @author Durgan McBroom
 */
public interface Aggregation<T extends AggregateNode> extends Iterable<T> {
    @NotNull
    @Override
    AggregationIterator<T> iterator();

    Buffer createBuf();

    <B extends Buffer> B asBuf(B buf);

    boolean add(T node);

    <A extends Aggregation<T>> A combine(A aggregate);

    int size();

    default T get(int i) {
        return this.iterator().nodes[i];
    }

    default boolean isEmpty() {
        return this.size() == 0;
    }

    class AggregationIterator<T extends AggregateNode> implements Iterator<T> {
        private final T[] nodes;
        private int current = 0;

        public AggregationIterator(T[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean hasNext() {
            return current < nodes.length;
        }

        @Override
        public T next() {
            return nodes[this.current++];
        }
    }
}
