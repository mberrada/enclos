package com.enclos.data;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SimpleReader {

    private String jsonFilePath;
    private Object file;

    public SimpleReader(String fileName) {
        JSONParser parser = new JSONParser();
        try {
            this.file = parser.parse(new FileReader("resources/save/" + fileName + ".json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> readGame(String fileName) {
        JSONParser parser = new JSONParser();
        Object targetFile = null;
        try {
            targetFile = parser.parse(new FileReader("resources/save/" + fileName + ".json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> values = new HashMap<String, Object>();
        try {
            JSONObject reader = (JSONObject) targetFile;

            for (Params param : Params.values()) {
                Object value = reader.get(param.toString());
                values.put(param.toString(), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public static List<Player> readPlayer(String fileName) {
        JSONParser parser = new JSONParser();
        Object targetFile = null;
        try {
            targetFile = parser.parse(new FileReader("resources/players/" + fileName + ".json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Player> listPlayers = new ArrayList<>();
        try {
            JSONObject reader = (JSONObject) targetFile;
            JSONArray players = (JSONArray) reader.get("Players");

            Iterator iterator = players.iterator();
            while (iterator.hasNext()) {
                JSONArray currentPlayer = (JSONArray) iterator.next();
                String[] playerInfo = ((String) currentPlayer.get(0)).split(",");

                String lastName = playerInfo[0];
                String firstName = playerInfo[1];
                int age = Integer.parseInt(playerInfo[2]);
                String picturePath = playerInfo[3];

                listPlayers.add(new Player(firstName, lastName, age, picturePath));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPlayers;
    }

    private enum Params {
        SHEEPNUMBER, BOARDSIZE, PLAYERS, SHEEPSPOSITIONS, BARRIERS; // ; is required
        // here.

        @Override
        public String toString() {
            // only capitalize the first letter
            String s = super.toString();
            return s.substring(0, 1) + s.substring(1).toLowerCase();
        }
    }
}