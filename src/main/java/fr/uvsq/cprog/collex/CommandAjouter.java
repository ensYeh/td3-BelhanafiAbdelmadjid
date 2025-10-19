package fr.uvsq.cprog.collex;

public class CommandAjouter implements Commande {
    private final Dns dns;
    private final String ipText;
    private final String nomText;

    public CommandAjouter(Dns dns, String ipText, String nomText) {
        this.dns = dns;
        this.ipText = ipText;
        this.nomText = nomText;
    }

    @Override
    public Object execute() throws Exception {
        AdresseIP ip = new AdresseIP(ipText);
        NomMachine nom = new NomMachine(nomText);
        dns.addItem(ip, nom);
        return "OK";
    }
}