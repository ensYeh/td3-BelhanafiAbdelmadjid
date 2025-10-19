package fr.uvsq.cprog.collex;

public class CommandRechercheNom implements Commande {
    private final Dns dns;
    private final String nomText;

    public CommandRechercheNom(Dns dns, String nomText) {
        this.dns = dns;
        this.nomText = nomText;
    }

    @Override
    public Object execute() throws Exception {
        NomMachine nom = new NomMachine(nomText);
        DnsItem item = dns.getItemByNom(nom);
        return item != null ? item : null;
    }
}