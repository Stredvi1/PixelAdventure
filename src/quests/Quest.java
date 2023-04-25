package quests;

public class Quest {

    private String quest;
    private String npcName;
    private int questID;

    public Quest(String npcName, String quest, int questID) {
        this.npcName = npcName;
        this.quest = quest;
        this.questID = questID;
    }

    public String get() {
        return npcName + ":  " + quest;
    }

    public int getID() {
        return questID;
    }
}
