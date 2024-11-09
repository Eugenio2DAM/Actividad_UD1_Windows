/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package entrega_Act_UD1;

import java.io.IOException;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class Calculator {

    public static void main(String[] args) throws InterruptedException {
        ProcessBuilder newProdessBuilder = new ProcessBuilder();
        newProdessBuilder.command("calc");
        Process newProcess;
        try {
            newProcess = newProdessBuilder.start();
            /*En windows waitFor no lo administra de la misma forma que en linux y no se obtine el relultado que
            se deberia, el programa no para hasta que se para, continua hasta el final.*/
            newProcess.waitFor();
            System.out.println("La calculadora se ha cerrado adecuadamente");
        } catch (IOException ioe) {
            System.out.println("No se ha podido abrir la calculadora. " + ioe.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("Se ha interrumpido la applicación. " + ie.getMessage());
        }
    }
}
