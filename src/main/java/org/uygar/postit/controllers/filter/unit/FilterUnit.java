package org.uygar.postit.controllers.filter.unit;

public abstract class FilterUnit {

    private final boolean enabled;

    public FilterUnit(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDisabled() {
        return !isEnabled();
    }

}