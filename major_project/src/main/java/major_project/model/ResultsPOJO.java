package major_project.model;

public class ResultsPOJO {
    private String id;
    private String webTitle;

    public ResultsPOJO(String id, String webTitle) {
        this.id = id;
        this.webTitle = webTitle;
    }

    public String getID() {
        return id;
    }

    public String getWebTitle() {
        return webTitle;
    }

    @Override
    public String toString() {
        return "ID " + id + " with title " + webTitle + ".";
    }


}