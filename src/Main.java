package com.apps.quantitymeasurement;

import java.util.Objects;

/**
 * WeightUnit handles the conversion factors and the 'math' of scaling.
 * QuantityWeight handles the 'logic' of equality and addition.
 */
enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    public double convertFromBaseUnit(double kgValue) {
        return kgValue / this.conversionFactor;
    }
}

public class QuantityWeight {
    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        this.value = value;
        this.unit = unit;
    }

    // UC9: Unit Conversion
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double kgValue = this.unit.convertToBaseUnit(this.value);
        return new QuantityWeight(targetUnit.convertFromBaseUnit(kgValue), targetUnit);
    }

    // UC9: Addition (Implicit & Explicit)
    public QuantityWeight add(QuantityWeight other) {
        return add(this, other, this.unit);
    }

    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2, WeightUnit target) {
        double sumInKg = w1.unit.convertToBaseUnit(w1.value) + w2.unit.convertToBaseUnit(w2.value);
        return new QuantityWeight(target.convertFromBaseUnit(sumInKg), target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Category Safety: Ensures we don't compare Weight to Length
        if (obj == null || getClass() != obj.getClass()) return false;
        
        QuantityWeight that = (QuantityWeight) obj;
        return Math.abs(this.unit.convertToBaseUnit(this.value) - 
                        that.unit.convertToBaseUnit(that.value)) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.unit.convertToBaseUnit(this.value));
    }

    @Override
    public String toString() {
        return String.format("%.3f %s", value, unit);
    }
}

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityWeightTest {

    private final double epsilon = 1e-6;

    // --- SECTION 1: Equality & Category Safety ---

    @Test
    public void testEquality_KilogramToGram_EquivalentValue() {
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight thousandGrams = new QuantityWeight(1000.0, WeightUnit.GRAM);
        
        assertEquals(oneKg, thousandGrams, "1.0 kg should equal 1000.0 g");
    }

    @Test
    public void testEquality_PoundToKilogram_ApproximateValue() {
        // 1 kg is roughly 2.20462 lbs
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight lbs = new QuantityWeight(2.20462, WeightUnit.POUND);
        
        assertTrue(oneKg.equals(lbs), "1 kg should equal ~2.20462 lbs within epsilon");
    }

    @Test
    public void testEquality_WeightVsLength_ShouldBeIncompatible() {
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        
        // This should return false due to the getClass() check in equals()
        assertNotEquals(oneKg, oneFoot, "Weights and Lengths should never be equal");
    }

    // --- SECTION 2: Conversion ---

    @Test
    public void testConversion_PoundToGram() {
        QuantityWeight oneLb = new QuantityWeight(1.0, WeightUnit.POUND);
        QuantityWeight result = oneLb.convertTo(WeightUnit.GRAM);
        
        // 1 lb = 453.592 grams
        assertEquals(453.592, result.getValue(), epsilon);
        assertEquals(WeightUnit.GRAM, result.getUnit());
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
        QuantityWeight original = new QuantityWeight(5.5, WeightUnit.KILOGRAM);
        QuantityWeight roundTrip = original.convertTo(WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);
        
        assertEquals(original.getValue(), roundTrip.getValue(), epsilon);
    }

    // --- SECTION 3: Addition ---

    @Test
    public void testAddition_KilogramAndGram_ResultInKg() {
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight fiveHundredGrams = new QuantityWeight(500.0, WeightUnit.GRAM);
        
        QuantityWeight sum = oneKg.add(fiveHundredGrams);
        
        assertEquals(1.5, sum.getValue(), epsilon);
        assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
    }

    @Test
    public void testAddition_ExplicitTargetUnit_ResultInPounds() {
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight oneKgOther = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        
        // Adding 1kg + 1kg and requesting result in Pounds
        QuantityWeight sum = QuantityWeight.add(oneKg, oneKgOther, WeightUnit.POUND);
        
        // 2kg = ~4.40924 lbs
        assertEquals(4.40924, sum.getValue(), epsilon);
        assertEquals(WeightUnit.POUND, sum.getUnit());
    }

    @Test
    public void testAddition_NegativeWeights() {
        QuantityWeight tenKg = new QuantityWeight(10.0, WeightUnit.KILOGRAM);
        QuantityWeight minusTwoKg = new QuantityWeight(-2.0, WeightUnit.KILOGRAM);
        
        QuantityWeight result = tenKg.add(minusTwoKg);
        
        assertEquals(8.0, result.getValue(), epsilon);
    }
}
