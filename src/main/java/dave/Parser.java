package dave;

import dave.commands.*;
import dave.exceptions.*;

public class Parser {
    private enum CommandType {
        LIST,
        DELETE,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        BYE
    }

    /**
     * Parses inputs from user.
     * 
     * @param input User input.
     * @return A command that executes operations according to the user's input.
     * @throws ChatbotException If unrecognisable or invalid input is given.
     */
    public static Command parse(String input) throws ChatbotException {
        String[] inputArr = input.split(" ");

        CommandType commandStr = null;

        try {
            commandStr = CommandType.valueOf(inputArr[0].toUpperCase());
        } catch (IllegalArgumentException exc) {
            return new InvalidCommand(new InvalidInputException());
        } finally {
            if (commandStr != null) {
                switch (commandStr) {
                case LIST:
                    return new ListTasksCommand();
                case DELETE:
                    int deleteNumber = Integer.parseInt(inputArr[1]) - 1;
                    try {
                        return new DeleteTaskCommand(deleteNumber);
                    } catch (Exception exc) {
                        System.out.println(exc.getMessage());
                    }
                case MARK:
                    int markNumber = Integer.parseInt(inputArr[1]) - 1;
                    return new MarkTaskCommand(markNumber);
                case UNMARK:
                    int unmarkNumber = Integer.parseInt(inputArr[1]) - 1;
                    return new UnmarkTaskCommand(unmarkNumber);
                case TODO:
                    try {
                        String todoName = input.substring(5);
                        return new AddTodoCommand(todoName);
                    } catch (Exception exc) {
                        return new InvalidCommand(new EmptyTaskException("Dave cannot record a TODO task without a name."
                        + "\nPlease help Dave by entering your TODO name as follows:\n" +
                        "\ntodo <name>"));
                    }
                case DEADLINE:
                    try {
                        int idxDeadline = input.indexOf("/by");
                        String deadlineName = input.substring(9, idxDeadline - 1);
                        String deadline = input.substring(idxDeadline + "/by ".length());
                        return new AddDeadlineCommand(deadlineName, deadline);
                    } catch (Exception exc) {
                        return new InvalidCommand(new EmptyTaskException("Dave cannot record a DEADLINE task without a name/deadline."
                        + "\nPlease help Dave by entering your DEADLINE task as follows:\n" +
                        "\ndeadline <name> /by dd-mm-yyyy hhmm"));
                    }
                case EVENT:
                    try {
                        int idxFrom = input.indexOf("/from");
                        int idxTo = input.indexOf("/to");
                        String eventName = input.substring(6, idxFrom - 1);
                        String from = input.substring(idxFrom + "/from ".length(), idxTo - 1);
                        String to = input.substring(idxTo + "/to ".length());
                        return new AddEventCommand(eventName, from, to);
                    } catch (Exception exc) {
                        return new InvalidCommand(new EmptyTaskException("Dave cannot record an EVENT task without a name/duration."
                        + "\nPlease help Dave by entering your EVENT task as follows:\n" +
                        "\nevent <name> /from dd-mm-yyy hhmm /to dd-mm-yyyy hhmm"));
                    }
                case BYE:
                    return new ExitCommand();

                default:
                    return new InvalidCommand(new InvalidInputException());
                }
            }
        }
        return new InvalidCommand(new InvalidInputException());
    }

}
