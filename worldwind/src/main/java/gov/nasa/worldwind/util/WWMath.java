/*
 * Copyright (c) 2016 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration. All Rights Reserved.
 */

package gov.nasa.worldwind.util;

import android.graphics.Rect;

import gov.nasa.worldwind.geom.Matrix4;

/**
 * Collection of static methods for performing common World Wind computations.
 */
public class WWMath {

    /**
     * Restricts a value to the range [min, max] degrees, clamping values outside the range. Values less than min are
     * returned as min, and values greater than max are returned as max. Values within the range are returned
     * unmodified.
     * <p/>
     * The result of this method is undefined if min is greater than max.
     *
     * @param value the values to clamp
     * @param min   the minimum value
     * @param max   the maximum value
     *
     * @return the specified values clamped to the range [min, max] degrees
     */
    public static double clamp(double value, double min, double max) {
        return value > max ? max : (value < min ? min : value);
    }

    /**
     * Restricts an angle to the range [-180, +180] degrees, clamping angles outside the range. Angles less than -180
     * are returned as -180, and angles greater than +180 are returned as +180. Angles within the range are returned
     * unmodified.
     *
     * @param degrees the angle to clamp in degrees
     *
     * @return the specified angle clamped to the range [-180, +180] degrees
     */
    public static double clampAngle180(double degrees) {
        return degrees > 180 ? 180 : (degrees < -180 ? -180 : degrees);
    }

    /**
     * Restricts an angle to the range [0, 360] degrees, clamping angles outside the range. Angles less than 0 are
     * returned as 0, and angles greater than 360 are returned as 360. Angles within the range are returned unmodified.
     *
     * @param degrees the angle to clamp in degrees
     *
     * @return the specified angle clamped to the range [0, 360] degrees
     */
    public static double clampAngle360(double degrees) {
        return degrees > 360 ? 360 : (degrees < 0 ? 0 : degrees);
    }

    /**
     * Restricts an angle to the range [-180, +180] degrees, wrapping angles outside the range. Wrapping takes place as
     * though traversing the edge of a unit circle; angles less than -180 wrap back to +180, while angles greater than
     * +180 wrap back to -180.
     *
     * @param degrees the angle to wrap in degrees
     *
     * @return the specified angle wrapped to [-180, +180] degrees
     */
    public static double normalizeAngle180(double degrees) {
        double angle = degrees % 360;
        return angle > 180 ? angle - 360 : (angle < -180 ? 360 + angle : angle);
    }

    /**
     * Restricts an angle to the range [0, 360] degrees, wrapping angles outside the range. Wrapping takes place as
     * though traversing the edge of a unit circle; angles less than 0 wrap back to 360, while angles greater than 360
     * wrap back to 0.
     *
     * @param degrees the angle to wrap in degrees
     *
     * @return the specified angle wrapped to [0, 360] degrees
     */
    public static double normalizeAngle360(double degrees) {
        double angle = degrees % 360;
        return angle >= 0 ? angle : (angle < 0 ? 360 + angle : 360 - angle);
    }

    /**
     * Computes the bounding rectangle for a unit quadrilateral after applying a transformation matrix to that
     * quadrilateral.
     *
     * @param transformMatrix The matrix to apply to the unit quadrilateral.
     *
     * @return The computed bounding rectangle.
     */
    public static Rect boundingRectForUnitQuad(Matrix4 transformMatrix) {
        if (transformMatrix == null) {
            throw new IllegalArgumentException(Logger.logMessage(Logger.ERROR, "WWMath", "boundingRectForUnitQuad",
                "missingMatrix"));
        }

        double[] m = transformMatrix.m;
        // transform of (0, 0)
        double x1 = m[3];
        double y1 = m[7];
        // transform of (1, 0)
        double x2 = m[0] + m[3];
        double y2 = m[4] + m[7];
        // transform of (0, 1)
        double x3 = m[1] + m[3];
        double y3 = m[5] + m[7];
        // transform of (1, 1)
        double x4 = m[0] + m[1] + m[3];
        double y4 = m[4] + m[5] + m[7];

        int minX = (int) Math.floor(Math.min(Math.min(x1, x2), Math.min(x3, x4)));
        int maxX = (int) Math.ceil(Math.max(Math.max(x1, x2), Math.max(x3, x4)));
        int minY = (int) Math.floor(Math.min(Math.min(y1, y2), Math.min(y3, y4)));
        int maxY = (int) Math.ceil(Math.max(Math.max(y1, y2), Math.max(y3, y4)));

        return new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Indicates whether a specified value is a power of two.
     *
     * @param value the value to test
     *
     * @return true if the specified value is a power of two, false othwerwise
     */
    public static boolean isPowerOfTwo(int value) {
        return value != 0 && (value & (value - 1)) == 0;
    }
}
