package org.uygar.postit.data.recoveries.post.recovery_folder.writer;

import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.recoveries.post.recovery_folder.RecoveryFolder;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Stream;

public class RecoveryWriter extends RecoveryFolder {

    private final Post post;
    private final PostItContainerOrganizer postIts;

    public RecoveryWriter(@NotNull String pathname, PostItContainerOrganizer postIts) throws IllegalArgumentException {
        super(pathname);
        createMainFolder();
        this.postIts = postIts;
        this.post = postIts.getFatherPost();
    }

    private void createMainFolder() {
        // folder name : recovery_<post-name>_<current-time-stamp>
        String pathname = "recovery_" + post.getName() + "_" + Instant.now().toString();

        File file = new File(pathname);

        try {
            file.mkdir();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void writePostFile() {
        try {
            File postFile = createPostFile();
            writePostToFile(postFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createPostFile() throws IOException {
        File postFile = new File(post.getName() + ".txt");
        postFile.createNewFile();
        return postFile;
    }

    private void writePostToFile(File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);

        writeNewLine(outputStream, post.getName());
        writeNewLine(outputStream, post.getSortType().toString());
        writeNewLine(outputStream, post.getCreationDate().toString());
        writeNewLine(outputStream, post.getLastModifiedDate().toString());
    }

    public void writePostItFiles() {
        Stream.iterate(0, cycle -> cycle + 1).forEachOrdered(currCycle ->
                writePostItToFile(postIts.getList().get(currCycle), currCycle));
    }

    private void writePostItToFile(PostIt postIt, int currCycle) {
        try {
            File postFile = createPostItFile(currCycle);
            writePostItToFile(postIt, postFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePostItToFile(PostIt postIt, File postItFile) throws IOException {
        FileOutputStream fio = new FileOutputStream(postItFile);
        writeNewLine(fio, postIt.getTitolo());
        writeNewLine(fio, postIt.getDataCreazione().toString());
        writeNewLine(fio, postIt.getTesto());
        writeNewLine(fio, postIt.getDataScadenza().toString());
        writeNewLine(fio, Integer.toString(postIt.getPriority()));
        writeNewLine(fio, Boolean.toString(postIt.isFatto()));
        writeNewLine(fio, postIt.getColore().toString());
    }

    private File createPostItFile(int fileNumber) throws IOException {
        File file = new File("postit_" + fileNumber);
        file.createNewFile();
        return file;
    }

    private void writeNewLine(FileOutputStream fileOutputStream, String data) throws IOException {
        fileOutputStream.write(data.getBytes());
        fileOutputStream.write(System.getProperty("line.separator").getBytes());
    }

    public Post getOldPost() {
        return post;
    }

    public PostItContainerOrganizer getOldPostIts() {
        return postIts;
    }

}