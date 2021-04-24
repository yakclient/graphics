package net.yakclient.opengl.util;

import org.jetbrains.annotations.NotNull;

public interface ColorFunction {
    ColorAggregation toAggregation(VerticeAggregation aggregation);


    @NotNull
    static ColorAggregation applyColorEffect(int offset, float alpha, @NotNull VerticeAggregation vertices, @NotNull RGBColor... colors) {
        if (colors.length == 0) return new ColorAggregation();
        //        double centerX = 0;
//        double centerY = 0;
//
//        for (int i = 0; i < vertices.size(); i++) {
//            final VerticeAggregation.Vertice vertice = vertices.get(i);
//            if (i == 0) {
//                centerX = vertice.getX();
//                centerY = vertice.getY();
//            } else {
//                centerX = (centerX + vertice.getX()) / 2;
//                centerY = (centerY + vertice.getY()) / 2;
//            }
//        }

        final var pointMappings = new int[vertices.size()];
        for (int color = 0; color < colors.length; color++) {
            final int initialSize = vertices.size() / colors.length;
            final int colorSize = color + 1 == colors.length ? (vertices.size() - (initialSize * colors.length)) + initialSize : initialSize;
            for (int i = 0; i < colorSize; i++) {
                pointMappings[(i) + (color * initialSize)] = color;
            }
        }

        final ColorAggregation aggregation = new ColorAggregation();

        for (int color = 0; color < pointMappings.length; color++) {
            final RGBColor mapping = colors[pointMappings[(color + offset) % pointMappings.length]];
            aggregation.add(mapping.getRed(), mapping.getBlue(), mapping.getGreen(), alpha);
        }

        return aggregation;
    }
}
