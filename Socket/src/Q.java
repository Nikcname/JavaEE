public class Q {

    String n;

    synchronized String get(){
        try{
                wait();
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
        return n;
    }

    synchronized void put(String n){

        this.n = n;
        notify();
    }

}
