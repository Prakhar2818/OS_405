import java.util.Arrays;

class Process {
    int id;
    int burstTime;
    int waitingTime;
    int turnaroundTime;

    Process(int id, int burstTime) {
        this.id = id;
        this.burstTime = burstTime;
    }
}

public class SJFScheduling {

    // Function to swap two integers in an array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Function to sort processes by burst time
    public static void sortProcessByBurst(int n, int[] burst, int[] process) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (burst[j] > burst[j + 1]) {
                    swap(burst, j, j + 1);
                    swap(process, j, j + 1);
                }
            }
        }
    }

    // Function to calculate waiting times and turnaround times
    public static void calculateTimes(int[] processes, int n, int[] burst_time) {
        int[] wait_time = new int[n];
        int[] tat = new int[n];
        int total_wt = 0, total_tat = 0;

        // Sort processes by burst time
        sortProcessByBurst(n, burst_time, processes);

        // Calculate waiting times and turn-around times
        wait_time[0] = 0;
        tat[0] = burst_time[0];

        for (int i = 1; i < n; i++) {
            wait_time[i] = wait_time[i - 1] + burst_time[i - 1];
            tat[i] = wait_time[i] + burst_time[i];
        }

        // Display results
        System.out.println("Processes Burst time Waiting time Turn around time");

        for (int i = 0; i < n; i++) {
            total_wt += wait_time[i];
            total_tat += tat[i];
            System.out.println((i + 1) + "\t\t" + burst_time[i] + "\t\t" + wait_time[i] + "\t\t" + tat[i]);
        }

        System.out.printf("Average waiting time = %.2f\n", (float) total_wt / n);
        System.out.printf("Average turn around time = %.2f\n", (float) total_tat / n);
    }

    public static void main(String[] args) {
        int[] processes = {1, 2, 3, 4};
        int n = processes.length;
        int[] burst_time = {6, 8, 7, 3};

        calculateTimes(processes, n, burst_time);
    }
}
