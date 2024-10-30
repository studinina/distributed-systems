public class Task1 {
    public static void main(String[] args) {
        // Print "Hello, World!" to the console
        System.out.println("Hello, World!");
        ThreadWriter thread1 = new ThreadWriter();
        ThreadWriter2 thread2 = new ThreadWriter2();
        thread1.start();
        thread2.start();
    }
}