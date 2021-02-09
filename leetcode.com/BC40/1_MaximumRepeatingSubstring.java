class Solution {
    public int maxRepeating(String sequence, String word) {
        int res = 0;
        String new_word = word;
        while (sequence.indexOf(new_word) != -1) {
            res++;
            new_word += word;
        }
        return res;
    }
}