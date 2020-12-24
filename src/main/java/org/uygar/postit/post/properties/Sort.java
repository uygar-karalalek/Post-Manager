package org.uygar.postit.post.properties;

import org.uygar.postit.post.PostIt;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public enum Sort {

    DONE("Finito") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_DONE);
        }
    },

    PRIORITY("Priorità") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_PRIORITY);
        }
    },

    UNDONE("Non finito") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_DONE.reversed());
        }
    },

    DATE("Cronologico") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_ID);
        }
    },

    DATE_AND_UNDONE("Non finito e data") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_DONE.reversed().thenComparing(BY_DATE));
        }
    },

    PRIORITY_AND_UNDONE("Non finito e Priorità") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_DONE.reversed().thenComparing(BY_PRIORITY));
        }
    },

    DATE_AND_DONE("Finito e Cronologico") {
        @Override
        public void sort(List<PostIt> postItList) {
            postItList.sort(BY_DONE.thenComparing(BY_ID));
        }
    };

    String name;

    static Comparator<PostIt> BY_DONE = Comparator.comparing(postIt -> !postIt.isFatto());
    static Comparator<PostIt> BY_ID = Comparator.comparing(PostIt::getId).reversed();
    static Comparator<PostIt> BY_DATE = Comparator.comparing(PostIt::getDataCreazione);
    static Comparator<PostIt> BY_PRIORITY = Comparator.comparing(PostIt::getPriority).reversed();

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