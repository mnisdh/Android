# Design Pattern


## Observer pattern

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Observer pattern
 * 
 * Subject <- Observer
 * 
 * @author daeho
 *
 */
public class DPMain {

	public static void main(String[] args) {
		Subject server = new Subject();
		server.start();
		
		ClientDeamon cd = new ClientDeamon(server);
		cd.start();
		
		//server.addClient(new Client1());
		//server.addClient(new Client2());
	}

}

class Client1 implements Subject.IObserver{
	String title;
	public Client1(String title){
		this.title = title;
	}
	
	@Override
	public void noti() {
		System.out.println("클라이언트" + title + "에 변경사항이 반영됨");
	}
}

class Client2 implements Subject.IObserver{
	String title;
	public Client2(String title){
		this.title = title;
	}
	
	@Override
	public void noti() {
		System.out.println("클라이언트" + title + "에 변경사항이 반영됨");
	}
}

class Subject extends Thread{
	private List<IObserver> clients = new ArrayList<>();
	
	public void addClient(IObserver client){
		clients.add(client);
	}
	
	public void run(){
		Random r = new Random();
		while(true){
			for(IObserver client : clients) client.noti();
			
			System.out.println("[Subject] 전체 메세지 요청 완료");
			
			// 비주기적 갱신을 위한 테스트 코드
			try {
				Thread.sleep((r.nextInt(10) + 1) * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public interface IObserver{
		public void noti();
	}
}

class ClientDeamon extends Thread{
	Subject server;
	public ClientDeamon(Subject server){
		this.server = server;
	}
	
	public void run(){
		int count = 0;
		
		while(true){
			if(count % 2 == 0) server.addClient(new Client1(count + ""));
			else server.addClient(new Client2(count + ""));
		
			count++;
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
```