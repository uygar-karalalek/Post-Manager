package org.uygar.postit.controllers.application.filter.util.predicates;

import org.uygar.postit.controllers.application.filter.util.FilterBuilder;
import org.uygar.postit.post.Post;

import java.util.function.BiPredicate;

public class PredicateReferencer {

    public String parameter;
    public boolean selected;
    public BiPredicate<String, String> stringOperation;
    public FilterBuilder builder;

    public PredicateReferencer(FilterBuilder builder, String name, boolean selected, BiPredicate<String, String> stringOperation) {
        this.builder = builder;
        this.selected = selected;
        this.stringOperation = stringOperation;
        this.parameter = transformFieldWithIgnoraMaiusc(name);
    }

    public boolean get(Post post) {
        if (selected) {
            String postName = transformFieldWithIgnoraMaiusc(post.getName());
            return stringOperation.test(postName, parameter);
        }
        return true;
    }

    public String transformFieldWithIgnoraMaiusc(String field) {
        return builder.ignoraMaiusc && selected ? field.toLowerCase() : field;
    }

}