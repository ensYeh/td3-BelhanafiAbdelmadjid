package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.util.Scanner;

public class DnsApp {
    private final Dns dns;
    private final DnsTUI tui;

    public DnsApp() throws IOException {
        this.dns = new Dns();
        this.tui = new DnsTUI(dns);
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("DnsApp started. commands: ip | nom | domain | add | quit");
            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) break;
                String line = scanner.nextLine();
                Commande commande = tui.nextCommande(line);
                if (commande == null) continue;
                Object result;
                try {
                    result = commande.execute();
                } catch (Exception e) {
                    result = "Erreur durant l'exécution : " + e.getMessage();
                }
                tui.affiche(result);
                if (result instanceof String && "QUIT".equals(result)) {
                    System.out.println("Au revoir.");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            new DnsApp().run();
        } catch (IOException e) {
            System.err.println("Impossible d'initialiser Dns: " + e.getMessage());
        }
    }
}