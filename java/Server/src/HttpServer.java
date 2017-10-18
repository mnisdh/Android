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