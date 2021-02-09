import java.util.*;

class VirtualArray {
    TreeMap<Integer, Integer> intervals;

    public VirtualArray() {
        this.intervals = new TreeMap();
    }

    public void set(int start, int end) {
        Integer floorkey = intervals.floorKey(start);
        if (floorkey != null && intervals.get(floorkey) >= end) {
            start = Math.min(start, floorkey);
            end = Math.max(end, intervals.get(floorkey));
            intervals.remove(floorkey);
        }

        int merge_start = start;

        // merge intervals
        while (intervals.ceilingKey(merge_start) != null) {
            int k = intervals.ceilingKey(merge_start);
            if (k > end)
                break;
            end = Math.max(end, intervals.get(k));
            intervals.remove(k);
            merge_start = k;
        }

        intervals.put(start, end);
    }

    public boolean get(int idx) {
        Integer key = intervals.floorKey(idx);
        if (key == null)
            return false;

        return intervals.get(key) >= idx;
    }
}