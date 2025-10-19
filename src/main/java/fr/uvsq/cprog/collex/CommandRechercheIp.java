package fr.uvsq.cprog.collex;

public class CommandRechercheIp implements Commande {
    private final Dns dns;
    private final String ipText;

    public CommandRechercheIp(Dns dns, String ipText) {
        this.dns = dns;
        this.ipText = ipText;
    }

    @Override
    public Object execute() throws Exception {
        AdresseIP ip = new AdresseIP(ipText);
        DnsItem item = dns.getItemByIp(ip);
        return item != null ? item : null;
    }
}