/**
 * QuantityMeasurementApp - UC3: Generic Quantity Class for DRY Principle
 * Refactors Feet and Inch classes into a single QuantityLength class.
 */

package com.apps.quantitymeasurement;

public class Main {

    /**
     * Enum to define Length Units and their conversion factors relative to a base (Inches).
     * Anchoring to Inches: 1 Foot = 12 Inches, 1 Inch = 1 Inch.
     */
    public enum LengthUnit {
        FEET(12.0), 
        INCH(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double convertToBase(double value) {
            return value * this.conversionFactor;
        }
    }

    /**
     * Generic class to represent any length measurement.
     */
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {
            // 1. Reference Check
            if (this == obj) return true;

            // 2. Null and Type Check
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength that = (QuantityLength) obj;

            // 3. Convert both to base unit (Inches) before comparing
            double firstValueInInches = this.unit.convertToBase(this.value);
            double secondValueInInches = that.unit.convertToBase(that.value);

            // 4. Value Comparison
            return Double.compare(firstValueInInches, secondValueInInches) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(unit.convertToBase(value));
        }
    }

    public static void main(String[] args) {
        // Test Case: Feet to Inches Equality
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCH);
        
        System.out.println("Input: 1.0 feet and 12.0 inches");
        System.out.println("Output: Equal (" + oneFoot.equals(twelveInches) + ")");

        // Test Case: Same Unit Equality
        QuantityLength oneInch = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength anotherInch = new QuantityLength(1.0, LengthUnit.INCH);
        
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + oneInch.equals(anotherInch) + ")");
    }
}
