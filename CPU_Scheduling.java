import java.util.*;

class Process {
    int pid;
    int burstTime;
    int priority;
    int arrivalTime; // Added arrival time
    int originalBurstTime; // Added original burst time for RR
    int waitingTime;
    int turnaroundTime;
    int responseTime;

    public Process(int pid, int burstTime, int priority, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.originalBurstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.responseTime = 0;
    }
}

public class CPU_Scheduling {

    // First-Come, First-Served (FCFS)
    public static void fcfs(ArrayList<Process> processes) {
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;
            process.responseTime = process.waitingTime;
        }
    }

    // Shortest Job First (SJF)
    public static void sjf(ArrayList<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.burstTime));
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;
            process.responseTime = process.waitingTime;
        }
    }

    // Priority Scheduling
    public static void priorityScheduling(ArrayList<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0;
        for (Process process : processes) {
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;
            process.responseTime = process.waitingTime;
        }
    }

    // Round Robin (RR)
    public static void roundRobin(ArrayList<Process> processes, int quantum) {
        Queue<Process> queue = new LinkedList<>(processes);
        int currentTime = 0;
        while (!queue.isEmpty()) {
            Process process = queue.poll();
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }
            if (process.responseTime == 0) {
                process.responseTime = currentTime - process.arrivalTime;
            }
            if (process.burstTime > quantum) {
                currentTime += quantum;
                process.burstTime -= quantum;
                queue.add(process);
            } else {
                currentTime += process.burstTime;
                process.turnaroundTime = currentTime - process.arrivalTime;
                process.waitingTime = process.turnaroundTime - (process.originalBurstTime - process.burstTime);
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 10, 2, 0));
        processes.add(new Process(2, 5, 1, 0));
        processes.add(new Process(3, 8, 3, 0));

        System.out.println("FCFS Scheduling:");
        fcfs(processes);
        calculateMetrics(processes);

        System.out.println("\nSJF Scheduling:");
        sjf(processes);
        calculateMetrics(processes);

        System.out.println("\nPriority Scheduling:");
        priorityScheduling(processes);
        calculateMetrics(processes);

        System.out.println("\nRound Robin Scheduling:");
        roundRobin(processes, 4);
        calculateMetrics(processes);
    }

    public static void calculateMetrics(ArrayList<Process> processes) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int totalResponseTime = 0;
        int n = processes.size();
        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
            totalResponseTime += process.responseTime;
        }
        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);
        System.out.println("Average Response Time: " + (double) totalResponseTime / n);
    }
}
