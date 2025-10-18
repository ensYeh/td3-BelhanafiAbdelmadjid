package fr.uvsq.cprog.collex;
import java.util.Objects;
import java.util.regex.Pattern;
public class NomMachine {
    private String nom;
    private final String nomMachine;
    private final String nomDomaine;

    private static final Pattern NOM_PATTERN = Pattern.compile("^(?=.{1,255}$)([a-zA-Z0-9]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9])?)(\\.[a-zA-Z0-9]([-a-zA-Z0-9]{0,61}[a-zA-Z0-9])?)*$");

    
    public NomMachine(String nom) {
        if (!validateNom(nom)) {
            throw new IllegalArgumentException("Nom de machine invalide : " + nom);
        }
        this.nom = nom;
        String[] parts = splitNom(nom);
        this.nomMachine = parts[0];
        this.nomDomaine = parts[1];
    }
    
    public static boolean validateNom(String nom) {
        return nom != null && !nom.isEmpty() && NOM_PATTERN.matcher(nom).matches();
    }

    public static String[] splitNom(String nom) {
        if (nom == null) {
            return new String[] { null, null };
        }
        int idx = nom.indexOf('.');
        if (idx < 0) {
            return new String[] { nom, "" };
        } else {
            return new String[] { nom.substring(0, idx), nom.substring(idx + 1) };
        }
    }
    
    public String getNom() {
        return nom;
    }

    public String getNomMachine() {
        return nomMachine;
    }

    public String getNomDomaine() {
        return nomDomaine;
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
