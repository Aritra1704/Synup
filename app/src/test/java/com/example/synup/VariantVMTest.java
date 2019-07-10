package com.example.synup;

import com.example.synup.models.ExcludeItems;
import com.example.synup.models.Variations;
import com.example.synup.viewmodel.VariantVM;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariantVMTest {

    VariantVM variant = new VariantVM();

    @Test
    public void getFormattedVariantTest() {

        String actual = variant.getFormattedVariant(1, 11);
        String expected = "G1V11";
        assertEquals(expected, actual);
    }

    @Test
    public void calculatePriceTest() {
        HashMap<String, Variations> hash = new HashMap<>();
        Variations variation = new Variations();
        variation.setPrice(100);
        hash.put("Test1", variation);

        variation = new Variations();
        variation.setPrice(11);
        hash.put("Test2", variation);

        variation = new Variations();
        variation.setPrice(30);
        hash.put("Test3", variation);
        float actual = variant.calculatePrice(hash);

        assertEquals(141, actual, 1);
    }

    @Test
    public void areAllTrueTest() {
        boolean[] test = {true, false, true};
        boolean actual = variant.areAllTrue(test);
        assertEquals(false, actual);
    }

    @Test
    public void variantsToStringListTest() {
        ArrayList<Variations> actual = new ArrayList<>();
        Variations variations = new Variations();
        variations.setName("Test1");
        actual.add(variations);

        variations = new Variations();
        variations.setName("Test2");
        actual.add(variations);

        variations = new Variations();
        variations.setName("Test3");
        actual.add(variations);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("Test1");
        expected.add("Test2");
        expected.add("Test3");

        assertEquals(expected, variant.variantsToStringList(actual));
    }

    @Test
    public void isExclusionTest() {
        ArrayList<String> selectedCombination = new ArrayList<>();
        ArrayList<ArrayList<ExcludeItems>> listExclude = new ArrayList<>();

        selectedCombination.add("G1V2");
        selectedCombination.add("G2V10");
        selectedCombination.add("G3V22");

        ArrayList<ExcludeItems> exclude = new ArrayList<>();
        ExcludeItems excl = new ExcludeItems();
        excl.setGroup_id("1");
        excl.setVariation_id("3");
        exclude.add(excl);

        excl = new ExcludeItems();
        excl.setGroup_id("2");
        excl.setVariation_id("10");
        exclude.add(excl);

        listExclude.add(exclude);

        exclude = new ArrayList<>();
        excl = new ExcludeItems();
        excl.setGroup_id("2");
        excl.setVariation_id("10");
        exclude.add(excl);

        excl = new ExcludeItems();
        excl.setGroup_id("3");
        excl.setVariation_id("22");
        exclude.add(excl);

        listExclude.add(exclude);

        assertEquals(true, variant.isExclusion(selectedCombination, listExclude));
    }
}
