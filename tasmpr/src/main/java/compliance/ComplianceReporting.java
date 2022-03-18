package compliance;

import permissions.AuthToken;

public interface ComplianceReporting {

    void sendReport(String projectName, int variance, AuthToken token);

}
