package fr.uvsq.cprog.collex;

import java.util.List;

public class CommandRechercheDomain implements Commande {
    private final Dns dns;
    private final String domain;

    public CommandRechercheDomain(Dns dns, String domain) {
        this.dns = dns;
        this.domain = domain;
    }

    @Override
    public Object execute() throws Exception {
        List<DnsItem> items = dns.getItemsByDomain(domain);
        return items;
    }
}