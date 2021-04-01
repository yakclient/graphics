package net.yakclient.opengl.util;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collection;

public class YakGLUtils {
    public static <T> Collection<T> defaultCollection() {
        return new ArrayList<>();
    }

    public static <T extends Buffer> T flipBuf(T buf) {
        buf.flip();
        return buf;
    }

    public static double[] toPrimitiveArray(Double[] initial) {
        final double[] allocation = new double[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static float[] toPrimitiveArray(Float[] initial) {
        final float[] allocation = new float[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static char[] toPrimitiveArray(Character[] initial) {
        final char[] allocation = new char[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static short[] toPrimitiveArray(Short[] initial) {
        final short[] allocation = new short[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static int[] toPrimitiveArray(Integer[] initial) {
        final int[] allocation = new int[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static long[] toPrimitiveArray(Long[] initial) {
        final long[] allocation = new long[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }

    public static boolean[] toPrimitiveArray(Boolean[] initial) {
        final boolean[] allocation = new boolean[initial.length];
        for (int i = 0; i < initial.length; i++) {
            allocation[i] = initial[i];
        }
        return allocation;
    }


}
