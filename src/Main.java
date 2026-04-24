/**
 * QuantityMeasurementApp - UC6: Addition of Two Length Units
 * Adds support for adding two quantities of different units, 
 * returning the result in the unit of the first operand.
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
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
            this.value = value;
            this.unit = unit;
        }

        /**
         * Instance Method: Adds another quantity to the current one.
         * The result unit matches the current instance's unit.
         */
        public QuantityLength add(QuantityLength other) {
            return add(this, other, this.unit);
        }

        /**
         * Static API Method: Adds two quantities and returns a new one in the target unit.
         */
        public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
            if (l1 == null || l2 == null) throw new IllegalArgumentException("Operands cannot be null.");
            
            // 1. Normalize both to base (Inches)
            double sumInBase = l1.unit.convertToBase(l1.value) + l2.unit.convertToBase(l2.value);
            
            // 2. Convert sum to target unit
            double finalValue = sumInBase / targetUnit.factor;
            
            return new QuantityLength(finalValue, targetUnit);
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

    public static void main(String[] args) {
        System.out.println("--- UC6: Quantity Arithmetic ---");

        // 1 Foot + 12 Inches = 2 Feet
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength result1 = oneFoot.add(twelveInches);
        System.out.println("1.0 FEET + 12.0 INCH = " + result1);

        // 12 Inches + 1 Foot = 24 Inches
        QuantityLength result2 = twelveInches.add(oneFoot);
        System.out.println("12.0 INCH + 1.0 FEET = " + result2);

        // 1 Yard + 3 Feet = 2 Yards (Logic: (36 + 36) / 36 = 2)
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength threeFeet = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("1.0 YARDS + 3.0 FEET = " + oneYard.add(threeFeet));
    }
}
