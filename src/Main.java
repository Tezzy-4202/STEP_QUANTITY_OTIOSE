/**
 * QuantityMeasurementApp - UC5: Unit-to-Unit Conversion
 * This class provides explicit conversion between length units and 
 * demonstrates Method Overloading and Encapsulation.
 */

package com.apps.quantitymeasurement;

import java.util.Objects;

public class Main {

    public enum LengthUnit {
        YARDS(36.0),
        FEET(12.0),
        INCH(1.0),
        CENTIMETERS(0.393701);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        private double convertToBase(double value) {
            return value * this.factor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        /**
         * Private helper to validate inputs during instantiation.
         */
        private static void validate(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        }

        /**
         * Instance method: Converts current quantity to a new target unit.
         * returns a new QuantityLength instance (Immutability).
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        /**
         * Static API Method: Pure logic to convert between two units.
         */
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == target) return value;
            double baseValue = source.convertToBase(value);
            return baseValue / target.factor;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength that = (QuantityLength) obj;
            return Math.abs(this.unit.convertToBase(this.value) - 
                            that.unit.convertToBase(that.value)) < 1e-6;
        }

        @Override
        public String toString() {
            return String.format("%.2f %s", value, unit);
        }
    }

    // --- API Demonstration Methods (Method Overloading) ---

    /**
     * Overload 1: Converts raw double values.
     */
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.printf("Input: %.2f %s -> Output: %.4f %s%n", value, from, result, to);
    }

    /**
     * Overload 2: Converts an existing QuantityLength object.
     */
    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        QuantityLength result = length.convertTo(to);
        System.out.println("Converted Object: " + result);
    }

    public static void main(String[] args) {
        System.out.println("--- UC5: Unit Conversion API ---");
        
        // Testing Static API
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);   // 12.0
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET); // 9.0
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCH); 

        // Testing Instance Method
        QuantityLength myLength = new QuantityLength(36.0, LengthUnit.INCH);
        demonstrateLengthConversion(myLength, LengthUnit.YARDS); // 1.00 YARDS
    }
}
