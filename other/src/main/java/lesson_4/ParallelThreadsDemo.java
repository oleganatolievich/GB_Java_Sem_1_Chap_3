package lesson_4;

public class ParallelThreadsDemo {

    //монитор
    private static final Object locker = new Object();

    //поставил себе Core i5 10600, OEM, посмотрим сможет ли он выдержать такое дело
    private static final int repeatsAmount = 100;
    //да здравствует волшебный мир UTF-8
    private static final int lettersAmount = 100;
    private static final char firstChar = 'A';
    private static char lastChar;
    private static volatile char curChar = firstChar;

    public static void main(String[] args) {
        Thread[] threadPool = new Thread[lettersAmount];
        for (int letterIndex = 0; letterIndex < lettersAmount; letterIndex++) {
            final char targetChar = (char) (firstChar + letterIndex);
            lastChar = targetChar;
            //1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
            // (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
            threadPool[letterIndex] = new Thread(() -> printLetter(targetChar));
        }
        for (Thread curThread: threadPool) curThread.start();
    }

    public static void printLetter(char letter) {
        synchronized (locker) {
            try {
                for (int i = 0; i < repeatsAmount; i++) {
                    while (curChar != letter) locker.wait();
                    System.out.printf((curChar == lastChar) ? "%s%n" : "%s", letter);
                    curChar = curChar == lastChar ? firstChar : (char) (letter + 1);
                    locker.notifyAll();
                }
            } catch (InterruptedException e) {
                System.out.println("Te gustan los errores, no? Here you go, amigo mio:");
                e.printStackTrace();
            }
        }
    }
}

//2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.
//Не сделал, так как пришлось бы прокидывать ссылки на MessageSocketThread во все методы,
//и не попереключаться между потоками, поэтому очень много пришлось бы переписывать, и по-сути костылять.