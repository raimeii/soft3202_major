package major_project.model;

public class GuardianPOJO {
    private ResponsePOJO response;

    public GuardianPOJO(ResponsePOJO response) { this.response = response; }

    public ResponsePOJO returnResponse() {
        return response;
    }
}