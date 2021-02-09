import java.util.*;

class PlaylistQueue {
    static class Node {
        Node next;
        Node prev;
        String song;

        Node(String song) {
            this.song = song;
            this.next = null;
            this.prev = null;
        }
    }

    Node head;
    HashMap<Integer, Node> hm;

    public void addSong(String song) {
        Node new_last = new Node(song);
        Node prev_last = head.prev;
        prev_last.next = new_last;
        new_last.prev = prev_last;
        new_last.next = head;
        head.prev = new_last;
        hm.put(hm.size() - 1, new_last);
    }

    public void remove(int idx) {
        
        Node last = head.prev;
        Node cur = hm.get(idx);
        String tmp = last.song;
        last.song = cur.song;
        cur.song = tmp;

        hm.put(idx, )
    }

    public PlaylistQueue(String[] songs) {
        head = new Node("lol");
        head.next = head;
        head.prev = head;
        hm = new HashMap();
        hm.put(-1, head);
        for (String s : songs) {
            addSong(s);
        }
    }

    public String play(int i) {
        remove(i);
    }
}