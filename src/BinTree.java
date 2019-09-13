import java.util.ArrayList;

public class BinTree {
    private Connect root;
    private BinTree left=null;
    private BinTree right=null;

    public void add(Connect c){
        if(c.getLevel()<root.getLevel()){
            if (left==null){
                left=new BinTree(c);
            }
            else {
                left.add(c);
            }
        }
        else {
            if (right==null){
                right=new BinTree(c);
            }
            else {
                right.add(c);
            }
        }
    }
    public BinTree(Connect root){
        this.root=root;
    }
    public void build(ArrayList<Connect> c){

        if(left!=null)left.build(c);
        c.add(root);
        if(right!=null)right.build(c);
    }

    public static ArrayList<Connect> sort(ArrayList<Connect> a){
        System.out.println(a.size());
        BinTree tree=new BinTree(a.get(0));
        for(int i=1;i<a.size();i++)tree.add(a.get(i));
        ArrayList<Connect>b=new ArrayList<>();
        tree.build(b);
        return b;
    }

    public static void main(String[] args) {
        ArrayList<Connect>a=new ArrayList<>();
        a.add(new Connect(0,0,50));
        a.add(new Connect(0,0,6));
        a.add(new Connect(0,0,87));
        a.add(new Connect(0,0,45));
        a.add(new Connect(0,0,30));
        a.add(new Connect(0,0,66));
        a.add(new Connect(0,0,2));
        a.add(new Connect(0,0,1));
        ArrayList<Connect>b;
        b=sort(a);
        for(Connect c:b) System.out.println(c.getLevel());
    }
}
