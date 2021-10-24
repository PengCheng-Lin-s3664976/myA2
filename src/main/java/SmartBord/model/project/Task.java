package SmartBord.model.project;

import SmartBord.exception.SmartBordException;

import java.io.Serializable;


public class Task implements Serializable {

    private String taskName;
    private String description;
    public Task(String taskName,String description) throws SmartBordException {
        if (taskName == null || taskName.isEmpty() || taskName.isBlank()){
            throw new SmartBordException("taskName is null or empty");
        }
        if (description == null || description.isEmpty() || description.isBlank()){
            throw new SmartBordException("taskName is null or empty");
        }
        this.taskName = taskName;
        this.description = description;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }


}
