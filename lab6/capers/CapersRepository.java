package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = CWD;
    // TODO Hint: look at the `join`
    //      function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() throws IOException {
        // TODO
        File cap = join(CAPERS_FOLDER, ".capers");
        cap.mkdir();
        File story = join(cap, "story");
        File dogs = join(cap, "dogs");
        story.createNewFile();
        dogs.mkdir();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File note = join(CAPERS_FOLDER, ".capers", "story");
        String before = readContentsAsString(note);
        if(before.length() != 0) Utils.writeContents(note,  before + "\n" + text);
        else Utils.writeContents(note, text);
        String after = readContentsAsString(note);
        System.out.printf(after);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) throws IOException {
        // TODO
        Dog nd = new Dog(name, breed, age);
        nd.saveDog();
        if(nd.toString().length() != 0) System.out.printf(nd.toString());
        else System.out.printf("\n" + nd.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) throws IOException {
        // TODO
        Dog bc = new Dog(null, null, 0);
        bc = bc.fromFile(name);
        bc.haveBirthday();
        bc.saveDog();
    }
}