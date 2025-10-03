package fr.uvsq.cprog.collex;
import java.util.regex.Pattern;
/**
 * Hello world!
 *
 */
public class AdresseIP 
{
    private String ip; 
    // On utilise la class Pattern de utils pour vérifier si l'IP est valide (exclut les octets à 0)
    private static final Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[1-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[1-9])$"); // Regex pour valider une adresse IPv4
    public AdresseIP(String ip) {
        if(!validateIP(ip)) {
            throw new IllegalArgumentException("Adresse IP invalide, elle doit respecter le format x.x.x.x avec x entre 0 et 255.");
        }
        this.ip = ip;
    }
    public static boolean validateIP(String ip){
        return ip != null && IP_PATTERN.matcher(ip).matches();
    }
    public String getIp() {
        return this.ip;
    }
}
