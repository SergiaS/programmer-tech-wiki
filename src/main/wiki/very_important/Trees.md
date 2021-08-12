# Trees / Деревья
Binary Search Tree (BST). 

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

