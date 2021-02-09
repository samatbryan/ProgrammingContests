/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode first = null;
        ListNode last = null;

        ListNode kth = head;
        for (int i = 0; i < k - 1; i++) {
            kth = kth.next;
        }

        first = kth;
        ListNode last_kth = head;
        while (kth.next != null) {
            kth = kth.next;
            last_kth = last_kth.next;
        }

        int tmp = first.val;
        first.val = last_kth.val;
        last_kth.val = tmp;
        return head;
    }

}