import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: zhifang.xu
 * Date: 2020/1/25
 * Time: 4:01 PM
 * Description: No Description
 *              56
 *
 *    23                          98
 *11        33          66
 *      32         54      75
 *             44             76
 *               53
 */
public class BinaryTreeNode {

    public static LinkedBlockingQueue<BTree> queue = new LinkedBlockingQueue();
    public static Set<BTree> set = new HashSet <>();
    public static void main( String[] args ) {
        BTree tree = new BTree(56);
        tree.root =tree;

        tree.insert(23);
        tree.insert(98);
        tree.insert(33);
        tree.insert(54);
        tree.insert(44);
        tree.insert(66);
        tree.insert(53);
        tree.insert(75);
        tree.insert(11);
        tree.insert(32);
        tree.insert(76);
        List<Integer> result = new ArrayList <>();
        middlerBinary(result,tree);
        System.out.println(result);
        List<Integer> result2 = new ArrayList <>();
        preBinary(result2,tree);
        System.out.println(result2);

        List<Integer> result3 = new ArrayList <>();
        postBinary(result3,tree);
        System.out.println(result3);

        BTree common = lowestCommonAncesstorMorN(tree,11,53);

        System.out.println(common.data);


        BFS(tree.root);

        System.out.println("===深度遍历===");
        tree.root.setLevel(0);
        DFS(tree.root);

    }

    /***
     * 中序遍历
     * @return
     */
    public  static void middlerBinary(List<Integer> list,BTree tree){
        BTree left = tree.left;
        BTree right = tree.right;

        int data = tree.data;
        if(left!=null) middlerBinary(list,left);
        list.add(data);
        if(right!=null) middlerBinary(list,right);
    }

    /***
     * 前序遍历
     * @return
     */
    public  static void preBinary(List<Integer> list,BTree tree){
        BTree left = tree.left;
        BTree right = tree.right;

        int data = tree.data;
        list.add(data);
        if(left!=null) preBinary(list,left);

        if(right!=null) preBinary(list,right);
    }

    /***
     * 后序遍历
     * @return
     */
    public  static void postBinary(List<Integer> list,BTree tree){
        BTree left = tree.left;
        BTree right = tree.right;

        int data = tree.data;

        if(left!=null) postBinary(list,left);

        if(right!=null) postBinary(list,right);
        list.add(data);
    }

    /***
     * 从树中查找最小的公共祖先节点
     * @param tree
     * @param m
     * @param n
     * @return
     */
    public static Integer lowestCommonAncesstor(BTree tree,int m,int n){

        if((m-tree.data)*(n-tree.data)<=0){
            return tree.data;
        }
        if(m-tree.data<0){
            return lowestCommonAncesstor(tree.left,m,n);
        }else{
            return lowestCommonAncesstor(tree.right,m,n);
        }
    }

    /**
     * 从树中查找最小的公共祖先节点
     * @param tree
     * @param m
     * @param n
     * @return
     */
    public static BTree lowestCommonAncesstorMorN(BTree tree,int m,int n){
        if(tree==null||tree.data==m||tree.data==n){
            return tree;
        }
        //从左子树里找m,n
        BTree l = lowestCommonAncesstorMorN(tree.left,m,n);
        //从右子树里找m,n
        BTree r = lowestCommonAncesstorMorN(tree.right,m,n);
        if(l==null){//那表示都在右子树里
            return r;
        }else if(r==null){//表示都在左子树里
            return l;
        }else{//l!=null&&r!=null 则表示在tree的树里左右各一个
            return tree;
        }
//        return l==null?r:r==null?l:tree;
    }

    /***
     * 广度优先遍历
     * @param tree
     */
    public static void BFS(BTree tree){
        System.out.println("===广度遍历===");
        tree.setLevel(0);
        queue.offer(tree);

        while(!queue.isEmpty()){
            BTree bTree = null;
            try {
                bTree = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            set.add(bTree);
            System.out.println("level="+bTree.level+",data="+bTree.data );
            BTree left = bTree.left;

            BTree right = bTree.right;
            if(left!=null){
                left.setLevel(bTree.level+1);
                queue.offer(left);
            }
            if(right!=null){
                right.setLevel(bTree.level+1);
                queue.offer(right);
            }

        }
    }

    /***
     * 深度优先遍历
     * @param tree
     */
    public static void DFS(BTree tree){


        int level= tree.level;
        BTree left = tree.left;
        System.out.println("level="+tree.level+",data="+tree.data );

        if(left!=null){
            DFS(left);
            left.setLevel(level+1);
        }
        BTree right =tree.right;
        if(right!=null){
            DFS(right);
            right.setLevel(level+1);
        }


    }
}
