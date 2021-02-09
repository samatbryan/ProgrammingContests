import java.util.*;

class Solution {
    public int solve(int[] a, int[] b, int[] c) {
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);

        int res = Integer.MAX_VALUE;
        for(int i=0; i<a.length; i++){
            int a_val = a[i];
            
            int left = 0;
            int right = b.length - 1;
            
            while(left <= right){
                int mid = left + (right - left)/2;
                int b_val = b[mid];
                
                
                // another iteration of binary search
                int new_left = 0;
                int new_right = c.length - 1;
                int closest = Integer.MAX_VALUE;
                
                while(new_left <= new_right){
                    int new_mid = new_left + (new_right - new_left)/2;
                    int c_val = c[new_mid];
                    
                    closest = Math.min(closest, Math.abs(c_val - b_val));
                    if(c_val > b_val){
                        new_right = new_mid - 1;
                    }
                    else{
                        new_left = new_mid + 1;
                    }
                }
                
                res = Math.min(res, closest + Math.abs(a_val - b_val));
                
                if(b_val > a_val){
                    right = mid - 1;
                }
                else{
                    left = mid + 1;
                }
            }
        }
        return res;
    }
}

// want i,j,k
// minimum abs(a[i] - b[j]) + abs(b[j] - c[k])
// minimize abs(a[i] - b[j])
// minimze abs(b[j] - c[k])

// what if just minimum abs(a[i] - b[j])
// for each number, binary search