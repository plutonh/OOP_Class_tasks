package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

public class SomeObj {
    public static void init() throws IOException {
        // Get the current working directory
        // Branches? Here we need to initialize a master branch and have it point to initial commit
        // What is UID?
        if(GITLET_DIR.exists()) {
            System.out.print("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        initCommand();
    }
    public static void add(String name) throws IOException {
        if(!(join(Repository.CWD, name).exists())) {
            System.out.print("File does not exist.");
            return;
        }
        addCommand(name);
    }
    public static void commit(String message) throws IOException {
        if(message.isEmpty()) {
            System.out.print("Please enter a commit message.");
            return;
        }
        if(join(GITLET_DIR, "staging_area").listFiles().length == 0) {
            System.out.print("No changes added to the commit.");
            return;
        }
        Commit one = new Commit(message);
        File HEAD = join(GITLET_DIR, "HEAD");
        File[] HEAD_list = HEAD.listFiles();
        if(HEAD_list != null) {
            for(int i = 0; i < HEAD_list.length; i++) {
                HEAD_list[i].delete();
            }
        }
        writeObject(HEAD, one);
        File area = join(GITLET_DIR, "staging_area");
        File[] folder_list = area.listFiles();
        for (int i = 0; i < folder_list.length; i++) {
            folder_list[i].delete();
        }
    }
    public static void rm(String name) throws IOException {
        File inFile = join(GITLET_DIR, "staging_area");// Finding file in the Staging Area
        File[] stageFile = inFile.listFiles();
        String[] stageName = inFile.list();
        File workFile = CWD; // Finding file in the working directory
        File[] workList = workFile.listFiles();
        String[] workName = workFile.list();
        int stageChange = 0;
        int commitChange = 0;
        for(int i = 0; i < stageFile.length; i++) { // staging area
            if(name.equals(stageName[i])) {
                stageFile[i].delete();
                stageChange = 1;
            }
        }
        for(int k = 0; k < workName.length; k++) {
            if(workName[k].equals(name)) {
                Repository.addforRemoval(workName[k]);
                workList[k].delete();
                commitChange = 1;
            }
        }
        if((stageChange == 0) && (commitChange == 0)) {
            System.out.print("No reason to remove the file.");
        }
    }
    public static void log() {
        //File HEAD = join(GITLET_DIR, "HEAD");
        File currentBranch = join(GITLET_DIR, "current_branch");
        File commitArea = join(GITLET_DIR, "committing_area", readContentsAsString(currentBranch));
        File[] commitFile = commitArea.listFiles();
        //String[] commitName = commitArea.list();
        Commit logging = null;
        //Commit logging = readObject(HEAD, Commit.class);
        for(int i = commitFile.length - 1; i >= 0; i--) {
            logging = readObject(commitFile[i], Commit.class);
            System.out.println("===");
            System.out.println("commit " + logging.getAddress());
            System.out.println("Date: " + logging.getTimestamp());
            System.out.println(logging.getMessage());
            if(i > 0) System.out.println();

//            if(logging.getMessage().equals("initial commit")) break;
//            else System.out.println();
        }
//        while(true) {
//            System.out.println("===");
//            System.out.println("commit " + logging.getAddress());
//            System.out.println("Date: " + logging.getTimestamp());
//            System.out.println(logging.getMessage());
//            if(logging.getMessage().equals("initial commit")) break;
//            else System.out.println();
//            for(int j = 0; j < commitName.length; j++) {
//                if(commitName[j].equals(logging.getParent())) {
//                    logging = readObject(commitFile[j], Commit.class);
//                    break;
//                }
//            }
//        }
    }
    public static void status() {
        File inFile = join(GITLET_DIR, "staging_area");
        String[] stageName = inFile.list();
        File removalArea = join(GITLET_DIR, "removing_area");
        String[] removeName = removalArea.list();
        File branch = join(GITLET_DIR, "branch_area");
        String[] branchName = branch.list();
        File check = join(GITLET_DIR, "current_branch");
        String compare = readContentsAsString(check);
        System.out.println("=== Branches ===");
        for(int i = 0; i < branchName.length; i++) {
            if(branchName[i].equals(compare)) System.out.println("*" + branchName[i]);
        }
        for(int i = 0; i < branchName.length; i++) {
            if(!(branchName[i].equals(compare))) System.out.println(branchName[i]);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for(int j = 0; j < stageName.length; j++) {
            System.out.println(stageName[j]);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for(int k = 0; k < removeName.length; k++) {
            System.out.println(removeName[k]);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
    }
    public static void checkout1(String two, String three) throws IOException{ // --, file name
        File workingDirectory = CWD;
        File[] workingList = workingDirectory.listFiles();
        String[] workingName = workingDirectory.list();
        File Blobs = join(GITLET_DIR, "Blobs"); // Space for accessing files
        File[] BlobsList = Blobs.listFiles();
        String[] BlobsName = Blobs.list();
        String buffer = null; // Instances for storing file contents
        File HEAD = join(GITLET_DIR, "HEAD"); // Loading Files Stored in Head Commit
        Commit one = readObject(HEAD, Commit.class);
        String[] tags = one.getTags();
        String[] fileList = one.getFolderName();
        int check = 0; // Check if a file exists in the commit
        for(int i = 0; i < tags.length; i++) if(three.equals(fileList[i])) check = 1;
        if(check != 1) {
            System.out.printf("File does not exist in that commit.");
            return;
        }
        for(int j = 0; j < BlobsList.length; j++) { // Import a list of files in the HEAD commit from the Blobs folder
            File[] BlobsFile = BlobsList[j].listFiles(); // Taking file
            String fileContents = readContentsAsString(BlobsFile[0]); // Taking the file contents
            if(one.getAddress().equals(BlobsName[j])) buffer = fileContents;
        }
        int exist = 0; // Reflect as a checked out file
        for(int i = 0; i < workingList.length; i++) {
            if(workingName[i].equals(three)) {
                writeContents(workingList[i], buffer);
                exist = 1;
            }
        }
        if(exist != 1) {
            File newOne = join(CWD, three);
            newOne.createNewFile();
            writeContents(newOne, buffer);
        }
    }
    public static void checkout2(String two, String three, String four) throws IOException { // commit id, --, file name
        File workingDirectory = CWD;
        File[] workingList = workingDirectory.listFiles();
        String[] workingName = workingDirectory.list();
        File Blobs = join(GITLET_DIR, "Blobs"); // Space for accessing files
        File[] BlobsList = Blobs.listFiles();
        String[] BlobsName = Blobs.list();
        String buffer = null; // Variables for storing file contents
        File target = join(GITLET_DIR, "committing_area"); // Loading Files Stored in target Commit
        File[] targetList = target.listFiles();
        String[] targetName = target.list();
        Commit one = null; // Instances to store commits that match id
        int checkCommit = 0; // Check if a commit exists in the committing_area
        for(int k = 0; k < targetName.length; k++) {
            if(targetName[k].equals(two)) {
                one = readObject(targetList[k], Commit.class);
                checkCommit = 1;
                break;
            }
        }
        if(checkCommit != 1) {
            System.out.print("No commit with that id exists.");
            return;
        }
        String[] tags = one.getTags();
        String[] fileList = one.getFolderName();
        int checkFile = 0; // Check if a file exists in the commit
        for(int i = 0; i < tags.length; i++) if(four.equals(fileList[i])) checkFile = 1;
        if(checkFile != 1) {
            System.out.print("File does not exist in that commit.");
            return;
        }
        for(int j = 0; j < BlobsList.length; j++) { // Import a list of files in the HEAD commit from the Blobs folder
            File[] BlobsFile = BlobsList[j].listFiles(); // Taking file
            String fileContents = readContentsAsString(BlobsFile[0]); // Taking the file contents
            if(one.getAddress().equals(BlobsName[j])) buffer = fileContents;
        }
        int exist = 0; // Reflect as a checked out file
        for(int i = 0; i < workingList.length; i++) {
            if(workingName[i].equals(four)) {
                writeContents(workingList[i], buffer);
                exist = 1;
            }
        }
        if(exist != 1) {
            File newOne = join(CWD, four);
            newOne.createNewFile();
            writeContents(newOne, buffer);
        }
    }
    public static void checkout3(String two) {
        File workingDirectory = CWD;
        File[] workingList = workingDirectory.listFiles();
        String[] workingName = workingDirectory.list();
        File Blobs = join(GITLET_DIR, "Blobs"); // Space for accessing files
        File[] BlobsList = Blobs.listFiles();
        String[] BlobsName = Blobs.list();
        File branch = join(GITLET_DIR, "branch_area"); // Find the contents of the current branch
        File[] branchContents = branch.listFiles();
        String[] branchName = branch.list();
        File current = join(GITLET_DIR, "current_branch");
        String currentName = readContentsAsString(current);
        File futureBranch = null;
        File fileList = null;
        File branchList = null;
        String buffer = null; // Variables for storing file contents
        if(two.equals(currentName)) {
            System.out.print("No need to checkout the current branch.");
            return;
        }
        int change = 0;
        for(int i = 0; i < branchContents.length; i++) {
            if(two.equals(branchName[i])) {
                futureBranch = branchContents[i];
                change = 1;
            }
        }
        if(change != 1) {
            System.out.print("No such branch exists.");
            return;
        }
        for(int i = 0; i < branchContents.length; i++) {
            if(currentName.equals(branchName[i])) branchList = branchContents[i];
        }
        for(int j = 0; j < BlobsList.length; j++) { // Import a list of files in the HEAD commit from the Blobs folder
            if (readContentsAsString(branchList).equals(BlobsName[j])) fileList = BlobsList[j];
        }
        File[] list = fileList.listFiles();
        String[] name = fileList.list();
        String contents1 = null;
        String contents2 = null;
        int count = 0;
        for(int k = 0; k < workingName.length; k++) {
            for(int i = 0; i < name.length; i++) {
                contents1 = readContentsAsString(list[i]);
                if(name[i].equals(workingName[k])) {
                    contents2 = readContentsAsString(workingList[k]);
                    if(!(contents1.equals(contents2))) count ++;
                }
            }
        }
        if(count > 0) {
            System.out.print("There is an untracked file in the way; delete it, or add and commit it first.");
            return;
        }
        String futureCommit = readContentsAsString(futureBranch);
        for(int j = 0; j < BlobsList.length; j++) { // Import a list of files in the HEAD commit from the Blobs folder
            if (futureCommit.equals(BlobsName[j])) fileList = BlobsList[j];
        }
        list = fileList.listFiles();
        name = fileList.list();
        count = 0;
        for(int k = 0; k < workingName.length; k++) {
            for(int i = 0; i < name.length; i++) {
                contents1 = readContentsAsString(list[i]);
                if(name[i].equals(workingName[k])) {
                    contents2 = readContentsAsString(workingList[k]);
                    if(!(contents1.equals(contents2))) count ++;
                }
            }
        }
        writeContents(current, two);
        File HEAD = join(GITLET_DIR, "HEAD"); // Changing HEAD
        File head = join(GITLET_DIR, "head_area", two);
        Commit one = readObject(head, Commit.class);
        writeObject(HEAD, one);
    }
    public static void globalLog() {
        File globalArea = join(GITLET_DIR, "global_area");
        File[] globalFile = globalArea.listFiles();
        Commit logging = null;
        for(int i = 0; i < globalFile.length; i++) {
            logging = readObject(globalFile[i], Commit.class);
            System.out.println("===");
            System.out.println("commit " + logging.getAddress());
            System.out.println("Date: " + logging.getTimestamp());
            System.out.println(logging.getMessage());
            if(i < globalFile.length - 1) System.out.println();
        }
    }
    public static void find(String message) {
        File messageArea = join(GITLET_DIR, "message_area"); // Folder for global log
        File[] messageFile = messageArea.listFiles();
        String[] messageName = messageArea.list(); // Name of commit
        String contents = null;
        int exist = 0;
        for(int i = 0; i < messageFile.length - 1; i++) {
            contents = readContentsAsString(messageFile[i]);
            if(contents.equals(message)) {
                System.out.print(messageName[i]);
                exist = 1;
            }
        }
        contents = readContentsAsString(messageFile[messageFile.length - 1]);
        if(contents.equals(message)) {
            System.out.print(messageName[messageFile.length - 1]);
            exist = 1;
        }
        if(exist != 1) System.out.print("Found no commit with that message.");
    }
    public static void branch(String name) throws IOException {
        File branch = join(GITLET_DIR, "branch_area"); // Find the contents of the current branch
        File[] branchContents = branch.listFiles();
        String[] branchName = branch.list();
        File current = join(GITLET_DIR, "current_branch");
        String currentName = readContentsAsString(current);
        String address = null;
        for(int i = 0; i < branchName.length; i++) {
            if(branchName[i].equals(currentName)) address = readContentsAsString(branchContents[i]);
        }
        for(int i = 0; i < branchName.length; i++) {
            if(branchName[i].equals(name)) {
                System.out.print("A branch with that name already exists.");
                return;
            }
        }
        File committing = join(GITLET_DIR, "committing_area");
        File[] committingList = committing.listFiles();
        String[] committingName = committing.list();
        File buffer = null;
        File[] bufferList = null;
        String[] bufferName = null;
        Commit one = null;
        for(int j = 0; j < committingList.length; j++) {
            if(currentName.equals(committingName[j])) {
                buffer = committingList[j];
                bufferList = buffer.listFiles();
                bufferName = buffer.list();
            }
        }
        File newBranch = join(GITLET_DIR, "committing_area", name); // Creating commiting area for new branch
        newBranch.mkdir();
        for(int k = 0; k < bufferList.length; k++) {
            File newFile = join(GITLET_DIR, "committing_area", name, bufferName[k]);
            one = readObject(bufferList[k], Commit.class);
            newFile.createNewFile();
            writeObject(newFile, one);
        }
        File newOne = join(GITLET_DIR, "branch_area", name);
        newOne.createNewFile();
        writeContents(newOne, address);
        File HEAD = join(GITLET_DIR, "HEAD");
        one = readObject(HEAD, Commit.class);
        File head = join(GITLET_DIR, "head_area", name);
        head.createNewFile();
        writeObject(head, one);
    }
    public static void rmBranch(String name) {
        File branch = join(GITLET_DIR, "branch_area"); // Find the contents of the current branch
        File[] branchContents = branch.listFiles();
        String[] branchName = branch.list();
        File current = join(GITLET_DIR, "current_branch");
        String currentName = readContentsAsString(current);
        File head = join(GITLET_DIR, "head_area");
        File[] headFile = head.listFiles();
        String[] headName = head.list();
        if(name.equals(currentName)) {
            System.out.print("Cannot remove the current branch.");
            return;
        }
        int change = 0;
        for(int i = 0; i < branchContents.length; i++) {
            if(name.equals(branchName[i])) {
                branchContents[i].delete();
                change = 1;
            }
        }
        for(int i = 0; i < headFile.length; i++) {
            if(name.equals(headName[i])) {
                headFile[i].delete();
            }
        }
        if(change != 1) System.out.print("A branch with that name does not exist.");
    }
    public static void reset(String id) throws IOException {
        File workingDirectory = CWD;
        File[] workingList = workingDirectory.listFiles();
        String[] workingName = workingDirectory.list();
        File HEAD = join(GITLET_DIR, "HEAD");
        File Blobs = join(GITLET_DIR, "Blobs"); // Space for accessing files
        File[] BlobsList = Blobs.listFiles();
        String[] BlobsName = Blobs.list();
        File commitArea = join(GITLET_DIR, "committing_area"); // Accessing commiting_area
        File[] commitFile = commitArea.listFiles();
        String[] commitName = commitArea.list();
        File fileList = null;
        Commit one = null;
        int change = 0;
        for(int i = 0; i < commitFile.length; i++) {
            if(id.equals(commitName[i])) change = 1;
        }
        if(change != 1) {
            System.out.print("No commit with that id exists.");
            return;
        }
        for(int j = 0; j < BlobsList.length; j++) { // Import a list of files in the HEAD commit from the Blobs folder
            if (id.equals(BlobsName[j])) fileList = BlobsList[j];
        }
        File[] component = fileList.listFiles();
        String[] name = fileList.list();
        String[] temp = new String[name.length];
        int[] num = new int[name.length];
        int notChange = 0;
        for(int i = 0; i < num.length; i++) num[i] = 0;
        for(int k = 0; k < workingName.length; k++) {
            if(workingList[k].isFile()){ // Check whether it is a file or not.
                for(int i = 0; i < name.length; i++) {
                    String first = readContentsAsString(component[i]);
                    String second = readContentsAsString(workingList[k]);
                    if(name[i].equals(workingName[k])) { // Comparing between file of commit and file of working directory
                        if(!(first.equals(second))) {
                            if(num[i] != 1) num[i] = 1;
                            notChange = 1;
                        }
                    }
                    else {
                        num[i] = 0;
                        temp[i] = first;
                    }
                }
            }
        }
        if(notChange == 1) {
            System.out.print("There is an untracked file in the way; delete it, or add and commit it first.");
            return;
        }
        for(int i = 0; i < num.length; i++) {
            if(num[i] == 0) {
                File build = join(CWD, name[i]);
                build.createNewFile();
                writeContents(build, temp[i]);
            }
        }
        for(int i = 0; i < commitFile.length; i++) {
            if(id.equals(commitName[i])) {
                one = readObject(commitFile[i], Commit.class);
                break;
            }
        }
        writeObject(HEAD, one); // Set the head with the corresponding commit
        File current = join(GITLET_DIR, "current_branch");
        writeContents(current, one.getBranch()); // To set as the branch of that commit
        File area = join(GITLET_DIR, "staging_area");
        File[] folder_list = area.listFiles();
        for (int i = 0; i < folder_list.length; i++) {
            folder_list[i].delete();
        }
    }
//    public static void merge(String name) {
//
//    }
}