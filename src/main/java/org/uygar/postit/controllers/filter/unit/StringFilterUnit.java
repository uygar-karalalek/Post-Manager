package org.uygar.postit.controllers.filter.unit;

import java.util.Locale;
import java.util.function.BiPredicate;

public class StringFilterUnit extends FilterUnit {

    private final String input;
    private final boolean ignoreCase;
    private final BiPredicate<String, String> condition;

    public StringFilterUnit(String input, BiPredicate<String, String> condition, boolean ignoreCase, boolean enabled) {
        super(enabled);
        this.input = input;
        this.condition = condition;
        this.ignoreCase = ignoreCase;
    }

    public StringFilterUnit(String input, BiPredicate<String, String> condition, boolean ignoreCase) {
        super(true);
        this.input = input;
        this.ignoreCase = ignoreCase;
        this.condition = condition;
    }

    public String getInputBasedOnIgnoreCase() {
        return ignoreCase ? input.toLowerCase() : input;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public BiPredicate<String, String> getCondition() {
        return condition;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled() && input != null;
    }

}