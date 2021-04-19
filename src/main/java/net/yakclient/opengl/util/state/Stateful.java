package net.yakclient.opengl.util.state;

public interface Stateful<T> {
     T get();

     T set(T value);

    default T setIf(boolean condition, T value) {
         if (condition)this.set(value);
         return value;
    }
}
