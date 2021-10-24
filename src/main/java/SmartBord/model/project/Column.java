package SmartBord.model.project;

import SmartBord.exception.SmartBordException;

import java.io.Serializable;
import java.util.ArrayList;

public class Column implements Serializable {
    private String columnName;
    private ArrayList<Task> tasks;

    public Column(String columnName) throws SmartBordException {
        if (columnName == null || columnName.isEmpty() || columnName.isBlank()){
            throw new SmartBordException("columnName is null or empty");
        }
        this.columnName = columnName;
        this.tasks = new ArrayList<>();
    }

    public String getColumnName() {
        return columnName;
    }


    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Boolean addTask(String taskName,String description) throws SmartBordException {

        for (Task task :tasks){
            if (taskName.equals(task.getTaskName())){
                return false;
            }
        }
        tasks.add(new Task(taskName,description));
        return true;
    }

    public Boolean DragTask(String taskName,String description, int i) throws SmartBordException {

        for (Task task :tasks){
            if (taskName.equals(task.getTaskName())){
                return false;
            }
        }
        tasks.add(i,new Task(taskName,description));
        return true;
    }

    public Boolean notFindTask(String taskName) {
        for (Task task :tasks){
            if (taskName.equals(task.getTaskName())){
                return false;
            }
        }
        return true;
    }

    public Boolean deleteTask(String taskName) {
        int i =0;
        for (Task task :tasks){
            if (taskName.equals(task.getTaskName())){
                tasks.remove(i);
                return true;
            }
            i++;
        }
    return false;
    }

    public Boolean updateTask(String oldTaskName,String taskName,String Description) throws SmartBordException {
        int i =0;
        for (Task task :tasks){
            if (oldTaskName.equals(task.getTaskName())){
                tasks.remove(i);
                tasks.add(new Task(taskName,Description));
                return true;
            }
            i++;
        }
        return false;
    }
}
