package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.text.SimpleDateFormat;
import java.util.Locale;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    private static final long serialVersionUID = 4205382376673471285L;
    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    public static final File Blobs = join(GITLET_DIR, "Blobs");
    /**
     * The message of this Commit.
     */
    private String message;

    /* TODO: fill in the rest of this class. */
    /**
     * The timestamp for this Commmit.
     */
    private String address;
    private String timestamp; // Something that keeps track of what files. This commit is tracking
    private String parent;
    private String branch;
    private String[] folderName;
    private String[] tags;

    public Commit() throws IOException {
        this.message = "initial commit"; // Record Commit Message
        this.parent = null; // No parent
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH); // Record generation time
        Date date = new Date();
        this.timestamp = format.format(date);
        this.address = Utils.sha1(timestamp); // hash of commit
        this.folderName = null;
        this.tags = null;
        this.branch = "master";
        File committing = join(GITLET_DIR, "committing_area", "master"); // Create Commit File
        committing.mkdir();
        File log = join(GITLET_DIR, "committing_area", "master", Utils.sha1(this.timestamp)); // Create Commit File
        committing.createNewFile();
        writeObject(log, this);
        File global = join(GITLET_DIR, "global_area", this.address); // Save the commit to the global folder
        global.createNewFile();
        writeObject(global, this);
        File commitMessage = join(GITLET_DIR, "message_area", this.address); // Folder for global log
        commitMessage.createNewFile();
        writeContents(commitMessage, this.message);
        File branch = join(GITLET_DIR, "branch_area", "master"); // Create the first branch 'master'
        writeContents(branch, this.address);
        File current = join(GITLET_DIR, "current_branch"); // record the current branch
        current.createNewFile();
        writeContents(current, "master");
        File HEAD = join(GITLET_DIR, "HEAD"); // HEAD is storing the top commit
        HEAD.createNewFile();
        writeObject(HEAD, this);
        Commit one = readObject(HEAD, Commit.class);
        File head = join(GITLET_DIR, "head_area", "master");
        head.createNewFile();
        writeObject(head, one);
    }

    public Commit(String message) throws IOException {
        this.message = message; // Record Commit Message
        File HEAD = join(GITLET_DIR, "HEAD"); // Associate with previous commit
        Commit past = readObject(HEAD, Commit.class);
        this.parent = past.address;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH); // Record generation time
        Date date = new Date();
        this.timestamp = format.format(date);
        this.address = Utils.sha1(this.timestamp); // hash of commit
        File area = join(GITLET_DIR, "staging_area"); // Loading Files to Save
        File[] folder_list = area.listFiles();
        String[] folder_name = area.list();
        String[] tags = new String[folder_list.length];
        for (int i = 0; i < folder_list.length; i++) { // Storing each files to the folder 'Blobs'
            date = new Date(); // Updating time
            tags[i] = Utils.sha1(format.format(date));
            File store = join(Blobs, tags[i]);
            store.mkdir();
            File newOne = join(store, folder_name[i]);
            writeContents(newOne, readContentsAsString(folder_list[i]));
        }
        this.folderName = folder_name;
        this.tags = tags;
        File current = join(GITLET_DIR, "current_branch");
        String branchAddress = readContentsAsString(current);
        this.branch = branchAddress;
        File committing = join(GITLET_DIR, "committing_area", this.branch, this.address); // Create Commit File
        committing.createNewFile();
        writeObject(committing, this);
        File global = join(GITLET_DIR, "global_area", this.address); // Save the commit to the global folder
        global.createNewFile();
        writeObject(global, this);
        writeObject(HEAD, this); // Point to Head
        File branch = join(GITLET_DIR, "branch_area");
        File[] branchContents = branch.listFiles();
        String[] branchName = branch.list();
        File commitMessage = join(GITLET_DIR, "message_area", this.address); // Folder for global log
        commitMessage.createNewFile();
        writeContents(commitMessage, message);
        commitMessage.mkdir();
        for (int i = 0; i < branchContents.length; i++) {
            if (branchName[i].equals(branchAddress)) {
                writeContents(branchContents[i], this.address);
                writeContents(current, branchName[i]);
            }
        }
        File head = join(GITLET_DIR, "head_area", branchAddress);
        writeObject(head, this);
    }

    public String getMessage() {
        return this.message;
    }

    public String getParent() {
        return this.parent;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getAddress() {
        return this.address;
    }

    public String[] getFolderName() {
        return this.folderName;
    }

    public String[] getTags() {
        return this.tags;
    }
    public String getBranch() {
        return this.branch;
    }
}