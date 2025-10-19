package fr.uvsq.cprog.collex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ls [-a] domaine
 * - sans -a : tri par nom de machine (label/FQDN)
 * - avec -a : tri par adresse IP (ordre lexicographique)
 * Retourne List<String> où chaque élément est "ip nom"
 */
public class CommandLs implements Commande {
    private final Dns dns;
    private final String domain;
    private final boolean sortByIp;

    public CommandLs(Dns dns, String domain, boolean sortByIp) {
        this.dns = dns;
        this.domain = domain;
        this.sortByIp = sortByIp;
    }

    @Override
    public Object execute() {
        try {
            List<DnsItem> items = dns.getItemsByDomain(domain);
            if (items == null || items.isEmpty()) {
                return "Aucune machine pour le domaine : " + domain;
            }
            if (sortByIp) {
                items.sort(Comparator.comparing(it -> it.getAdresseIP().getIp()));
            } else {
                items.sort(Comparator.comparing(it -> it.getNomMachine().getNom()));
            }
            List<String> lines = new ArrayList<>();
            for (DnsItem it : items) {
                lines.add(it.getAdresseIP().getIp() + " " + it.getNomMachine().getNom());
            }
            return lines;
        } catch (Exception e) {
            return "Erreur lors de lister le domaine : " + e.getMessage();
        }
    }
}