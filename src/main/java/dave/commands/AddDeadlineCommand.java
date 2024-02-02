package dave.commands;

import java.io.IOException;

import dave.Storage;
import dave.TaskList;
import dave.Ui;

import dave.tasks.Deadline;

public class AddDeadlineCommand extends Command {
    private Deadline toAdd;

    public AddDeadlineCommand(String taskName, String deadline) {
        this.toAdd = new Deadline(taskName, deadline);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.addTask(this.toAdd);
        try {
            storage.saveTask(this.toAdd);
        } catch (IOException exc) {
            System.out.println("Dave could not write the new task to the output file");
        }
        ui.showTaskAdded(this.toAdd, taskList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
