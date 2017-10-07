
public class ThreadMain {

	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		
		new Thread(){
			@Override
			public void run(){
				a.process();
			}
		}.start();
		
		new Thread(){
			@Override
			public void run(){
				b.process();
			}
		}.start();
	}
}

class A{
	public void process(){
		for(int i = 0; i < 20; i++) {
			System.out.println("A" + i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class B{
	public void process(){
		for(int i = 0; i < 20; i++) {
			System.out.println("B" + i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}