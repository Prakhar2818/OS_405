import java.util.Arrays;

public class BankersAlgorithm {
    public static void main(String[] args) {
        int n = 5; // Number of processes
        int m = 3; // Number of resources
        
        int[][] alloc = { 
            {0, 1, 0}, // P0 // Allocation Matrix
            {2, 0, 0}, // P1
            {3, 0, 2}, // P2
            {2, 1, 1}, // P3
            {0, 0, 2}  // P4
        };

        int[][] max = { 
            {7, 5, 3}, // P0 // Maximum Demand Matrix
            {3, 2, 2}, // P1
            {9, 0, 2}, // P2
            {2, 2, 2}, // P3
            {4, 3, 3}  // P4
        };

        int[] avail = {3, 3, 2}; // Available Resources

        int[] f = new int[n];
        Arrays.fill(f, 0);
        int[] ans = new int[n];
        int ind = 0;
        int[][] need = new int[n][m];

        // Calculate the need matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }

        int y = 0;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (f[i] == 0) {
                    int flag = 0;
                    for (int j = 0; j < m; j++) {
                        if (need[i][j] > avail[j]) {
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 0) {
                        ans[ind++] = i;
                        for (y = 0; y < m; y++) {
                            avail[y] += alloc[i][y];
                        }
                        f[i] = 1;
                    }
                }
            }
        }

        System.out.println("Following is the SAFE Sequence");
        for (int i = 0; i < n - 1; i++) {
            System.out.print(" P" + ans[i] + " ->");
        }
        System.out.println(" P" + ans[n - 1]);
    }
}
