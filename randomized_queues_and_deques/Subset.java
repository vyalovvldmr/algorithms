public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        String str;

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            queue.enqueue(str);
        }

        for (int i = 1; i <= k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}