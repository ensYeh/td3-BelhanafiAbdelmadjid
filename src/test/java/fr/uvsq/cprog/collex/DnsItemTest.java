package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit test en anglais pour facilter la lecture du code.
 */
public class DnsItemTest {
    @Test 
    public void shouldInitialiseTheClass() {
        AdresseIP ip = new AdresseIP("192.168.1.1"); 
        NomMachine nom = new NomMachine("server1");
        DnsItem dnsItem = new DnsItem(ip, nom);

        assertNotNull(dnsItem);
        assertEquals(ip, dnsItem.getAdresseIP());
        assertEquals(nom, dnsItem.getNomMachine());
    }

    @Test 
    public void shouldReturnStringRepresentation() {
        AdresseIP ip = new AdresseIP("192.168.1.1"); 
        NomMachine nom = new NomMachine("server1");
        DnsItem dnsItem = new DnsItem(ip, nom);
        String expectedString = "DnsItem{adresseIP=" + ip.toString() + ", nomMachine=" + nom.toString() + "}";
        assertEquals(expectedString, dnsItem.toString());
    }
}
