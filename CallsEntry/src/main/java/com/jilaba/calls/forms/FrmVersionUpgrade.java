package com.jilaba.calls.forms;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class FrmVersionUpgrade extends JFrame {

    public FrmVersionUpgrade() {
        String sourcePath = "\\\\192.168.10.20\\jilabaexes\\Docs\\GokulaSeranjeevi V\\HHH\\Calls.jar"; // Source path
        String currentDir = System.getProperty("user.dir");
        String destinationPath = currentDir + File.separator + "CallsNew.jar";
        String oldJarPath = currentDir + File.separator + "CallsNew.jar";
        String newJarPath = currentDir + File.separator + "Calls.jar";
        String deletePath = currentDir + File.separator + "Calls.jar";

        try {
            // Register shutdown hook for renaming the .jar file
            registerShutdownHook(oldJarPath, newJarPath);

            // Copy the JAR file to the new location
            copyJarFile(sourcePath, destinationPath);
            
            // Delete the old file (if needed)
            deleteJarFile(deletePath);
            
            System.exit(0);  
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error occurred while copying the JAR file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Register a shutdown hook to rename the file after the JVM shuts down
    private void registerShutdownHook(String oldJarPath, String newJarPath) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                File oldJarFile = new File(oldJarPath);
                File newJarFile = new File(newJarPath);

                if (oldJarFile.exists()) {
                    boolean renamed = oldJarFile.renameTo(newJarFile);
                    if (renamed) {
                        JOptionPane.showMessageDialog(this, "File renamed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to rename the file during shutdown.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error occurred while renaming the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }));
    }

    // Delete the .jar file if needed
    private static boolean deleteJarFile(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    // Copy the JAR file to the new location
    private void copyJarFile(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void main(String[] args) {
        new FrmVersionUpgrade();
    }
}
