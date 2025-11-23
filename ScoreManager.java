package Project3_6713249;

import java.io.*;
import java.util.*;

public class ScoreManager {

    private final ArrayList<ScoreEntry> entries = new ArrayList<>();
    private final File storageFile;
    
    public ScoreManager() {
        storageFile = new File(constants.TABLEFILE);
        loadFromFile();
    }
    
    /*public void addScore(String name, int iconIndex, int score) {
        entries.add(new ScoreEntry(name, iconIndex, score));
        entries.sort((a, b) -> b.getScore() - a.getScore());

        if (entries.size() > 5)
            entries.remove(entries.size() - 1);
    }

    public List<ScoreEntry> getTopScores() {
        return Collections.unmodifiableList(entries);
    }*/
    
    public synchronized void addScore(String name, int iconIndex, int score) {
        entries.add(new ScoreEntry(name, iconIndex, score));
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        //keep data 100 
        if (entries.size() > 100) {
            while (entries.size() > 100) entries.remove(entries.size() - 1);
        }
        saveToFile();
    }
    //
    public synchronized List<ScoreEntry> getTopScores(int topN) {
        int n = Math.min(topN, entries.size());
        return Collections.unmodifiableList(entries.subList(0, n));
    }
    
    public synchronized List<ScoreEntry> getTopScores() {
        return Collections.unmodifiableList(entries);
    }
    
    // -------- file I/O --------
    private void loadFromFile() {
        entries.clear();
        if (!storageFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(storageFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;
                String name = parts[0];
                int iconIndex = Integer.parseInt(parts[1]);
                int score = Integer.parseInt(parts[2]);
                entries.add(new ScoreEntry(name, iconIndex, score));
            }
            entries.sort((a,b)-> Integer.compare(b.getScore(), a.getScore()));
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
    
    private void saveToFile() {
        // ensure parent dir exists
        try {
            storageFile.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(storageFile, false))) {
                for (ScoreEntry e : entries) {
                    bw.write(String.format("%s,%d,%d%n", e.getName().replace(",", " "), e.getIconIndex(), e.getScore()));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // optional: clear scores
    public void clearScores() {
        entries.clear();
        saveToFile();
    }
}
