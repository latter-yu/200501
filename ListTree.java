import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListTree {
    public class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
   }

    private int index = 0;//preorder 长度和 index 相同
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        //根据一棵树的前序遍历和先序遍历构建一棵树

        //preorder 前序遍历
        //inorder 中序遍历
        index = 0;
        return buildTreeHelper(preorder, inorder, 0, inorder.length);
        //为了更好地描述子树
        //加入一对参数表示当前子树对应的中序遍历结果的区间 [0, inorder.length)
    }
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int inorderLeft, int inorderRight) {
        if (inorderLeft >= inorderRight) {
            //中序区间为空区间，当前子树为空树
            return null;
        }
        if(index >= preorder.length) {
            //先序中所有元素都访问完毕了
            return  null;
        }
        //根据 index 取出当前树的根节点的值，并构造根节点
        TreeNode newNode = new TreeNode(preorder[index]);
        //根据根节点，在中序结果中，找出左子树和右子树的范围
        //确定位置
        //左子树对应的中序区间：[inorderLeft, pos)
        //右子树对应的中序区间：[pos + 1, inorderRight)
        int pos = find(inorder, inorderLeft, inorderRight, newNode.val);
        index++;
        newNode.left = buildTreeHelper(preorder, inorder, inorderLeft, pos);
        newNode.right = buildTreeHelper(preorder, inorder, pos+1, inorderRight);
        return newNode;
    }
    private int find(int[] inorder, int inorderLeft, int inorderRight, int val) {
        for(int i = inorderLeft; i < inorderRight; i++) {
            if(inorder[i] == val) {
                return i;
            }
        }
        return -1;
    }

    private StringBuilder s = new StringBuilder();
    //表示最终结果
    //StringBuilder 线程安全（不涉及多线程时, 优先使用）  StringBuffer 线程不安全
    //String 不可修改对象，要想修改 String 只能创建新对象
    public String tree2str(TreeNode t) {
        if(t == null) {
            return "";
        }
        helper(t);//完成递归先序辅助方法
        s.deleteCharAt(0);//删除最外层括号
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }
    private void helper(TreeNode root) {
        if(root == null) {
            return;
        }
        s.append("(");
        s.append(root.val);
        helper(root.left);
        if(root.left == null && root.right != null) {
            s.append("()");
        }
        helper(root.right);
        s.append(")");
    }

    public void preorderTraversal(TreeNode root) {
        //给定一个二叉树，返回它的 前序 遍历（非递归 写 递归 要用 栈）

        //1.创建一个栈。初始情况下，把根节点入栈
        //2.进入循环
        // a.取栈顶元素（出栈）
        // b.访问该元素
        // c.若该元素的右子树不为空，就入栈;
        //   若该元素的左子树不为空，也入栈;
        //   当栈为空时，遍历完成

        if(root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();//创建一个栈
        stack.push(root);//根节点
        while(!stack.isEmpty()) {
            TreeNode top = stack.pop();//取出栈顶元素
            System.out.print(top.val + " ");//输出栈顶元素
            if(top.right != null) {
                stack.push(top.right);
            }
            if(top.left != null) {
                stack.push(top.left);
            }
        }
    }
    //用 List 写非递归的先序遍历
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();//创建一个栈
        stack.push(root);//根节点
        while(!stack.isEmpty()) {
            TreeNode top = stack.pop();//取出栈顶元素
            //System.out.print(top.val + " ");//输出栈顶元素
            list.add(Integer.valueOf(top.val));
            if(top.right != null) {
                stack.push(top.right);
            }
            if(top.left != null) {
                stack.push(top.left);
            }
        }
        return list;
    }
}
