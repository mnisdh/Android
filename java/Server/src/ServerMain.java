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
			while((temp = br.readLine()) != null){
				sb.append(temp).append("\n");
			}
			
			System.out.println(sb.toString());
			
			
		} catch (IOException e) {
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
