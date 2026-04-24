package com.apps.quantitymeasurement;

public class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value.");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double feetValue = this.unit.convertToBaseUnit(this.value);
        double targetValue = targetUnit.convertFromBaseUnit(feetValue);
        return new QuantityLength(targetValue, targetUnit);
    }

    public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
        // Delegate conversion responsibility to the units themselves
        double sumInFeet = l1.unit.convertToBaseUnit(l1.value) + 
                           l2.unit.convertToBaseUnit(l2.value);
        
        double finalValue = targetUnit.convertFromBaseUnit(sumInFeet);
        return new QuantityLength(finalValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength that = (QuantityLength) obj;
        
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        that.unit.convertToBaseUnit(that.value)) < 1e-6;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}
