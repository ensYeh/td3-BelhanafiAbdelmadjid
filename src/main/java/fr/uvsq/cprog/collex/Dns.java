package fr.uvsq.cprog.collex;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Properties;

public class Dns {
    // On devra l'intialiser dans le constructeur sinon on aura une erreur
    private final Path dbPath;
    // Liste pour stocker les entrées DNS
    private ArrayList<DnsItem> entries = new ArrayList<>();
    // On l'utilise pour lire le fichier de configuration dans notre classe
    private Properties props = new Properties();
    public Dns() throws IOException {
        String filePath = this.props.getProperty("db.file");
        if(filePath == null) {
            throw new IllegalArgumentException("Le chemin de la base de données n'est pas spécifié dans les propriétés.");
        }
        this.dbPath = Paths.get(filePath.trim());
        loadDataBase(this.dbPath);
    }
    private void loadDataBase(Path path) throws IOException {
        if(path == null) {
            throw new IllegalStateException("Le chemin de la base de données n'a pas été initialisé.");
        }
        if(Files.notExists(path)){
            throw new IOException("Base de données non trouvé.");
        }
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                AdresseIP adresseIP = new AdresseIP(parts[0].trim());
                NomMachine nomMachine = new NomMachine(parts[1].trim());
                DnsItem dnsItem = new DnsItem(adresseIP, nomMachine);
                entries.add(dnsItem);
            } else {
                throw new IOException("Format de ligne invalide dans la base de données : " + line);
            }
        }
    }
    // Retourne la première entrée correspondant à l'adresse IP donnée, ou null si introuvable.
    public DnsItem getItemByIp(AdresseIP ip) {
        if (ip == null) return null;
        for (DnsItem it : entries) {
            if (ip.equals(it.getAdresseIP())) {
                return it;
            }
        }
        return null;
    }

    // Retourne la première entrée correspondant au nom de machine donné, ou null si introuvable.
    public DnsItem getItemByNom(NomMachine nom) {
        if (nom == null) return null;
        for (DnsItem it : entries) {
            if (nom.equals(it.getNomMachine())) {
                return it;
            }
        }
        return null;
    }

    public List<DnsItem> getItemsByDomain(String domain) {
        List<DnsItem> result = new ArrayList<>();
        // on gère le cas null ou vide , donc on retourne une liste vide
        if (domain == null) return result;

        //on doit bien formater l'input utilisateur
        String d = domain.trim().toLowerCase();

        // si apres formatage c'est vide on retourne une liste vide
        if (d.isEmpty()) return result;

        for (DnsItem it : entries) {
            NomMachine nm = it.getNomMachine();
            if (nm == null) {
                continue;
            }
            // on récupère le nom de domaine de la machine
            String nomDomaine = nm.getNomDomaine();
            if (nomDomaine == null || nomDomaine.isEmpty()) continue;

            String lower = nomDomaine.toLowerCase();
            if (lower.equals(d) || lower.endsWith("." + d)) {
                result.add(it);
            }
        }
        return result;
    }
    public void addItem(AdresseIP adresse, NomMachine nom) throws IOException {
        if (adresse == null || nom == null) {
            throw new IllegalArgumentException("Adresse et nom doivent être non nuls.");
        }
        // Vérifier si le nom de machine existe déjà
        for (DnsItem it : entries) {
            NomMachine existing = it.getNomMachine();
            if (existing != null && existing.equals(nom)) {
                throw new IllegalArgumentException("ERREUR : Le nom de machine existe déjà !");
            }
        }

        // Préparer la ligne à écrire (même format que loadDataBase : "ip nom")
        String line = adresse.getIp() + " " + nom.getNom() + System.lineSeparator();
        
        // Écriture centralisée via DBEcrireEntry
        DBEcrireEntry(line);

        // Mettre à jour la collection en mémoire
        entries.add(new DnsItem(adresse, nom));
    }

    // Méthode unique pour écrire une entrée dans la base
    private void DBEcrireEntry(String line) throws IOException {
        try {
            Files.write(dbPath, line.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IOException("Impossible d'écrire dans la base de données : " + e.getMessage(), e);
        }
    }
}
