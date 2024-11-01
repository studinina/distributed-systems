public class CounterTest {
    //  InterruptedException signalisiert, dass ein Thread unterbrochen wurde, 
    //während er auf eine bestimmte Aktion wartet, schläft oder blockiert ist. 
    // In unserem Beispiel tritt die InterruptedException auf, wenn wir die Methode join() verwenden
    // Onhe InterruptedException und mit join kann man nicht kompilieren
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        CounterSynchronized counterSynchronized = new CounterSynchronized();

        // Erster Thread für die Inkrementierung
        Thread incrementThread = new Thread() {
            public void run() {
                for (int i = 0; i < 4000; i++) {
                    counter.increment();
                    counterSynchronized.increment();
                    
                }
            }
        };

        // Zweiter Thread für die Dekrementierung
        Thread decrementThread = new Thread() {
            public void run() {
                for (int i = 0; i < 4000; i++) {
                    counter.decrement();
                    counterSynchronized.decrement();
                }
            }
        };

        // Starte die Threads
        incrementThread.start();
        decrementThread.start();

        // Warte darauf, dass beide Threads beendet sind
        incrementThread.join();
        decrementThread.join();

        // Ausgabe des Zählerwertes
        System.out.println("Final counter value: " + counter.value());
        System.out.println("Final counterSynchronited value: " + counterSynchronized.value());
    }
}