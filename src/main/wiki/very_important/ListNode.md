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

Полноценный пример:
```java
// #25 https://leetcode.com/problems/reverse-nodes-in-k-group/
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

