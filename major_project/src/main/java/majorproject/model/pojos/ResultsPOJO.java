package majorproject.model.pojos;

public class ResultsPOJO {


    /**
     * tag of the article
     */
    private String id;
    /**
     * web title of the article
     */
    private String webTitle;
    /**
     * publication date of the article
     */
    private String webPublicationDate;
    /**
     * web url to the article
     */
    private String webUrl;

    public ResultsPOJO(String id, String webTitle, String webPublicationDate, String webURL) {
        this.id = id;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webURL;
    }

    /**
     * Returns the id of the article
     *
     * @return article id
     */
    public String getID() {
        return id;
    }

    /**
     * returns the web title of the article
     *
     * @return web title of the article
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     * returns the web publication date of the article
     *
     * @return web publication date of the article
     */
    public String getWebPublicationDate() {return webPublicationDate; };

    /**
     * returns the web url of the article
     *
     * @return web url of the article
     */
    public String getWebURL() {return webUrl;}


    /**
     * returns a String representation of a ResultsPOJO, for use in displaying in the view
     *
     * @return String representation of the ResultsPOJO object
     */
    @Override
    public String toString() {
        return webTitle + ", published " + webPublicationDate;
    }


}