class Solution {
    int sum(int[][] mat, ArrayList<Integer> a){
        int m = mat.length;
        int sum_a = 0;
        for(int i=0; i<m; i++){
            sum_a += mat[i][a.get(i)];
        }
        return sum_a;
    }
    public int kthSmallest(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        for(int i=0; i<m; i++){
            Arrays.sort(mat[i]);
        }
        PriorityQueue<ArrayList<Integer>> pq = new PriorityQueue<ArrayList<Integer>>(new Comparator<ArrayList<Integer>>(){
            @Override
            public int compare(ArrayList<Integer> a, ArrayList<Integer> b){
                System.out.println("comparing a and b  " + a + " " + b);
                System.out.println("sums " + sum(mat,a) + " " + sum(mat, b));
                return sum(mat, a) - sum(mat,b);
            }
        });
        ArrayList<Integer> start = new ArrayList(m);
        HashSet<ArrayList<Integer>> visited = new HashSet();
        
        for(int i=0; i<m; i++) start.add(0);
        pq.add(start);
        visited.add(start);
        
        while(true){
            ArrayList<Integer> cur = pq.poll();
            System.out.println("cur " + cur);
            k --;
            if(k == 0) return sum(mat, cur);
            
            if(pq.size() >= k) continue;
            
            for(int i=0; i<m; i++){
                ArrayList<Integer> neighbor = (ArrayList<Integer>) cur.clone();
                if(neighbor.get(i) + 1 >= n) continue;
                neighbor.set(i, neighbor.get(i) + 1);
                if(visited.contains(neighbor)) continue;
                visited.add(neighbor);
                pq.add(neighbor);
                System.out.println("neighbor  " + neighbor);
            }
        }                                                
                                                           
    }
    
}