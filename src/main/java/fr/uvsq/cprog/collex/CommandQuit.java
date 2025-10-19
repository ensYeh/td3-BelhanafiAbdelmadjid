package fr.uvsq.cprog.collex;

public class CommandQuit implements Commande {
    @Override
    public Object execute() {
        return "QUIT";
    }
}