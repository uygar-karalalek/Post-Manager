package org.uygar.postit.post.viewers.post_it.post_it_viewer.builder;

import org.uygar.postit.post.viewers.post_it.post_it_viewer.PostItViewer;

public class PostItViewerBuilder {

    private PostItViewer viewer;
    private PostItMouseInteractionManager interactionManager;

    public PostItViewerBuilder(PostItViewer postItViewer) {
        this.interactionManager = new PostItMouseInteractionManager(postItViewer);
        this.viewer = postItViewer;
        build();
    }

    public void build() {
        interactionManager.manage();
    }

    public PostItMouseInteractionManager getInteractionManager() {
        return interactionManager;
    }

}