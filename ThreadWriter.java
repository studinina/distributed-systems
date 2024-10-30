public class ThreadWriter extends Thread {
    public void run() {
        for(int i = 0; i < 10; i++)
        {
            System.out.println("Thread 1: " + i);
            try {
                // Kurze Verzögerung, um das Interleaving zu beobachten
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  }
