package billingsystem;

import client.ClientReporting;
import compliance.ComplianceReporting;
import permissions.AuthenticationModule;
import permissions.AuthorisationModule;
import project.Project;

import java.util.List;

public class BSFacadeImpl implements BSFacade {
    @Override
    public Project addProject(String name, String client, double standardRate, double overRate) {
        return Project.makeProject(name, standardRate, overRate);
    }

    @Override
    public boolean addTask(int projectID, String taskDescription, int taskHours, boolean force) {
        return false;
    }

    @Override
    public void audit() {

    }

    @Override
    public void finaliseProject(int projectID) {

    }

    @Override
    public int findProjectID(String searchName, String client) {
        return 0;
    }

    @Override
    public List<Project> getAllProjects() {
        return null;
    }

    @Override
    public void injectAuth(AuthenticationModule authenticationModule, AuthorisationModule authorisationModule) {

    }

    @Override
    public void injectClient(ClientReporting clientReporting) {

    }

    @Override
    public void injectCompliance(ComplianceReporting complianceReporting) {

    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public void logout() {

    }

    @Override
    public void removeProject(int projectID) {

    }

    @Override
    public List<Project> searchProjects(String client) {
        return null;
    }

    @Override
    public void setProjectCeiling(int projectID, int ceiling) {

    }
}
