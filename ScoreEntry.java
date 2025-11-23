package Project3_6713249;

public class ScoreEntry {
    private final String name;
    private final int iconIndex;
    private final int score;

    public ScoreEntry(String name, int iconIndex, int score) {
        this.name = name;
        this.iconIndex = iconIndex;
        this.score = score;
    }

    public String getName()      { return name; }
    public int getIconIndex()    { return iconIndex; }
    public int getScore()        { return score; }
}
