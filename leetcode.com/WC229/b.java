class Solution {
    public int[] minOperations(String boxes) {
        char[] b = boxes.toCharArray();
        int n = b.length;
        int[] res = new int[n];
        int[] prefix = new int[n]; // place balls < i to i
        int[] suffix = new int[n]; // place balls > i to i

        int ball_count = 0;
        int sum = 0;
        if (b[0] == '1') {
            ball_count++;
        }
        for (int i = 1; i < n; i++) {
            sum += ball_count;

            if (b[i] == '1')
                ball_count++;
            prefix[i] = sum;
        }

        ball_count = 0;
        sum = 0;
        if (b[n - 1] == '1')
            ball_count++;
        for (int i = n - 2; i >= 0; i--) {
            sum += ball_count;
            suffix[i] = sum;
            if(b[i] == '1'){
                ball_count ++;
            }
        }
        for(int i=0; i<n; i++){
            res[i] = prefix[i] + suffix[i];
        }
        return res;

    }
}