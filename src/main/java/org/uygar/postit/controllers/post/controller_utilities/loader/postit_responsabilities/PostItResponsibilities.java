package org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities;

import org.uygar.postit.controllers.post.controller_utilities.loader.PostItCreator;

public class PostItResponsibilities {

    private final DimensionalBoundResponsibilities dimensionalBoundResponsibilities;
    private final LoadingResponsibilities loadingResponsibilities;
    private final ResponseResponsibilities responseResponsibilities;

    public PostItResponsibilities(PostItCreator postItCreator) {
        this.dimensionalBoundResponsibilities = new DimensionalBoundResponsibilities(postItCreator);
        this.loadingResponsibilities = new LoadingResponsibilities(postItCreator);
        this.responseResponsibilities = new ResponseResponsibilities(postItCreator);
    }

    public DimensionalBoundResponsibilities getDimensionalBoundResponsibilities() {
        return dimensionalBoundResponsibilities;
    }

    public LoadingResponsibilities getLoadingResponsibilities() {
        return loadingResponsibilities;
    }

    public ResponseResponsibilities getResponseResponsibilities() {
        return responseResponsibilities;
    }

}