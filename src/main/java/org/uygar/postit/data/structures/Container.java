package org.uygar.postit.data.structures;

import org.uygar.postit.post.properties.Sort;

public interface Container {

    void sort();
    void initStructure();
    Sort getSort();
    void setSort(Sort sort);

}