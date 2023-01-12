package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */

    /**
     * TODO: somehow make the initial commit
     * TODO: somehow create the .gitlet directory
     * TODO: create all the rest of the things in the .gitlet that we need
     */
    public Commit HEAD;
    public static void initCommand() throws IOException {
        GITLET_DIR.mkdir();
        File Blobs = join(GITLET_DIR, "Blobs"); // Folder for storing all files
        Blobs.mkdir();
        File area = join(GITLET_DIR, "staging_area"); // Folder for Staging
        area.mkdir();
        File removalArea = join(GITLET_DIR, "removing_area"); // Folder for Files that need to be removed
        removalArea.mkdir();
        File commitArea = join(GITLET_DIR, "committing_area"); // Folder for Commit list
        commitArea.mkdir();
        File branch = join(GITLET_DIR, "branch_area"); // Folder for Branch list
        branch.mkdir();
        File global = join(GITLET_DIR, "global_area"); // Folder for global log
        global.mkdir();
        File message = join(GITLET_DIR, "message_area"); // Folder for global log
        message.mkdir();
        File head = join(GITLET_DIR, "head_area"); // Folder for global log
        head.mkdir();
        Commit initialCommit = new Commit(); // Create First Commit
    }
    public static void addCommand(String name) throws IOException {
        File area = join(GITLET_DIR, "staging_area");
        File infile = join(CWD, name);
        File outfile = join(area, name);
        outfile.createNewFile();
        String contents = Utils.readContentsAsString(infile);
        writeContents(outfile, contents);
    }
    public static void addforRemoval(String name) throws IOException {
        File removalArea = join(GITLET_DIR, "removing_area");
        File infile = join(CWD, name);
        File outfile = join(removalArea, name);
        outfile.createNewFile();
        String contents = Utils.readContentsAsString(infile);
        writeContents(outfile, contents);
    }
}
// Add is simply the role of importing external files and then storing them in the staging area.
// Commit creates a tag for a file(object) stored in the staging area using the sha1 method,
// adds a separate object stored with the file to a git hub-only store called blob,
// and creates a new commit with the tag and connects it to an existing commit.