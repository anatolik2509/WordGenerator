import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Generator {
    static ArrayList<Syllable>syllables= new ArrayList<>();
    static ArrayList<Connect>connects=new ArrayList<>();
    static int percent=0;

    public static Syllable searchSyllable(String s){
        for(Syllable i:syllables) {
            if(i.getSyllable().compareTo(s)==0)return i;
        }
        return null;
    }
    public static Syllable searchSyllable(int id){
        for(Syllable i:syllables) {
            if(i.getID()==id)return i;
        }
        return null;
    }
    public static void newSyllable(String syllable,int id){
        Syllable s=new Syllable(syllable,id);
        for(Syllable i:syllables){
            connects.add(new Connect(i.getID(),id,0));
        }
        syllables.add(s);
    }
    public static Connect searchConnect(int id1,int id2){
        for(Connect i:connects){
            if((i.getID1()==id1&&i.getID2()==id2)||(i.getID2()==id1&&i.getID1()==id2)){
                return i;
            }
        }
        return null;
    }

    public static void save(String path){
        PrintWriter wr=null;
        try {
            wr=new PrintWriter(new File(path));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        int n=syllables.size();
        wr.println(syllables.size());
        int l=0;
        for(Syllable s:syllables){
            wr.println(s.getSyllable()+" "+s.getID());
            l++;
        }
        n=connects.size();
        wr.println(connects.size());
        for(Connect c:connects){
            wr.println(c.getID1()+" "+c.getID2()+" "+c.getLevel());
            l++;
        }
        wr.close();

    }
    public static void load(String path){
        Scanner sc=null;
        try{
            sc=new Scanner(new File(path));
        }catch (IOException e){
            e.printStackTrace();
        }
        int n=sc.nextInt();
        int l=0;
        for(int i=0;i<n;i++){
            syllables.add(new Syllable(sc.next(),sc.nextInt()));
            l++;
            percent=l*100/n*2;
        }
        n=sc.nextInt();
        l=0;
        for(int i=0;i<n;i++){
            connects.add(new Connect(sc.nextInt(),sc.nextInt(),sc.nextInt()));
            l++;
            percent=l*100/n*2+50;
        }
        sc.close();
        installConnects();
        System.out.println("done");
    }

    public static void installConnects(){
        for(Connect c:connects){
            searchSyllable(c.getID1()).getConnects().add(c);
            searchSyllable(c.getID2()).getConnects().add(c);
        }

    }


    public static final char[] gl={'а','е','ё','и','о','у','ы','э','ю','я'};
    public static final char[] sogl={'б','в','г','д','ж','з','й','к','л','м','н','п','р','с','т','ф','х','ц','ч','ш','щ'};
    public static final char[] spec={'ъ','ь','-'};
    public static boolean isGl(char c){
        for(int i=0;i<gl.length;i++){
            if(c==gl[i])return true;
        }
        return false;
    }
    public static boolean isSogl(char c){
        for(int i=0;i<sogl.length;i++){
            if(c==sogl[i])return true;
        }
        return false;
    }
    public static boolean isSpec(char c){
        for(int i=0;i<spec.length;i++){
            if(c==spec[i])return true;
        }
        return false;
    }


    public static String[] wordAnalis(String word){
        int l=0;
        word=word.toLowerCase();
        for(int i=0;i<word.length();i++){
            if(isGl(word.charAt(i))) l++;
        }
        String[]syllables=new String[l];
        int p1=word.length();
        int p2=word.length();
        for(int i=word.length()-1;i>=0;i--){
            if(isGl(word.charAt(i))){
                if(p1==p2)p1=i;
                else {
                    p1=(i+p1)/2+(i+p1)%2;
                    syllables[l-1]=word.substring(p1,p2);
                    l--;
                    p2=p1;
                    i++;
                }
            }
            if(isSpec(word.charAt(i))&&l!=1){
                if(p1!=p2){
                    p1=i+1;
                    syllables[l-1]=word.substring(p1,p2);
                    l--;
                    p2=p1;
                    i++;
                }
            }
        }
        syllables[0]=word.substring(0,p2);

        return syllables;
    }


    public static void readWord(String word){
        String[]analis=wordAnalis(word);
        for(String s:analis){
            if(searchSyllable(s)==null){
                newSyllable(s,syllables.size());
            }
        }
        for(int i=0;i<analis.length-1;i++){
            int id1=searchSyllable(analis[i]).getID();
            int id2=searchSyllable(analis[i+1]).getID();
            if(id1!=id2) {
                searchConnect(id1, id2).increase();
            }
        }

    }
    public static void learn(String path){
        try {
            Scanner sc = new Scanner(new File(path));

            while (sc.hasNext()) {

                Generator.readWord(sc.next());

            }
            sc.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String generateWord(int length){
        Random r=new Random();
        Syllable[]word=new Syllable[length];
        word[0]=syllables.get(r.nextInt(syllables.size()));
        for(int i=1;i<length;i++){
            int t=0;
            Connect c=null;
            while (r.nextInt(10)>1){
                c=word[i-1].getConnects().get(t%word[i-1].getConnects().size());
                t++;
            }
            if(c.getID1()==word[i-1].getID()){
                word[i]=searchSyllable(c.getID2());
            }
            else {
                word[i]=searchSyllable(c.getID1());
            }
        }
        String result="";
        for (Syllable s:word){
            result = result+s.getSyllable();
        }
        return result;
    }
    public static String generateWord(){
        Random r=new Random();
        int length=r.nextInt(3)+2;
        Syllable[]word=new Syllable[length];
        word[0]=syllables.get(r.nextInt(syllables.size()));
        if(word[0].getConnects().size()==0){
            while(word[0].getConnects().size()==0){
                word[0]=syllables.get(r.nextInt(syllables.size()));
            }
        }
        for(int i=1;i<length;i++){
            for(int x=0;x<word[i-1].getConnects().size();x++){
                for(int j=1;j<word[i-1].getConnects().size();j++){
                    if(word[i-1].getConnects().get(j-1).getLevel()<word[i-1].getConnects().get(j).getLevel()) {
                        Connect c = word[i-1].getConnects().get(j);
                        word[i-1].getConnects().set(j, word[i-1].getConnects().get(j - 1));
                        word[i-1].getConnects().set(j - 1, c);
                    }

                }
            }
            int t=0;
            Connect c=null;
            do{
                c=word[i-1].getConnects().get(t%word[i-1].getConnects().size());

                t++;
            }
            while (r.nextInt(10)>1);


            try {
                if (c.getID1() == word[i - 1].getID()) {
                    word[i] = searchSyllable(c.getID2());
                } else {
                    word[i] = searchSyllable(c.getID1());
                }
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println(c==null);
                System.out.println(c.getID1());
                System.out.println(c.getID2());
                System.out.println(word[i-1].getSyllable());
                System.out.println(word[i-1].getID());
            }
        }
        String result="";
        for (Syllable s:word){
            result = result+s.getSyllable();
        }
        return result;
    }
}
