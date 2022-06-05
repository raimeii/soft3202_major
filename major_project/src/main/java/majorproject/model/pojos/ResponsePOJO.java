package majorproject.model.pojos;
import java.util.List;

public class ResponsePOJO {
    /**
     * status of the response from the api call
     */
    private String status;
    /**
     * list of ResultsPOJO that contains the result information
     */
    private List<ResultsPOJO> results;

    public ResponsePOJO(String status, List<ResultsPOJO> results) {
        this.status = status;
        this.results = results;
    }

    /**
     * returns the response status
     *
     * @return response status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * returns the list of ResultsPOJO that contains the result information
     *
     * @return list of ResultsPOJOs with result information
     */
    public List<ResultsPOJO> getResults() {
        return this.results;
    } 

}