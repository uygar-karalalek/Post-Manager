package org.uygar.postit.data.recoveries.post.recovery_folder.writer;

import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.recoveries.post.recovery_folder.RecoveryFolder;
import org.uygar.postit.data.structures.PostItContainerOrganizer;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Stream;

public class RecoveryWriter extends RecoveryFolder {

    private final Post post;
    private final PostItContainerOrganizer postIts;
    private String recoveryDir;

    public RecoveryWriter(@NotNull String absPath, PostItContainerOrganizer postIts) throws IllegalArgumentException {
        super(absPath);
        this.postIts = postIts;
        this.post = postIts.getFatherPost();
        createMainFolder();
    }

    private void createMainFolder() {
        // folder name : recovery_<post-name>_<current-time-stamp>
        String recoverySubDir = "recovery_" + post.getName() + "_" + Timestamp.from(Instant.now()).getTime();
        recoveryDir = fromParentPath(recoverySubDir);

        File file = new File(recoveryDir);

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
        File postFile = new File(fromParentPath(recoveryDir, "post.txt"));
        postFile.createNewFile();
        return postFile;
    }

    private void writePostToFile(File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);

        writeNewLine(outputStream, post.getName());
        writeNewLine(outputStream, post.getSortType().toString());
        writeNewLine(outputStream, post.getCreationDate().toString());
        writeNewLine(outputStream, post.getLastModifiedDate().toString());

        outputStream.flush();
        outputStream.close();
    }

    public void writePostItFiles() {
        Stream.iterate(0, cycle -> cycle + 1).limit(postIts.getList().size()).forEachOrdered(currCycle ->
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
        fio.flush();
        fio.close();
    }

    private File createPostItFile(int fileNumber) throws IOException {
        File file = new File(fromParentPath(recoveryDir, "postit_" + fileNumber + ".txt"));
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