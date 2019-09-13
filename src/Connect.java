public class Connect {
    private int ID1;
    private int ID2;
    private int level;

    public int getID1() {
        return ID1;
    }

    public int getID2() {
        return ID2;
    }

    public int getLevel() {
        return level;
    }
    public void increase(){
        if(level<200)level++;
    }
    public void decrease(){
        if(level>0)level--;
    }
    public Connect(int ID1,int ID2,int level){
        this.ID1=ID1;
        this.ID2=ID2;
        this.level=level;
    }
}
