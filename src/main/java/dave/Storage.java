package dave;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import dave.tasks.Deadline;
import dave.tasks.Event;
import dave.tasks.Task;
import dave.tasks.Todo;

public class Storage {
    private String storageFilepath;

    public Storage(String filepath) {
        this.storageFilepath = filepath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> taskList = new ArrayList<Task>();
        BufferedReader br = new BufferedReader(new FileReader(this.storageFilepath));
        String line = br.readLine();
        while (line != null) {
            String[] taskDescription = line.split(" \\| ");
            String taskType = taskDescription[0];

            switch (taskType) {
                case "TODO":
                    taskList.add(new Todo(taskDescription[2]));
                    break;

                case "DEADLINE":
                    taskList.add(new Deadline(taskDescription[2], taskDescription[3]));
                    break;

                case "EVENT":
                    taskList.add(new Event(taskDescription[2], taskDescription[3], taskDescription[4]));
                    break;

                default:
                    throw new IOException();
            }

            if (taskDescription[1].equals("COMPLETED")) {
                taskList.get(taskList.size() - 1).setDone(true);
            }

            line = br.readLine();
        }
        br.close();
        return taskList;
    }

    public void saveTask(Task newTask) throws IOException {
        File fileToWrite = new File(this.storageFilepath);
        if (!fileToWrite.exists()) {
            fileToWrite.getParentFile().mkdir();
            fileToWrite.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.storageFilepath, true));
        writer.append(newTask.fileString());
        writer.newLine();
        writer.close();
    }

    public void rewriteOutput(TaskList taskList) {
        File fileToDelete = new File(this.storageFilepath);
        try {
            fileToDelete.delete();
            for (int i = 0; i < taskList.getNumberOfTasks(); i++) {
                saveTask(taskList.getTask(i));
            }
        } catch (IOException exc) {
            System.out.println(String.format("Dave had a problem updating the output file."));
        }
    }

}
