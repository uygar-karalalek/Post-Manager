package org.uygar.postit.controllers.post.postit;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.uygar.postit.controllers.BaseController;
import org.uygar.postit.controllers.ControllerType;
import org.uygar.postit.controllers.exception.WindowCoordinatesContainer;
import org.uygar.postit.controllers.exception.WrongFieldsException;
import org.uygar.postit.controllers.post.postit.editor_manager.PostItEditorManager;
import org.uygar.postit.data.database.DataMiner;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.viewers.post_it.PostItGridViewer;

import java.util.Optional;
import java.util.function.Predicate;

import static org.uygar.postit.data.query_utils.QueryUtils.*;

public class PostItController extends BaseController {

    @FXML
    public BorderPane postIt;
    @FXML
    public Rectangle postItRectangle;
    @FXML
    public DatePicker dataField;
    @FXML
    public TextField titoloField, oraField, minutoField;
    @FXML
    public Spinner<Integer> prioritySpinner;
    @FXML
    public TextArea compitoField;
    @FXML
    public Button annullaBtn, salvaBtn, rimuoviBtn;
    @FXML
    public HBox coloriTopHBox;
    @FXML
    public VBox propertyBox;

    public final DataMiner miner = new DataMiner();
    public PostItEditorManager postItEditorManager;
    public PostIt loadedPostIt;
    public PostItGridViewer postItGrid;
    public boolean modifying;

    public void init(Optional<PostIt> loadingPostIt, PostItGridViewer containerOrganizer) {
        this.loadedPostIt = loadingPostIt.orElse(null);
        this.postItGrid = containerOrganizer;
        this.modifying = loadingPostIt.isPresent();

        this.postItEditorManager = new PostItEditorManager(this);
        this.postItEditorManager.initialize();
    }

    @FXML
    public void onSalva() throws WrongFieldsException {
        Optional<PostIt> postItFromController = modifying ?
                PostItUtils.getPostItFromControllerWhenModifying(this)
                : PostItUtils.getPostItFromControllerWhenCreating(this);

        Predicate<PostIt> trySavePostIt = postItEditorManager::trySavePostIt;

        if (postItFromController.isPresent() && trySavePostIt.test(postItFromController.get())) {
            postItEditorManager.closePostItEditor();
        } else {
            throw new WrongFieldsException("C'è stato un errore! Inserisci i campi giusti!",
                    new WindowCoordinatesContainer(this.postIt.getScene().getWindow()));
        }
    }

    @FXML
    public void onAnnulla() {
        postItEditorManager.closePostItEditor();
    }

    @FXML
    public void onRimuovi() {
        tryRemovePostItFromDB(miner, loadedPostIt);
        postItGrid.remove(loadedPostIt);
        postItEditorManager.closePostItEditor();
    }

}