/**
 * QuantityMeasurementApp - UC1: Feet measurement equality
 * * This class is responsible for checking the equality of two numerical values
 * measured in feet in the Quantity Measurement Application.
 * */

package com.apps.quantitymeasurement;

public class Main {

    // Inner class to represent Feet measurement
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        /**
         * Override equals() method to compare two Feet objects based on their value
         * * <p>Important Checks:</p>
         * 1. Reference Check: If both references point to the same object, return true
         * 2. Null Check: If the compared object is null, return false
         * 3. Type Check: If the compared object is not of type Feet, return false
         * 4. Value Comparison: Use Double.compare() to compare the double values for equality
         * * @param obj The object to compare with
         * @return true if both Feet objects have the same value, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Feet feet = (Feet) obj;
            return Double.compare(feet.value, this.value) == 0;
        }
    }

    // Main method to demonstrate Feet equality check
    public static void main(String[] args) {
        Feet firstFeet = new Feet(0.0);
        Feet secondFeet = new Feet(0.0);

        boolean isEqual = firstFeet.equals(secondFeet);
        System.out.println("Feet Equality Check (0.0 vs 0.0): " + isEqual);
    }
}