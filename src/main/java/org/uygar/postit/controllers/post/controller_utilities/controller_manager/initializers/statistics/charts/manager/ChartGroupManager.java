package org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.manager;

import org.uygar.postit.controllers.post.PostController;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.StatisticsChunk;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.StatisticsChunkPane;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.color.ColoreChunk;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.creation_date.CreationDateChunk;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.priority.PriorityChunk;
import org.uygar.postit.controllers.post.controller_utilities.controller_manager.initializers.statistics.charts.chunks.state.StateChunk;

import java.util.ArrayList;
import java.util.List;

public class ChartGroupManager {

    private final PostController postController;
    private final List<StatisticsChunkPane> chunks;

    public ChartGroupManager(PostController postController) {
        this.chunks = new ArrayList<>();
        this.postController = postController;
        this.addChunksToManagerGroupIfAlmostOnePostIt();
    }

    public void addChunksToManagerGroupIfAlmostOnePostIt() {
        if (this.thereIsAlmostOnePostIt()) {
            this.chunks.add(new StateChunk(postController.statsPane, postController.postItGrid.getPostItOrganizer()));
            this.chunks.add(new CreationDateChunk(postController.statsPane, postController.postItGrid.getPostItOrganizer()));
            this.chunks.add(new PriorityChunk(postController.statsPane, postController.postItGrid.getPostItOrganizer()));
            this.chunks.add(new ColoreChunk(postController.statsPane, postController.postItGrid.getPostItOrganizer()));
        }
    }

    public void updateAllCharts() {
        this.chunks.forEach(StatisticsChunk::updateCharts);
    }

    public List<StatisticsChunkPane> getChunks() {
        return chunks;
    }

    public boolean thereIsAlmostOnePostIt() {
        return postController.postItGrid.getPostItOrganizer().getList().size() >= 1;
    }

    public boolean thereAreZeroPostIts() {
        return !thereIsAlmostOnePostIt();
    }

    public boolean isEmpty() {
        return this.chunks.isEmpty();
    }

}