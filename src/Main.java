/**
 * QuantityMeasurementApp - UC4: Extended Unit Support
 * Demonstrates scalability by adding Yards and Centimeters via Enum modification.
 */

package com.apps.quantitymeasurement;

public class Main {

    public enum LengthUnit {
        // Factors relative to 1.0 Inch
        YARDS(36.0),         // 1 Yard = 3 Feet = 36 Inches
        FEET(12.0),          // 1 Foot = 12 Inches
        INCH(1.0),           // Base Unit
        CENTIMETERS(0.393701); // 1 Centimeter = 0.393701 Inches

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double convertToBase(double value) {
            return value * this.conversionFactor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // Convert both to the base unit (Inches) for cross-unit comparison
            double firstValue = this.unit.convertToBase(this.value);
            double secondValue = that.unit.convertToBase(that.value);

            // Use a small epsilon for floating-point comparison with Centimeters
            return Math.abs(firstValue - secondValue) < 1e-6;
        }
    }

    public static void main(String[] args) {
        // Yard to Feet comparison
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1.0 Yard == 3.0 Feet: " + oneYard.equals(threeFeet));

        // Yard to Inch comparison
        QuantityLength thirtySixInches = new QuantityLength(36.0, LengthUnit.INCH);
        System.out.println("1.0 Yard == 36.0 Inches: " + oneYard.equals(thirtySixInches));

        // Centimeter to Inch comparison
        QuantityLength oneCm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength targetInch = new QuantityLength(0.393701, LengthUnit.INCH);
        System.out.println("1.0 cm == 0.393701 inches: " + oneCm.equals(targetInch));
    }
}
