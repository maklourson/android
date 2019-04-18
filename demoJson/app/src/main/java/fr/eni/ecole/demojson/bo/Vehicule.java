package fr.eni.ecole.demojson.bo;

public class Vehicule {

    private long Id;
    private String Designation;
    private String ModeleCommercial;
    private String CNIT;
    private String ModeleDossier;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getModeleCommercial() {
        return ModeleCommercial;
    }

    public void setModeleCommercial(String modeleCommercial) {
        ModeleCommercial = modeleCommercial;
    }

    public String getCNIT() {
        return CNIT;
    }

    public void setCNIT(String CNIT) {
        this.CNIT = CNIT;
    }

    public String getModeleDossier() {
        return ModeleDossier;
    }

    public void setModeleDossier(String modeleDossier) {
        ModeleDossier = modeleDossier;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "Id=" + Id +
                ", Designation='" + Designation + '\'' +
                ", ModeleCommercial='" + ModeleCommercial + '\'' +
                ", CNIT='" + CNIT + '\'' +
                ", ModeleDossier='" + ModeleDossier + '\'' +
                '}';
    }
}
