package fr.uvsq.cprog.collex;

import java.util.List;

/**
 * Interface texte-utilisateur pour notre DNS
**/
public class DnsTUI {
    private final Dns dns;

    public DnsTUI(Dns dns) {
        this.dns = dns;
    }

    /**
    Analyse une ligne et retourne une Commande.
    Commandes autorisées :
      ip <adresse>
      nom <nomMachine>
      domain <domaine>
      add <ip> <nomMachine>
      quit
    */
    public Commande nextCommande(String line) {
        // Bien verifier l'input de notre utilisateur
        if (line == null) {
            return new CommandQuit();
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        // on découpe l'input de notre utilisateur par le premier blanc qu'il utilise
        String[] parts = trimmed.split("\\s+");
        //on récupère la commande
        String cmd = parts[0].toLowerCase();

        switch (cmd) {
            // case "notre commande": 
                // vérifier le nombre d'arguments si il convient a la commande selectionée
                // appeler le constructeur de la commande correspondante
            case "ip":
                if (parts.length < 2) return () -> "Usage: ip <adresse>";
                return new CommandRechercheIp(dns, parts[1]);
            case "nom":
                if (parts.length < 2) return () -> "Usage: nom <nomMachine>";
                return new CommandRechercheNom(dns, parts[1]);
            case "domain":
                if (parts.length < 2) return () -> "Usage: domain <domaine>";
                return new CommandRechercheDomain(dns, parts[1]);
            case "add":
                if (parts.length < 3) return () -> "Usage: add <ip> <nomMachine>";
                return new CommandAjouter(dns, parts[1], parts[2]);
            case "ls":
                // ls [-a] domaine
                if (parts.length < 2) return () -> "Usage: ls [-a] <domaine>";
                boolean sortByIp = false;
                String domain;
                if (parts.length == 2) {
                    domain = parts[1];
                } else if (parts.length == 3 && "-a".equals(parts[1])) {
                    sortByIp = true;
                    domain = parts[2];
                } else {
                    return () -> "Usage: ls [-a] <domaine>";
                }
                return new CommandLs(dns, domain, sortByIp);
            case "quit":
            case "exit":
                return new CommandQuit();
            default:
                return () -> "Commande inconnue : " + cmd;
        }
    }

    /**
     * Afficher le résultat selon la commande qui a été exécutée
     */
    public void affiche(Object result) {
        if (result == null) {
            System.out.println("(aucun résultat)");
            return;
        }
        if (result instanceof String) {
            System.out.println((String) result);
            return;
        }
        if (result instanceof DnsItem) {
            DnsItem it = (DnsItem) result;
            System.out.printf("%s -> %s%n", it.getNomMachine().getNom(), it.getAdresseIP().getIp());
            return;
        }
        if (result instanceof List) {
            List<?> list = (List<?>) result;
            if (list.isEmpty()) {
                System.out.println("(liste vide)");
                return;
            }
            for (Object o : list) {
                if (o instanceof DnsItem) {
                    DnsItem it = (DnsItem) o;
                    System.out.printf("%s -> %s%n", it.getNomMachine().getNom(), it.getAdresseIP().getIp());
                } else {
                    System.out.println(o);
                }
            }
            return;
        }
        System.out.println(result.toString());
    }
}