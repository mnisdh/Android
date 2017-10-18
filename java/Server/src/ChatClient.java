import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	/**
	 * 특정 ip와 port를 가진 서버에 접속해서 메시지를 전송하는 프로그램
	 * @param args
	 */
	public static void main(String[] args){
		Client client = new Client("192.168.1.120", 10004);
		client.start();
	}
}

class Client extends Thread{
	boolean runFlag = true;
	String ip;
	int port;
	
	public Client(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void run(){
		Scanner scanner = new Scanner(System.in);
		try {
			Socket socket = new Socket(ip, port);
			OutputStream os = socket.getOutputStream();
			while(runFlag){
				String msg = scanner.nextLine();
				if("exit".equals(msg)) runFlag = false;
				
				msg += "\r\n";
				os.write(msg.getBytes());
				os.flush();
			}
			os.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}