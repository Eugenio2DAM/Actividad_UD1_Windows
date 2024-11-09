/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entrega_Act_UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class GettingIP {
    public static void main(String[] args) {
        Runtime newRuntime = Runtime.getRuntime();
        Process newProcess = null;
        String command = "cmd /c ipconfig";
        
        System.out.println("Ejecutando ipconfig...");
        
        try {
            newProcess = newRuntime.exec(command);
            InputStream newInputStream = newProcess.getInputStream();
            BufferedReader newBufferedReader = new BufferedReader(new InputStreamReader(newInputStream));
            String line;
            String ipAddress = null;

            while ((line = newBufferedReader.readLine()) != null) {
                if (line.contains("IPv4") && !line.contains("127.0.0.1")) {
                    String[] parts = line.trim().split(":");
                    if (parts.length > 1) {
                        ipAddress = parts[1].trim();
                        break;
                    }
                }
            }
            newBufferedReader.close();
            
            if (ipAddress != null) {
                System.out.println("La IP obtenida es: " + ipAddress);
            } else {
                System.out.println("No se encontró una dirección IPv4 válida.");
            }
            
        } catch (IOException ioe) {
            System.out.println("Imposible iniciar el proceso: " + ioe.getMessage());
        }
        
        int returnValue;
        try {
            returnValue = newProcess.waitFor();
            System.out.println("El proceso tiene un resultado de: " + returnValue);
        } catch (InterruptedException ie) {
            System.out.println("El proceso ha sido interrumpido");
        }
    }
}