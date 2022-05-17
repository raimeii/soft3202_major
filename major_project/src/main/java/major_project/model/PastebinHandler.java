package major_project.model;

public interface PastebinHandler {

    /**
     * Makes a post request to the Pastebin API to dump the string parameter into a pastebin dump, and returns the link
     * to this dump
     *
     * @param toReport output to report
     * @return url to the pastebin dump
     */
    String generateOutputReport(String toReport);
}
