import java.text.SimpleDateFormat;

/**
 * 개별글 한개 한개를 저장하는 클래스
 * 
 * @author daeho
 *
 */
public class Memo {
	private int no;
	private String name;
	private String content;
	private long datetime;
	
	/**
	 * 생성자
	 * @param name
	 * @param content
	 */
	Memo(String name, String content){
		this.name = name;
		this.content = content;
		this.datetime = System.currentTimeMillis();
	}
	
	/**
	 * no 설정하기
	 * @param no
	 */
	public void setNo(int no) {this.no = no;}
	/**
	 * no 가져오기
	 * @return
	 */
	public int getNo() {return no;}
	/**
	 * name 가져오기
	 * @return
	 */
	public String getName() {return name;}
	/**
	 * content 가져오기
	 * @return
	 */
	public String getContent() {return content;}
	/**
	 * 날짜 가져오기
	 * 숫자로된 날짜값을 실제 날짜로 변경
	 * @return
	 */
	public String getDatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(datetime);
	}
	
	/**
	 * 내용 업데이트
	 * @param name
	 * @param content
	 */
	public void update(String name, String content){
		this.name = name;
		this.content = content;
		this.datetime = System.currentTimeMillis();
	}
}
