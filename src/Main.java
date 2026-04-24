/**
 * QuantityMeasurementApp - UC7: Addition with Target Unit Specification
 * Demonstrates Method Overloading and explicit control over result units.
 */

package com.apps.quantitymeasurement;

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
         * UC6: Implicit addition (Result in unit of 'this')
         */
        public QuantityLength add(QuantityLength other) {
            return add(this, other, this.unit);
        }

        /**
         * UC7: Explicit addition (Result in specified targetUnit)
         * Overloaded to allow caller control.
         */
        public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
            if (l1 == null || l2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Operands and target unit cannot be null.");
            }
            return performAddition(l1, l2, targetUnit);
        }

        /**
         * Private Utility Method: The engine for all addition logic.
         * Centralizing this ensures consistent rounding and precision.
         */
        private static QuantityLength performAddition(QuantityLength l1, QuantityLength l2, LengthUnit target) {
            // 1. Normalize to base
            double sumInBase = l1.unit.convertToBase(l1.value) + l2.unit.convertToBase(l2.value);
            // 2. Scale to target
            double resultValue = sumInBase / target.factor;
            // 3. Return new immutable instance
            return new QuantityLength(resultValue, target);
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
            return String.format("%.3f %s", value, unit);
        }
    }

    public static void main(String[] args) {
        System.out.println("--- UC7: Explicit Target Unit Addition ---");

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET
