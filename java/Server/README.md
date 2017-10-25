# Server

## ServerMain

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	/**
	 * 브라우저에서 내가 만든 서버프로그램쪽으로 request를 요청
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket client = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			// 1. 서버 생성
			server = new ServerSocket(4000);
			
			// 2. 요청을 대기
			client = server.accept();
			
			// 3. 접속된 client로 부터 stream을 생성한다
			isr = new InputStreamReader(client.getInputStream());
			br = new BufferedReader(isr);
			
			StringBuilder sb = new StringBuilder();
			
			String temp = "";
			while((temp = br.readLine()) != null) sb.append(temp).append("\n");
			
			System.out.println(sb.toString());
		} 
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(isr != null)
				try {
					isr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(client != null)
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(server != null)
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
```


## HttpServer

```java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

public class HttpServer {
	public static void main(String[] args) {
		WebServer server = new WebServer(8081);
		server.start();
	}
}

class WebServer extends Thread{
	public boolean runFlag = false;
	ServerSocket server = null;
	
	public WebServer(int port){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private byte[] getTextFileToBytes(String filePath){
		byte[] result = null;
		// 1. 읽는 스트림을 연다
		try(FileInputStream fis = new FileInputStream(filePath)){
			// 2. 실제 파일 인코딩을 바꿔주는 래퍼 클래스 사용
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

			// 3. 버퍼처리
			BufferedReader br = new BufferedReader(isr);

			// readLine이 null이 아닐때까지 실행
			String row;
			String temp = "";
			while((row = br.readLine()) != null){
				temp += row + "\r\n";
			}
			result = temp.getBytes();
			br.close();
			isr.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
	
	private byte[] getImageToBytes(Path filePath){
		byte[] result = null;
		try {
			result = Files.readAllBytes(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void readFile(OutputStream os, String filePath){
		String type = "";
		filePath = "/Users/daeho/Downloads" + filePath;
		byte[] result = null;
		
		File file = new File(filePath);
		if(file.exists()){
			String mimeType = new MimetypesFileTypeMap().getContentType(file);
			String fileType = file.getName().split("\\.")[1];
			
			switch(fileType.toLowerCase()){
				case "txt":
					type = "text/text";
					result = getTextFileToBytes(filePath);
					break;
				case "png":
					type = "image/png";
					result = getImageToBytes(file.toPath());
					break;
				case "jpg":
					type = "image/jpg";
					result = getImageToBytes(file.toPath());
					break;
				case "gif":
					type = "image/gif";
					result = getImageToBytes(file.toPath());
					break;
			}
		}
		
		try {
			os.write("HTTP/1.0 200 OK \r\n".getBytes());
			os.write(("Content-Type: " + type + " \r\n").getBytes());
			os.write(("Content-Length: " + result.length + "\r\n").getBytes());
			os.write("\r\n".getBytes());
			os.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run(){
		try{
			runFlag = true;
			while(runFlag){
				// 1. 클라이언트 연결대기
				Socket client = server.accept();
				// 2. 요청에대한 처리를 새로운 thread에서 처리해준다
				new Thread(){
					public void run(){
						try {
							// 3. 스트림을 연결
							InputStreamReader isr = new InputStreamReader(client.getInputStream());
							BufferedReader br = new BufferedReader(isr);
							
							// 4. 웹브라우저에서 요청한 주소로 줄단위의 명령어가 날라오는 것을 꺼내서 처리
							String line = br.readLine();
							if(line != null){
								// 5. 요청된 명령어의 첫줄만 parsing 해서 동작을 결정
								// Method 도메인을제외한조소 프로토콜버전
								String cmd[] = line.split(" ");
								System.out.println(cmd[0]);
								System.out.println(cmd[1]);
								System.out.println(cmd[2]);
								
								OutputStream os = client.getOutputStream();
								readFile(os, cmd[1]);
								os.flush();
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
			}
		} catch(Exception e) {
			
		}
	}
}
```


## ChatServer

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		Server server = new Server(10004);
		server.start();
	}
}

class Server extends Thread{
	ServerSocket server;
	public boolean runFlag = true;
	// 0. 서버소켓 생성
	public Server(int port){
		try{
			server = new ServerSocket(port);
		}catch(Exception e){}
	}
	public void run(){
		System.out.println("server is running...");
		while(runFlag){
			try{
				// 1. 클라이언트의 요청을 대기
				Socket client = server.accept(); // 아래쪽 코드는 접속요청을 받기 전까지는 실행되지 않는다
				new ClientProcess(client).start();
			}catch(Exception e){}
		}
	}
}
// 클라이언트 요청을 개별 thread로 처리하는 클래스
class ClientProcess extends Thread{
	Socket client;
	public ClientProcess(Socket client){
		this.client = client;
	}
	public void run(){
		try{
			// 1. client와 stream을 열고
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String msg = "";
			// 2. exit가 아닐때까지 한줄씩 읽어서 내용을 출력 
			while((msg=br.readLine()) != null){
				if("exit".equals(msg)) break;
				System.out.println(client.getInetAddress()+":"+msg);
			}
			// 연결닫기
			br.close();
			isr.close();
			client.close();
		}catch(Exception e){
			
		}
	}
}
```


## ChatClient

```java
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
```