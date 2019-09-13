import java.util.ArrayList;

public class Syllable {
    private int ID;
    private String syllable;
    private ArrayList<Connect>connects=new ArrayList<>();

    public ArrayList<Connect> getConnects() {
        return connects;
    }

    public void setConnects(ArrayList<Connect> connects) {
        this.connects = connects;
    }

    public int getID() {
        return ID;
    }

    public String getSyllable() {
        return syllable;
    }
    public Syllable(String syllable,int ID){
        this.syllable=syllable;
        this.ID=ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSyllable(String syllable) {
        this.syllable = syllable;
    }
}
