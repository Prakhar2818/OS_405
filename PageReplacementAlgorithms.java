import java.util.*;

class Page {
    int pageNumber;
    int arrivalTime;

    public Page(int pageNumber, int arrivalTime) {
        this.pageNumber = pageNumber;
        this.arrivalTime = arrivalTime;
    }
}

public class PageReplacementAlgorithms {
    public static void main(String[] args) {
        int[] pageReferences = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2};
        int frameSize = 3;

        System.out.println("FIFO:");
        fifo(pageReferences, frameSize);
        System.out.println("\nLRU:");
        lru(pageReferences, frameSize);
        System.out.println("\nOptimal:");
        optimal(pageReferences, frameSize);
    }

    public static void fifo(int[] pageReferences, int frameSize) {
        LinkedList<Integer> fifoQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();
        int pageFaults = 0;

        for (int pageNumber : pageReferences) {
            if (!frameSet.contains(pageNumber)) {
                pageFaults++;
                if (fifoQueue.size() == frameSize) {
                    int removedPage = fifoQueue.removeFirst();
                    frameSet.remove(removedPage);
                }
                fifoQueue.addLast(pageNumber);
                frameSet.add(pageNumber);
            }
            System.out.println("Page reference: " + pageNumber + ", Page faults: " + pageFaults);
        }
    }

    public static void lru(int[] pageReferences, int frameSize) {
        LinkedHashMap<Integer, Integer> lruMap = new LinkedHashMap<>(frameSize, 0.75f, true);
        int pageFaults = 0;

        for (int pageNumber : pageReferences) {
            if (!lruMap.containsKey(pageNumber)) {
                pageFaults++;
                if (lruMap.size() == frameSize) {
                    Iterator<Map.Entry<Integer, Integer>> iterator = lruMap.entrySet().iterator();
                    iterator.next();
                    iterator.remove();
                }
                lruMap.put(pageNumber, 0);
            }
            for (Map.Entry<Integer, Integer> entry : lruMap.entrySet()) {
                entry.setValue(entry.getValue() + 1);
            }
            lruMap.put(pageNumber, 0);
            System.out.println("Page reference: " + pageNumber + ", Page faults: " + pageFaults);
        }
    }

    public static void optimal(int[] pageReferences, int frameSize) {
        List<Page> pageList = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < pageReferences.length; i++) {
            pageList.add(new Page(pageReferences[i], i));
        }

        List<Integer> frames = new ArrayList<>(frameSize);
        Map<Integer, Integer> frameIndexMap = new HashMap<>();

        for (int i = 0; i < pageList.size(); i++) {
            Page currentPage = pageList.get(i);
            if (!frames.contains(currentPage.pageNumber)) {
                pageFaults++;
                if (frames.size() < frameSize) {
                    frames.add(currentPage.pageNumber);
                    frameIndexMap.put(currentPage.pageNumber, frames.size() - 1);
                } else {
                    int index = findOptimalReplacementIndex(pageList, frames, i);
                    frameIndexMap.remove(frames.get(index));
                    frames.set(index, currentPage.pageNumber);
                    frameIndexMap.put(currentPage.pageNumber, index);
                }
            }
            System.out.println("Page reference: " + currentPage.pageNumber + ", Page faults: " + pageFaults);
        }
    }

    private static int findOptimalReplacementIndex(List<Page> pageList, List<Integer> frames, int currentIndex) {
        int index = -1;
        int farthest = currentIndex;
        for (int i = 0; i < frames.size(); i++) {
            int j;
            for (j = currentIndex; j < pageList.size(); j++) {
                if (frames.get(i) == pageList.get(j).pageNumber) {
                    if (j > farthest) {
                        farthest = j;
                        index = i;
                    }
                    break;
                }
            }
            if (j == pageList.size()) {
                return i;
            }
        }
        return index == -1 ? 0 : index;
    }
}
