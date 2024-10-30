public class ThreadWriter2 extends Thread {
  public void run() {
    for(int i = 0; i < 10; i++)
    {
        System.out.println("Thread 2: " + i);
    }
  }
}