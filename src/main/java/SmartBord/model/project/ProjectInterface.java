package SmartBord.model.project;

import SmartBord.exception.SmartBordException;

public interface ProjectInterface {
    Boolean addColumn(String column) throws SmartBordException;

}
