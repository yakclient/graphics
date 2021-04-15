package net.yakclient.opengl.util.state;

public interface Stateful<T> {
     T get();

     T set(T value);
}
