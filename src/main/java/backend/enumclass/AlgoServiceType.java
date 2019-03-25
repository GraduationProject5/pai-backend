package backend.enumclass;

public enum AlgoServiceType {

    EVALUATION("evaluation"),
    ML("ml"),
    TEXTANALYSIS("textanalysis");

    public String serviceName;

    AlgoServiceType(String serviceName) {
        this.serviceName = serviceName;
    }




}
