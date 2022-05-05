# Trees / Деревья

## Binary Search Tree (BST)
A binary search tree is a tree that satisfies these constraints:
* The left subtree of a node contains only nodes with keys less than the node's key.
* The right subtree of a node contains only nodes with keys greater than the node's key.
* Both the left and right subtrees must also be binary search trees.


## TreeNode
Пример последовательного создания (заполнения) дерева по значениям из массива типа `Integer` включая `null` значения:
```java
Integer[] ints = {3, 2, 1, 6, null, 0, 5, null};

TreeNode root = new TreeNode(ints[0]);
Deque<TreeNode> q = new LinkedList<>();
q.add(root);

int idx = 1;
while (!q.isEmpty()){
    TreeNode tn = q.poll();
    if (tn.left == null) {
        Integer anInt = ints[idx++];
        TreeNode left = anInt != null ? new TreeNode(anInt) : null;
        q.add(left);
        tn.left = left;
    }
    
    if (idx >= ints.length) {
        break;
    }
    
    if (tn.right == null) {
        Integer anInt = ints[idx++];
        TreeNode right = anInt != null ? new TreeNode(anInt) : null;
        q.add(right);
        tn.right = right;
    }
    q.add(tn);
}
System.out.println(root);
```
В зависимости от алгоритма необходимо использовать очередь или стек.

***

Пример создания дерева с разбиением на подмассивы от максимального числа:
1. Дан массив: `[3, 2, 1, 6, 0, 5]`, где `6` самое большое, слева подмассив `[3, 2, 1]`, справа - `[0, 5]`.
2. Далее у массива `[3, 2, 1]`, где `3` самое большое, и слева будет подмассив `[]` (т.е. будет `null`), а справа - `[2, 1]`. И так далее.

Алгоритм основан на передаче индекса, а не числа или под массивов...
```java
// from LeetCode task: 654. Maximum Binary Tree
// https://leetcode.com/problems/maximum-binary-tree/
public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

public static TreeNode helper(int[] nums, int start, int end) {
    if (from > to) {
        return null;
    }
    if (from == to) {
        return new TreeNode(nums[from]);
    }

    int maxIdx = from;
    for (int i = from; i <= to; i++) {
        if (nums[maxIdx] < nums[i]) {
            maxIdx = i;
        }
    }
    TreeNode main = new TreeNode(nums[maxIdx]);
        main.left = helper(nums, from, maxIdx-1);
        main.right = helper(nums, maxIdx+1, to);
        return main;
//    return new TreeNode(nums[maxIdx], helper(nums, from, maxIdx - 1), helper(nums, maxIdx + 1, to));
}
```

***

Пример слияния (merge) деревьев:
```java
// from LeetCode task: 617. Merge Two Binary Trees
// https://leetcode.com/problems/merge-two-binary-trees/
public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
    if (root2 == null) return root1;
    if (root1 == null) return root2;
    root1.val = root1.val + root2.val;

    root1.left = mergeTrees(root1.left, root2.left);
    root1.right = mergeTrees(root1.right, root2.right);

    return root1;
}
```

***

[LeetCode - 226. Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/discuss/1540810/Easy-Java-Solution-or-0-ms-or-Beats-100)
When dealing with trees (and most importantly recursion), the first thin you need to do is to apply one of the most important algorithmic principles: **divide and conquer**.
Usually what you want to do is to divide the problem in actions that you need to apply to current nodes, supposing that you did the same to children nodes.

In our example if we want to invert the tree:
* We first want to invert right and left node for our current root node;
* Then we need to do the same for left and right nodes of our root left and right nodes (and repeat the same until we reach a `null` node).

So if we simplify the code snippet below, it becomes

```java
// We do a standard java permutation, to invert nodes at current level (if current node is not null).
TreeNode left = root.left;
root.left = root.right;
root.right = left;

// Then we invert children nodes for each of inverted nodes.
// So here we simply call our same method for recursion, since it soul purpose is to 
// invert left and right children for a specefic node then recurse to lower levels.
root.left = invertTree(root.left); // here we can simply call the method without affecting it to root.left, but I've put it for clarity
root.right = invertTree(root.right); // same as for root.left
```

Which translates to the following instructions:
* If current node is not null - invert its left and right nodes;
* Do the same for its left node and its right node.

```java
// full example
public TreeNode invertTree(TreeNode root) {
    if(root == null) return null;
    
    TreeNode left = root.left;
    root.left = invertTree(root.right);
    root.right = invertTree(left);
    
    return root;
}
```
