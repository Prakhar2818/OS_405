import java.util.Arrays;

public class FCFS {

    public static void findWaitingTime(int[] processes, int n, int[] burstTime, int[] waitTime) {
        waitTime[0] = 0;

        for (int i = 1; i < n; i++) {
            waitTime[i] = burstTime[i - 1] + waitTime[i - 1];
        }
    }

    public static void findTurnAroundTime(int[] processes, int n, int[] burstTime, int[] waitTime, int[] tat) {
        for (int i = 0; i < n; i++) {
            tat[i] = burstTime[i] + waitTime[i];
        }
    }

    public static void findAvgTime(int[] processes, int n, int[] burstTime) {
        int[] waitTime = new int[n];
        int[] tat = new int[n];
        float totalWaitTime = 0, totalTat = 0;

        findWaitingTime(processes, n, burstTime, waitTime);
        findTurnAroundTime(processes, n, burstTime, waitTime, tat);

        System.out.println("Processes\tBurst time\tWaiting time\tTurn around time");

        for (int i = 0; i < n; i++) {
            totalWaitTime += waitTime[i];
            totalTat += tat[i];

            System.out.println(processes[i] + "\t\t" + burstTime[i] + "\t\t" + waitTime[i] + "\t\t" + tat[i]);
        }

        System.out.println("Average waiting time = " + totalWaitTime / n);
        System.out.println("Average turn around time = " + totalTat / n);
    }

    public static void main(String[] args) {
        int[] processes = {1, 2, 3, 4};
        int n = processes.length;
        int[] burstTime = {21, 3, 6, 2};

        findAvgTime(processes, n, burstTime);
    }
}
