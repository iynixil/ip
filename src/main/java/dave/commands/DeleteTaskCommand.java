package dave.commands;

import dave.Storage;
import dave.TaskList;
import dave.Ui;

import dave.tasks.Task;
import dave.exceptions.UnableToFindTaskException;

public class DeleteTaskCommand extends Command {
    private int taskNumber;

    public DeleteTaskCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task toDelete = taskList.getTask(this.taskNumber);
            taskList.deleteTask(this.taskNumber);
            storage.rewriteOutput(taskList);
            ui.showTaskDeleted(toDelete, taskList);    
        } catch (Exception exc) {
            ui.showHorizontalLine();
            System.out.println(new UnableToFindTaskException(taskList).getMessage());
            ui.showHorizontalLine();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
