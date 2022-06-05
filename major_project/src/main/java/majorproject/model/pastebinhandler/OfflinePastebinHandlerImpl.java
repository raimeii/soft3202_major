package majorproject.model.pastebinhandler;

/**
 * Offline implementation of the PastebinHandler interface, simply returns an empty string
 */
public class OfflinePastebinHandlerImpl implements PastebinHandler {
    @Override
    public String generateOutputReport(String toReport) {
        return "";
    }
}
