package edu.cornell.PPMFullStack.Exceptions;

public class ProjectNotFoundExceptionResponse {
    private String projectNotFound;

    public String getProjectNotFound() {
        return projectNotFound;
    }

    public void setProjectNotFoundString(String projectNotFound) {
        this.projectNotFound= projectNotFound;
    }

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        super();
        this.projectNotFound= projectNotFound;
    }

}
