package dave.commands;

import dave.Storage;
import dave.Ui;
import dave.TaskList;

public class ListTasksCommand extends Command {

    /**
     * {@inheritDoc}
     * Lists the current tasks in the task list.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showHorizontalLine();
        System.out.println("\nHere are the tasks in your list:\n");
        for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
            System.out.println(String.format("%d. %s", i+1, taskList.getTask(i).toString()));
        }
        ui.showHorizontalLine();
    }

    /**
     * {@inheritDoc}
     * Not an exit command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
