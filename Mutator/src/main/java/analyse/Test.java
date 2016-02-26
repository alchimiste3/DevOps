package analyse;

public class Test {
    
    private String nom;
    private String nomClass;
    private boolean fail;
    private String typeFail;
    
    public Test(String nom, String nomClass) {
        this.nom = nom;
        this.nomClass = nomClass;
    }

    ///////////////////////////// Getters et Setters //////////////////////////////
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomClass() {
        return nomClass;
    }

    public void setNomClass(String nomClass) {
        this.nomClass = nomClass;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }

    public String isTypeFail() {
        return typeFail;
    }

    public void setTypeFail(String typeFail) {
        this.typeFail = typeFail;
    }
    
    public String display() {
        String res = "Nom du test : "+ nom + "\nNom de la class teste : " + nomClass + "\nExecution réussie ? ";
        
        if(fail){
            res += "Non";
            res += "\nType de l'erreur : " + typeFail;
        }else{
            res += "Oui";    
        }

        return res;
     }
    

}
