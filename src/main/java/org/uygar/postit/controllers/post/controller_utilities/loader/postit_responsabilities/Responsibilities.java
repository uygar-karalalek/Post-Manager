package org.uygar.postit.controllers.post.controller_utilities.loader.postit_responsabilities;

import org.uygar.postit.controllers.post.controller_utilities.loader.PostItCreator;

abstract class Responsibilities {

    protected PostItCreator postItCreator;

    public Responsibilities(PostItCreator postItCreator) {
        this.postItCreator = postItCreator;
    }

}