package gitlet;

import java.io.IOException;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?

        SomeObj bloop = new SomeObj();
        if(args.length == 0) {
            System.out.print("No command with that name exists.");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                bloop.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                bloop.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                bloop.commit(args[1]);
                break;
            case "rm":
                bloop.rm(args[1]);
                break;
            case "log":
                bloop.log();
                break;
            case "status":
                bloop.status();
                break;
            case "checkout":
                if(args.length == 3) {
                    bloop.checkout1(args[1], args[2]); // --, file name
                    break;
                }
                else if(args.length == 4) {
                    bloop.checkout2(args[1], args[2], args[3]); // commit id, --, file name
                    break;
                }
                else {
                    bloop.checkout3(args[1]);// branch name
                    break;
                }
            case "global-log":
                bloop.globalLog();
                break;
            case "find":
                bloop.find(args[1]);
                break;
            case "branch":
                bloop.branch(args[1]);
                break;
            case "rm-branch":
                bloop.rmBranch(args[1]);
                break;
            case "reset":
                bloop.reset(args[1]);
                break;
//            case "merge":
//                bloop.merge(args[1]);
//                break;
            default:
                System.out.print("Incorrect operands.");
                return;
        }
    }
}