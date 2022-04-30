package major_project.model;

public class AppModel {
    private final GuardianHandler guardianHandler;
    private final PastebinHandler pastebinHandler;

    public AppModel() {
        this.guardianHandler = new GuardianHandler();
        this.pastebinHandler = new PastebinHandler();
    }
}
