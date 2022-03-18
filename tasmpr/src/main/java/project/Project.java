package project;

public interface Project {

    int getId();

    String getName();

    double getOverDifference();

    double getStandardRate();

    static Project makeProject(int id, String name, double standardRate, double overDifference) {
        return null;
    }

    void setOverDifference(double overDifference);

    void setStandardRate(double standardRate);


}
