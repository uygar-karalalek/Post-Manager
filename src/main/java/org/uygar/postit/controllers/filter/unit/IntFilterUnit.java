package org.uygar.postit.controllers.filter.unit;

import java.util.function.BiPredicate;

public class IntFilterUnit extends FilterUnit {

    private final int input;
    private final BiPredicate<Integer, Integer> condition;

    public IntFilterUnit(int input, BiPredicate<Integer, Integer> condition, boolean enabled) {
        super(enabled);
        this.input = input;
        this.condition = condition;
    }

    public int getInput() {
        return input;
    }

    public BiPredicate<Integer, Integer> getCondition() {
        return condition;
    }

    @Override
    public boolean isDisabled() {
        return super.isDisabled() && this.input == Integer.MIN_VALUE;
    }

}