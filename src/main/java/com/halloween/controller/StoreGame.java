package com.halloween.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.halloween.model.Neighborhood;
import com.halloween.model.Player;
import com.halloween.model.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class StoreGame {
//    private Gson gson = new Gson();

    public <T> T loadGame(String resourceFile, Type type, Gson gson) {

        try {
            URL url = StoreGame.class.getProtectionDomain().getCodeSource().getLocation();
            File jar = new File(url.toURI());
            File f = new File(jar.getParent(), resourceFile);
            try {
                if (f.exists()) {
                    Reader reader = new InputStreamReader(new FileInputStream(f));
                    return gson.fromJson(reader, type);
                } else {
                    System.out.println("There is no game to load! \n Starting a new game soon...");
                }
            } catch (FileNotFoundException e) {
                System.out.println("There is no game to load");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void saveGame(State state, Player player, Neighborhood neighborhood){
        writeFile(state, "state.json");
        writeFile(player, "player.json");
        writeFile(neighborhood, "neighborhood.json");
        System.out.println("Your game has been saved!");
    }

    private <T> void writeFile(T gameObject, String name) {
        Gson gson = new Gson();
        try {
        String filepath = new File(name).getAbsolutePath();
        FileWriter writer = new FileWriter(filepath, false);
            gson.toJson(gameObject, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            throw new RuntimeException();
        }
    }
    public void removeJsonFiles() {
        removeFile("state.json");
        removeFile("player.json");
        removeFile("neighborhood.json");
    }
    private void removeFile(String resourceFile) {
        try {
            URL url = StoreGame.class.getProtectionDomain().getCodeSource().getLocation();
            File jar = new File(url.toURI());
            File f = new File(jar.getParentFile().getParent(), resourceFile);
            Files.deleteIfExists(Path.of(f.getAbsolutePath()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
