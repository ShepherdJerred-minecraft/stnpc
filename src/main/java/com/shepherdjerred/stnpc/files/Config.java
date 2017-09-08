
package com.shepherdjerred.stnpc.files;

import com.shepherdjerred.stnpc.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;


public class Config {

    // Provide Instances
    private static Config instance;
    // Create variables for each file
    public File messagesf;
    public FileConfiguration messages;

    public Config() {
        instance = this;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    // Method to load and reload files
    public void loadFiles() {

        messagesf = new File(Main.getInstance().getDataFolder(), "messages.yml");

        // Copy the default file if they don't exist
        if (!messagesf.exists()) {

            messagesf.getParentFile().mkdirs();
            copy(Main.getInstance().getResource("messages.yml"), messagesf);

        }

        messages = new YamlConfiguration();

        // Load the file
        try {

            messages.load(messagesf);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // Method to save specified file
    public void saveFiles(String input) {

        try {

            if (input.equals("messages")) {

                messages.save(messagesf);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    // Method to copy the default files
    public void copy(InputStream in, File file) {

        try {

            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);

            }
            out.close();
            in.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
