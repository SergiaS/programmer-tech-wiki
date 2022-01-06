# ListNode
Объект, который имеет только одну ссылку на следующий `ListNode`.

```java
public static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                val +
                ", -> " + next +
                '}';
    }
}
```
```text
ListNode{2, -> ListNode{1, -> ListNode{4, -> ListNode{3, -> ListNode{5, -> null}}}}}
```

***

## Examples

### [LeetCode - #25 Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/)

Перевернуть все ноды до _k_.

![img](https://assets.leetcode.com/uploads/2020/10/03/reverse_ex1.jpg)

```java
public ListNode reverseKGroup(ListNode head, int k) {
    Deque<ListNode> stack = new LinkedList<>();
    int counter = 0;
    ListNode nodes = new ListNode(0);
    ListNode root = nodes;
    while (head != null) {
        ListNode headCopy = head;
        stack.add(headCopy);
        counter++;
        head = head.next;
        if (counter == k) {
            for (int i = 0; i < counter; i++) {
                ListNode pollLast = stack.pollLast();
                pollLast.next = null;
                nodes.next = pollLast;
                nodes = nodes.next;
            }
        counter = 0;
        }
    }

    for (int i = 0; i < counter; i++) {
        ListNode pollFirst = stack.pollFirst();
        pollFirst.next = null;
        nodes.next = pollFirst;
        nodes = nodes.next;
    }
    return root.next;
}
```

***

### [LeetCode - #86 Partition List](https://leetcode.com/problems/partition-list/)

Пример с разделением нодов по условию нода (>= x || < x)

![img](https://assets.leetcode.com/uploads/2021/01/04/partition.jpg)

```java
public ListNode partition(ListNode head, int x) {
    ListNode l = new ListNode(), r = new ListNode();
    ListNode rootL = l, rootR = r;

    while (head != null) {
        if (head.val >= x) {
            r.next = new ListNode(head.val);
            r = r.next;
        } else {
            l.next = new ListNode(head.val);
            l = l.next;
        }
        head = head.next;
    }
    l.next = rootR.next;
    return rootL.next;
}
```
В примере входящий `ListNode` будет поделен на два `ListNode` (в одном то что `< x`, а в другом то что `>= x`) путём сравнения каждого значений с `x`. 
При этом необходимо хранить ссылки на первые элементы (`root`). Нужно будет склеить два листа - последний элемент `ListNode l` будет ссылаться на next-элемент, который будет выступать первый элемент `ListNode r`.
И в самом конце передаем следующий элемент от главного `ListNode l`.

***

### [LeetCode - #206 Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/)

Пример с простым переворотом листа

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg)
```java
public ListNode reverseList(ListNode head) {
    ListNode reverseLN = new ListNode(0);
    ListNode res = reverseLN;

    Deque<ListNode> deq = new LinkedList<>();
    while (head != null) {
        deq.add(head);
        head = head.next;
    }

    while (!deq.isEmpty()) {
        ListNode pollListNode = deq.pollLast();
        pollListNode.next = null;

        reverseLN.next = pollListNode;
        reverseLN = reverseLN.next;
    }
    return res.next;
}
```
