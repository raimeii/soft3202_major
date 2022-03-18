package billingsystem;

import client.ClientReporting;
import compliance.ComplianceReporting;
import permissions.AuthenticationModule;
import permissions.AuthorisationModule;
import project.Project;

import java.util.List;

public interface BSFacade {

    Project addProject(String name, String client, double standardRate, double overRate);

    boolean addTask(int projectID, String taskDescription, int taskHours, boolean force);

    void audit();

    void finaliseProject(int projectID);

    int findProjectID(String searchName, String client);

    List<Project> getAllProjects();

    void injectAuth(AuthenticationModule authenticationModule, AuthorisationModule authorisationModule);

    void injectClient(ClientReporting clientReporting);

    void injectCompliance(ComplianceReporting complianceReporting);

    boolean login(String username, String password);

    void logout();

    void removeProject(int projectID);

    List<Project> searchProjects(String client);

    void setProjectCeiling(int projectID, int ceiling);

}
