/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode
 * next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val,
 * ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode ath = list1;
        ListNode bth = list1;
        for (int i = 1; i <= a - 1; i++) {
            ath = ath.next;
        }
        for (int i = 1; i <= b + 1; i++) {
            bth = bth.next;
        }

        ath.next = list2;
        while (ath.next != null) {
            ath = ath.next;
        }

        ath.next = bth;
        return list1;
    }

}