import java.util.*;

public class Disk {
    public static void main(String[] args) {
        int[] requests = {98, 183, 37, 122, 14, 124, 65, 67};
        int head = 53;
        
        System.out.println("FCFS:");
        fcfs(requests, head);
        
        System.out.println("\nSSTF:");
        sstf(requests, head);
        
        System.out.println("\nSCAN:");
        scan(requests, head);
        
        System.out.println("\nC-SCAN:");
        cscan(requests, head);
        
        System.out.println("\nLOOK:");
        look(requests, head);
        
        System.out.println("\nC-LOOK:");
        clook(requests, head);
    }

    public static void fcfs(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;

        for (int i = 0; i < requests.length; i++) {
            curTrack = requests[i];
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
        }

        System.out.println("Total seek operations: " + seekCount);
    }

    public static void sstf(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < requests.length; i++) {
            if (requests[i] < head)
                left.add(requests[i]);
            if (requests[i] > head)
                right.add(requests[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        while (!left.isEmpty() || !right.isEmpty()) {
            if (!left.isEmpty() && !right.isEmpty()) {
                if (Math.abs(left.get(left.size() - 1) - head) < Math.abs(right.get(0) - head)) {
                    curTrack = left.remove(left.size() - 1);
                } else {
                    curTrack = right.remove(0);
                }
            } else if (!left.isEmpty()) {
                curTrack = left.remove(left.size() - 1);
            } else {
                curTrack = right.remove(0);
            }

            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        System.out.println("Total seek operations: " + seekCount);
    }

    public static void scan(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();

        left.add(0); // Add start
        for (int i = 0; i < requests.length; i++) {
            if (requests[i] < head)
                left.add(requests[i]);
            if (requests[i] > head)
                right.add(requests[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = left.size() - 1; i >= 0; i--) {
            curTrack = left.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        for (int i = 0; i < right.size(); i++) {
            curTrack = right.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        System.out.println("Total seek operations: " + seekCount);
    }

    public static void cscan(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();

        left.add(0); // Add start
        right.add(199); // Add end

        for (int i = 0; i < requests.length; i++) {
            if (requests[i] < head)
                left.add(requests[i]);
            if (requests[i] > head)
                right.add(requests[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            curTrack = right.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        head = 0; // Move head to the start of the disk

        for (int i = 0; i < left.size(); i++) {
            curTrack = left.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        System.out.println("Total seek operations: " + seekCount);
    }

    public static void look(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < requests.length; i++) {
            if (requests[i] < head)
                left.add(requests[i]);
            if (requests[i] > head)
                right.add(requests[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = left.size() - 1; i >= 0; i--) {
            curTrack = left.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        for (int i = 0; i < right.size(); i++) {
            curTrack = right.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        System.out.println("Total seek operations: " + seekCount);
    }

    public static void clook(int[] requests, int head) {
        int seekCount = 0;
        int distance, curTrack;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>();

        for (int i = 0; i < requests.length; i++) {
            if (requests[i] < head)
                left.add(requests[i]);
            if (requests[i] > head)
                right.add(requests[i]);
        }

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < right.size(); i++) {
            curTrack = right.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        for (int i = 0; i < left.size(); i++) {
            curTrack = left.get(i);
            distance = Math.abs(curTrack - head);
            seekCount += distance;
            head = curTrack;
            sequence.add(curTrack);
        }

        System.out.println("Total seek operations: " + seekCount);
    }
}
