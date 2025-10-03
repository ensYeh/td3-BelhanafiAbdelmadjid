package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test en anglais pour facilter la lecture du code.
 */
public class AdresseIPTest {
    @Test
    public void shouldInitialseTheClass() {
        AdresseIP ip = new AdresseIP("192.168.1.1");

        assertNotNull(ip);
        assertEquals("192.168.1.1", ip.getIp());
    }
    @Test 
    public void shouldRejectNullIP(){
        try {
            new AdresseIP(null);
        } catch(IllegalArgumentException e) {
            assertEquals("Adresse IP invalide, elle doit respecter le format x.x.x.x avec x entre 0 et 255.", e.getMessage());
        }
    }
    
    @Test
    public void shouldRejectMalFormedIPs(){
        String[] testIPs = {
            "192.168.1.256", // 256
            "192.168.1.-1",  
            "192.168.-1.25", 
            "192.-168.1.25", 
            "-192.168.1.25", 
            "256.100.50.25",
            "10.256.50.25",
            "10.100.72.256",
            "10.100.256.200",
            "192.168.1",
            "192.168",
            "192",
            "0.0.0.0"
        };
        for(String ip : testIPs) {
            try {
                new AdresseIP(ip);
                assertTrue("L'IP " + ip + " aurait du être rejetée.", false);
            } catch(IllegalArgumentException e) {
                assertEquals("Adresse IP invalide, elle doit respecter le format x.x.x.x avec x entre 0 et 255.", e.getMessage());
            }
        }
    }
    @Test
    public void shouldRejectEmptyString() {
        try{
            new AdresseIP("");
            assertTrue("L'IP vide aurait du être rejetée.", false);
        } catch(IllegalArgumentException e) {
            assertEquals("Adresse IP invalide, elle doit respecter le format x.x.x.x avec x entre 0 et 255.", e.getMessage());
        }
    }

    @Test
    public void shouldAcceptValidIPsWithoutZeros() {
        String[] validIPs = {
            "192.168.1.1",
            "10.1.1.1", 
            "172.16.1.1",
            "8.8.8.8",
            "255.255.255.255",
            "1.1.1.1",
            "193.42.22.90"
        };
        
        for (String ip : validIPs) {
            AdresseIP adresseIP = new AdresseIP(ip);
            assertNotNull(adresseIP);
            assertEquals(ip, adresseIP.getIp());
        }
    }
}
