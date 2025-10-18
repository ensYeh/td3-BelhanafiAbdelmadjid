package fr.uvsq.cprog.collex;
import java.util.Objects;

public class DnsItem {
    private AdresseIP adresseIP;
    private NomMachine nomMachine;

    public DnsItem(AdresseIP adresseIP, NomMachine nomMachine) {
        this.adresseIP = adresseIP;
        this.nomMachine = nomMachine;
    }
    public AdresseIP getAdresseIP() {
        return adresseIP;
    }
    public NomMachine getNomMachine() {
        return nomMachine;
    }
    @Override
    public String toString() {
        return "DnsItem{" +
                "adresseIP=" + adresseIP +
                ", nomMachine=" + nomMachine +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DnsItem)) return false;
        DnsItem that = (DnsItem) o;
        return adresseIP.equals(that.adresseIP) && nomMachine.equals(that.nomMachine);
    }
    @Override
    public int hashCode() {
        return Objects.hash(adresseIP, nomMachine);
    }
}
