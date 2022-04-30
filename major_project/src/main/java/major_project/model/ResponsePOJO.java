package major_project.model;
import java.util.List;

public class ResponsePOJO {
    private String status;
    private Integer total;
    private List<ResultsPOJO> results;

    public ResponsePOJO(String status, Integer total, List<ResultsPOJO> results) {
        this.status = status;
        this.results = results;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getTotal() {
        return this.total;
    } 

    public List<ResultsPOJO> getResults() {
        return this.results;
    } 

}