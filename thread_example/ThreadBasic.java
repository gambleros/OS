class ThreadA extends Thread{
		public void run(){
			for (int i=1; i<=20; i++) {
				System.out.println("A: "+i);
			}
			System.out.println("A done!!");
		}
}
class ThreadB extends Thread{
		public void run(){
			for (int i=-1; i >= -20; i--) {
				System.out.println("\t\tB: "+ i);
			}
			System.out.println("B done!!");
		}
}
public class ThreadBasic {
	public static void main(String[] args) {
		Thread a= new ThreadA();
		Thread b= new ThreadB();
		a.start();
		b.start();
		System.out.println("Program done!!");
	}
}