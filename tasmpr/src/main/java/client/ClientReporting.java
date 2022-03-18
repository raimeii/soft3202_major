package client;

import permissions.AuthToken;

public interface ClientReporting {

    void sendReport(String clientName, String reportData, AuthToken token);

}
