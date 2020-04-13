package com.tako.test;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
    /**
     *       1
     *    2     3
     *  4     5   6
     *       7 8
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.left = node5;
        node3.right = node6;
        node5.left = node7;
        node5.right = node8;

        System.out.println("DLR:");
        node1.printDLR();
        String serializationNode1 = serializationByDLR(node1);
        System.out.println("序列化:" + serializationNode1);

        TreeNode treeNode = deserializationByDLR(serializationNode1);
        System.out.println("反序列化:");
        treeNode.printDLR();

        System.out.println("LDR:");
        node1.printLDR();
        System.out.println("LRD:");
        node1.printLRD();

        System.out.println("广度遍历:");
        //隔层换行
        test(node1);
        System.out.println("广度遍历2:");
        test2(node1);
    }

    public static void test(TreeNode root) {
        if (root == null) {
            System.out.println("Tree is NULL");
            return;
        }
        Queue<TreeNode> queue = new LinkedList();
        queue.offer(root);
        int currentCount = 1;
        int nextCount = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            currentCount--;
            if (node.left != null) {
                queue.offer(node.left);
                nextCount++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextCount++;
            }
            if (currentCount == 0) {
                System.out.print(node.val + "\n");
                currentCount = nextCount;
                nextCount = 0;
            } else {
                System.out.print(node.val + "\t");
            }
        }
    }

    public static void test2(TreeNode root) {
        if (root == null) {
            System.out.println("TreeNode is Null");
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode last = root;
        TreeNode nLast = null;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node.left != null) {
                queue.offer(node.left);
                nLast = node.left;
            }

            if (node.right != null) {
                queue.offer(node.right);
                nLast = node.right;
            }

            if (node == last) {
                System.out.print(node.val + "\n");
                last = nLast;
            } else {
                System.out.print(node.val + "\t");
            }
        }
    }

    private int val;

    private TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
    }

    public void printDLR() {
        System.out.println(val);
        if (left != null) left.printDLR();
        if (right != null) right.printDLR();
    }

    public void printLDR() {
        if (left != null) left.printLDR();
        System.out.println(val);
        if (right != null) right.printLDR();
    }

    public void printLRD() {
        if (left != null) left.printLRD();
        if (right != null) right.printLRD();
        System.out.println(val);
    }

    public static String serializationByDLR(TreeNode root) {
        if (root == null) return "#!";
        String str = root.val + "!";
        str += serializationByDLR(root.left);
        str += serializationByDLR(root.right);
        return str;
    }

    private static int i;

    public static TreeNode deserializationByDLR(String str) {
        i = 0;
        String[] strings = str.split("!");
        return getNode(strings);
    }

    /**
     * 1!2!4!#!#!#!3!5!7!#!#!8!#!#!6!#!#!
     */
    private static TreeNode getNode (String[] str) {
        if (str[i].equals("#")) return null;
        TreeNode node = new TreeNode(Integer.valueOf(str[i]));
        i++;
        node.left = getNode(str);
        i++;
        node.right = getNode(str);
        return node;
    }
}
