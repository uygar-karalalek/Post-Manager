package org.uygar.postit.data.recoveries.post.recovery_folder.reader;

import org.jetbrains.annotations.NotNull;
import org.uygar.postit.data.recoveries.post.recovery_folder.RecoveryFolder;
import org.uygar.postit.post.Post;
import org.uygar.postit.post.PostIt;
import org.uygar.postit.post.properties.Colore;
import org.uygar.postit.post.properties.Sort;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecoveryReader extends RecoveryFolder {

    private File postFile;
    private final List<File> postItFiles = new ArrayList<>();

    private Post newPost;
    private final List<PostIt> newPostIts = new ArrayList<>();

    // The path name must follow this signature: recovery_<post-name>_<current-time-stamp>
    public RecoveryReader(@NotNull String pathname) throws IllegalArgumentException, IOException {
        super(pathname);
        readPostFile();
        readPostItFiles();
    }

    private void readPostFile() throws IOException {
        postFile = new File(this.getAbsolutePath() + "/post.txt");
        FileInputStream fio = new FileInputStream(postFile);
        this.readPostFileAndInit(fio);
    }

    private void readPostFileAndInit(FileInputStream fileInputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String postName = bufferedReader.readLine();
        Sort postSort = Sort.valueOf(bufferedReader.readLine());
        LocalDateTime postCreationDate = LocalDateTime.parse(bufferedReader.readLine());
        LocalDateTime lastModifiedDate = LocalDateTime.parse(bufferedReader.readLine());

        // Right order: id, creationDate, name, sort, lastModifiedDate
        this.newPost = new Post(-1, postName, postCreationDate, lastModifiedDate, postSort);

        bufferedReader.close();
    }

    private void readPostItFiles() {
        try {
            for (int i = 0; i < this.list().length - 1; i++) {
                File currPostItFile = new File(this.getAbsolutePath() + "/postit_" + i + ".txt");
                this.readPostItFileAndInit(currPostItFile);
                this.postItFiles.add(currPostItFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readPostItFileAndInit(File postItFile) throws IOException {
        FileInputStream fis = new FileInputStream(postItFile);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);

        String postItTitle = bufferedReader.readLine();
        LocalDateTime postItCreationDate = LocalDateTime.parse(bufferedReader.readLine());
        String postItText = bufferedReader.readLine();
        LocalDateTime postItEndDate = LocalDateTime.parse(bufferedReader.readLine());
        int postItPriority = Integer.parseInt(bufferedReader.readLine());
        boolean postItDone = Boolean.getBoolean(bufferedReader.readLine());
        Colore postItColore = Colore.valueOf(bufferedReader.readLine());

        newPostIts.add(new PostIt(-1, -1
                , postItDone
                , postItTitle
                , postItText
                , postItCreationDate, postItEndDate
                , postItColore
                , postItPriority));

        bufferedReader.close();
    }

    public static boolean exists(String path) {
        return new File(path + "/post.txt").exists();
    }

    public static boolean doesNotExist(String path) {
        return !exists(path);
    }

    public Post getNewPost() {
        return newPost;
    }

    public List<PostIt> getNewPostIts() {
        return newPostIts;
    }

}