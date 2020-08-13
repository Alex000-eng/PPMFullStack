package edu.cornell.PPMFullStack.Exceptions;

public class ProjectIdExpectionResponse {

    private String projectIdentifier;

    public ProjectIdExpectionResponse(String projectIdentifier) {
        this.projectIdentifier= projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier= projectIdentifier;
    }

}
