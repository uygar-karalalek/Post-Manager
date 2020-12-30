package org.uygar.postit.controllers.filter.postit;

import org.uygar.postit.controllers.filter.Filter;
import org.uygar.postit.controllers.filter.GeneralFilter;
import org.uygar.postit.controllers.filter.unit.DateFilterUnit;
import org.uygar.postit.controllers.filter.unit.StringFilterUnit;
import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.post.PostIt;

import java.util.function.Predicate;

public class PostItFilter extends GeneralFilter<PostController, PostIt> {

    @Override
    public void buildFilterSettingUnits() {

    }

    @Override
    public void applyFilterToController() {

    }

    @Override
    public void addDateFilterUnit(DateFilterUnit dateFilterUnit) {

    }

    @Override
    public void addStringFilterUnit(StringFilterUnit stringFilterUnit) {

    }

    @Override
    public void serialize() {

    }

    @Override
    public Filter<PostIt> deserialize() {
        return null;
    }

    @Override
    public Predicate<PostIt> getResult() {
        return null;
    }

}