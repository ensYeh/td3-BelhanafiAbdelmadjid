package fr.uvsq.cprog.collex;
import java.util.Objects;
import java.util.regex.Pattern;
public class NomMachine {
    private String nom;
    
    private static final Pattern NOM_PATTERN = Pattern.compile("^(?=.{1,255}$)([a-zA-Z0-9]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9])?)(\\.[a-zA-Z0-9]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9])?)*$");

    
    public NomMachine(String nom) {
        if (!validateNom(nom)) {
            throw new IllegalArgumentException("Nom de machine invalide : " + nom);
        }
        this.nom = nom;
    }
    
    public static boolean validateNom(String nom) {
        return nom != null && !nom.isEmpty() && NOM_PATTERN.matcher(nom).matches();
    }
    
    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomMachine)) return false;
        NomMachine that = (NomMachine) o;
        return nom.equals(that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
