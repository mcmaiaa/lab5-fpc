import java.util.ArrayList;
import java.util.List;

class Buffer {
    private final List<Integer> data = new ArrayList<>();
    private int maxItems = 10;
    
    public synchronized void put(int value) {
        while (data.size() >= maxItems) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data.add(value);
        System.out.println("Inserted: " + value + " | Buffer size: " + data.size());
        notify();
    }
    
    public synchronized int remove() {
        while (data.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int value = data.remove(0);
        System.out.println("Removed: " + value + " | Buffer size: " + data.size());
        notify();
        return value;
    }
}
