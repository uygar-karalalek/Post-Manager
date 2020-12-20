package org.uygar.postit.post.properties;

import org.uygar.postit.post.PostIt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Predicate;

public enum Sort {

    DONE("Finite") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(PostIt::compareToDone);
        }
    },

    UNDONE("Non finite") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(PostIt::compareToUndone);
        }
    },

    DATE("Data") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(Comparator.comparing(PostIt::getId));
        }
    },

    DATE_AND_DONE("Data e Finite") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(Comparator.comparing(
                    PostIt::isFatto).thenComparing(PostIt::getId));
        }
    },

    DATE_AND_UNDONE("Data e non finite") {
        @Override
        public void sort(List<PostIt> postItList) {
            Function<PostIt, Boolean> notDone = postIt -> !postIt.isFatto();
            postItList.sort(Comparator.comparing(PostIt::getDataCreazione).thenComparing(notDone));
        }
    };

    String name;

    Sort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void sort(List<PostIt> postItList);

    public static Sort getSortBasedOnName(String name) {
        for (Sort element : values())
            if (element.name.equals(name))
                return element;
        return null;
    }

}