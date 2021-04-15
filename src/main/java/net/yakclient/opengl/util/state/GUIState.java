package net.yakclient.opengl.util.state;

public class GUIState<T> implements Stateful<T> {
    private T value;

    public GUIState(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public T set(T value) {
        return this.value = value;
    }
}
