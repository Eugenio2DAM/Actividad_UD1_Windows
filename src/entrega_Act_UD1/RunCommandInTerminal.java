/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entrega_Act_UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @version 1.0
 * @author Eugenio Gimeno Dolz
 */
public class RunCommandInTerminal {

    public static void main(String[] args) {
        System.out.print("Escriba el comando a ejecutar: ");
        String newCommand = requestCommand();
        if (searchPipe(newCommand)) {
            runCommandWithPipes(commandQueue(newCommand));
        } else {
            runSimpleCommand(newCommand);
        }
    }

    private static String requestCommand() {
        Scanner lector = new Scanner(System.in);
        Boolean isValidInput = false;
        String command = null;
        while (!isValidInput) {
            command = lector.nextLine();
            if (command.length() > 0) {
                isValidInput = true;
            } else {
                System.out.println("Debe de introducir un comando.");
            }
        }
        return command;
    }

    private static ArrayList<String> commandQueue(String command) {
        String[] commands = command.split("\\|");
        ArrayList<String> commandQueue = new ArrayList<>();
        for (int i = 0; i < commands.length; i++) {
            commandQueue.add(commands[i]);
        }
        return commandQueue;
    }

    private static boolean searchPipe(String command) {
        for (int i = 0; i < command.length(); i++) {
            char search = command.charAt(i);
            if (search == '|') {
                return true;
            }
        }
        return false;
    }

    private static void runSimpleCommand(String command) {
        String[] newComandParts = {"cmd.exe", "/c", command};
        ProcessBuilder newProdessBuilder = new ProcessBuilder(newComandParts);
        Process newProcess = null;
        BufferedReader newBuffredReader = null;
        try {
            newProcess = newProdessBuilder.start();
            newBuffredReader = new BufferedReader(new InputStreamReader(newProcess.getInputStream()));
            String line;
            System.out.println("El resultado del comando es: ");
            while ((line = newBuffredReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = newProcess.waitFor();
            System.out.println("\nEl comando se ejecutó con éxito con código de salida: " + exitCode);
        } catch (IOException ioe) {
            System.out.println("Imposible ejecutar el comando. " + ioe.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("La ejecución del comando se ha interrumpido. " + ie.getMessage());
        } finally {
            if (newBuffredReader != null) {
                try {
                    newBuffredReader.close();
                } catch (IOException ioe) {
                    System.out.println("No se ha podido cerrar el buffer de lectura. " + ioe.getMessage());
                }
            } else if (newProcess != null) {
                newProcess.destroy();
            }
        }
    }

    private static void runCommandWithPipes(ArrayList<String> commands) {
        StringBuilder buildCommand = new StringBuilder();
        for (String command : commands) {
            buildCommand.append(command.trim()).append(" | ");
        }
        String finalCommand = buildCommand.toString().replaceAll(" \\| $", "");

        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", finalCommand);
        System.out.println("El resultado del comando es: ");
        Process newProcess = null;
        BufferedReader newBuffredReader = null;
        try {
            newProcess = processBuilder.start();
            newBuffredReader = new BufferedReader(new InputStreamReader(newProcess.getInputStream()));
            String line;
            while ((line = newBuffredReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = newProcess.waitFor();
            System.out.println("\nEl comando se ejecutó con éxito con código de salida: " + exitCode);
        } catch (IOException ioe) {
            System.out.println("Imposible ejecutar el comando. " + ioe.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("La ejecución del comando se ha interrumpido. " + ie.getMessage());
        }finally {
            if (newBuffredReader != null) {
                try {
                    newBuffredReader.close();
                } catch (IOException ioe) {
                    System.out.println("No se ha podido cerrar el buffer de lectura. " + ioe.getMessage());
                }
            } else if (newProcess != null) {
                newProcess.destroy();
            }
        }
    }
}
