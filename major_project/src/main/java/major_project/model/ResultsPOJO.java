package major_project.model;

public class ResultsPOJO {
    private String id;
    private String webTitle;
    private String webPublicationDate;
    private String webUrl;

    public ResultsPOJO(String id, String webTitle, String webPublicationDate, String webURL) {
        this.id = id;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webURL;
    }

    public String getID() {
        return id;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebPublicationDate() {return webPublicationDate; };

    public String getWebURL() {return webUrl;}

    @Override
    public String toString() {
        return webTitle + ", published " + webPublicationDate;
    }


}