package major_project.model.POJOS;

public class GuardianPOJO {

    /**
     * POJO that contains the Response
     */
    private ResponsePOJO response;

    public GuardianPOJO(ResponsePOJO response) { this.response = response; }

    /**
     * returns the ResponsePOJO associated to this object
     *
     * @return ResponsePOJO of the api request
     */
    public ResponsePOJO returnResponse() {
        return response;
    }
}