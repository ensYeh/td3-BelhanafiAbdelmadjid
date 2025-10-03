package fr.uvsq.cprog.collex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests unitaires pour la classe NomMachine.
 */
public class NomMachineTest {
    
    @Test
    public void shouldInitialiseTheClass() {
        NomMachine nom = new NomMachine("server1");

        assertNotNull(nom);
        assertEquals("server1", nom.getNom());
    }
    
    @Test 
    public void shouldRejectNullNom() {
        try {
            new NomMachine(null);
            assertTrue("Le nom null aurait dû être rejeté.", false);
        } catch(IllegalArgumentException e) {
            assertEquals("Nom de machine invalide : null", e.getMessage());
        }
    }
    
    @Test
    public void shouldRejectMalFormedNoms() {
        String[] testNoms = {
            "-server",              
            "server-",             
            "-server-",             
            "ser ver",           
            "server@host",       
            "server#1",           
            "server_1",             
            ".server.com",         
            "server.com.",      
            "server..com",        
            "",                   
            "a".repeat(256),      
            "a".repeat(64) + ".com" 
        };
        
        for(String nom : testNoms) {
            try {
                new NomMachine(nom);
                assertTrue("Le nom " + nom + " aurait dû être rejeté.", false);
            } catch(IllegalArgumentException e) {
                assertEquals("Nom de machine invalide : " + nom, e.getMessage());
            }
        }
    }
    
    @Test
    public void shouldRejectEmptyString() {
        try{
            new NomMachine("");
            assertTrue("Le nom vide aurait dû être rejeté.", false);
        } catch(IllegalArgumentException e) {
            assertEquals("Nom de machine invalide : ", e.getMessage());
        }
    }

    @Test
    public void shouldAcceptValidNoms() {
        String[] validNoms = {
            "www",
            "server1",
            "my-server", 
            "host123",
            "a",                      
            "A1",                       
            
            "www.example.com",
            "server.domain.local",
            "my-server.company.org",
            "web-site-1.test.fr",
            "api.v2.service.com",
            "sub.domain.example.org",
            
            // Cas limites valides
            "host-123.sub-domain.example-site.com",
            "a.b.c.d.e",               
            "x".repeat(63) + ".com"   
        };
        
        for (String nom : validNoms) {
            NomMachine nomMachine = new NomMachine(nom);
            assertNotNull(nomMachine);
            assertEquals(nom, nomMachine.getNom());
        }
    }
    
    @Test
    public void shouldTestValidateNomStaticMethod() {
        assertTrue(NomMachine.validateNom("server1"));
        assertTrue(NomMachine.validateNom("my-server"));
        assertTrue(NomMachine.validateNom("a"));
        
        assertTrue(NomMachine.validateNom("www.example.com"));
        assertTrue(NomMachine.validateNom("server.domain.local"));
        assertTrue(NomMachine.validateNom("api.v2.service.com"));
        
        assertFalse(NomMachine.validateNom(null));
        assertFalse(NomMachine.validateNom(""));
        assertFalse(NomMachine.validateNom("-server"));
        assertFalse(NomMachine.validateNom("server-"));
        assertFalse(NomMachine.validateNom(".server.com"));
        assertFalse(NomMachine.validateNom("server.com."));
        assertFalse(NomMachine.validateNom("server..com"));
        assertFalse(NomMachine.validateNom("ser ver.com"));
    }
    
    @Test
    public void shouldAcceptLongButValidDomains() {
        String longDomain = "very-long-subdomain-name-that-is-still-valid.another-long-part.example.com";
        NomMachine nomMachine = new NomMachine(longDomain);
        assertNotNull(nomMachine);
        assertEquals(longDomain, nomMachine.getNom());
    }
}