package SmartBord.model.project;

import SmartBord.exception.SmartBordException;

import java.io.Serializable;
import java.util.ArrayList;


public class Project implements ProjectInterface, Serializable {

    private String projectName;
    private ArrayList<Column> columns;

    public Project(String projectName) throws SmartBordException {
        if (projectName == null || projectName.isEmpty() || projectName.isBlank()){
            throw new SmartBordException("projectName is null or empty");
        }
        this.projectName = projectName;
        this.columns = new ArrayList<>();
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public Boolean addColumn(String columnName) throws SmartBordException {
        for (Column column:columns){
            if (columnName.equals(column.getColumnName())){

                return false;
            }
        }
        columns.add(new Column(columnName));
        return true;
    }

    public Boolean findColumn(String columnName) {
        for (Column column:columns){
            if (columnName.equals(column.getColumnName())){
                return true;
            }
        }
        return false;
    }

    public Boolean deleteColumn(String columnName) {
        int i =0;
        for (Column column:columns){
            if (columnName.equals(column.getColumnName())){
                columns.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
