package fr.uvsq.cprog.collex;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
ee
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
}
