/**
 * QuantityMeasurementApp - UC2: Feet and Inches measurement equality
 * This class demonstrates separate equality checks for Feet and Inches.
 */

package com.apps.quantitymeasurement;

public class Main {

    // Inner class to represent Feet measurement
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, this.value) == 0;
        }
    }

    // Inner class to represent Inch measurement
    public static class Inch {
        private final double value;

        public Inch(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inch inch = (Inch) obj;
            return Double.compare(inch.value, this.value) == 0;
        }
    }

    /**
     * Static method to validate equality of two Feet measurements
     */
    public static boolean checkFeetEquality(double val1, double val2) {
        Feet f1 = new Feet(val1);
        Feet f2 = new Feet(val2);
        return f1.equals(f2);
    }

    /**
     * Static method to validate equality of two Inch measurements
     */
    public static boolean checkInchEquality(double val1, double val2) {
        Inch i1 = new Inch(val1);
        Inch i2 = new Inch(val2);
        return i1.equals(i2);
    }

    public static void main(String[] args) {
        // Main Flow for Inches
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + checkInchEquality(1.0, 1.0) + ")");

        // Main Flow for Feet
        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + checkFeetEquality(1.0, 1.0) + ")");
        
        // Example of Inequality
        System.out.println("Input: 1.0 inch and 2.0 inch");
        System.out.println("Output: Equal (" + checkInchEquality(1.0, 2.0) + ")");
    }
}
