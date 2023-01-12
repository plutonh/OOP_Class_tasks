//package gitlet;
//
//import java.io.File;
//import java.io.Serializable;
//
//public class Target implements Serializable { // Target Object has a file and tag and is stored in the directory blobs
//    private String message;
//    private String[] name;
//    private String[] tags;
//
//    public Target(String message, String[] name, String[] tags) {
//        this.message = message; // Commit message
//        this.name = name; // Name of each file
//        this.tags = tags; // Hash of each file
//    }
//    public String getMessage() {
//        return this.message;
//    }
//    public String[] getName() {
//        return this.name;
//    }
//    public String[] getTags() {
//        return this.tags;
//    }
//}