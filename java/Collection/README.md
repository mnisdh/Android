# Collection

## Array
```java
/**
 * 선언
 * 타입[] 이름 = new 타입[배열공간크기];
 * 기본형인 int, long 등은 공간을 할당하는 것만으로
 * 기본값으로 0이 할당이됨
 */
public void checkArray(){
	int[] intArray = new int[10];
	System.out.println(intArray[7]);

	Item[] itemArray = new Item[10];
	int itemLength = itemArray.length;
	for(int i = 0; i < itemLength; i++) itemArray[i] = new Item();
	System.out.println(itemArray[7].getMyName());
}
```

## List
```java
/**
 * Index를 포함하는 동적 객체배열
 */
public void checkList(){
	// 선언은 일반 객체를 초기화 하는것과 같다
	List list = new ArrayList();

	// 입력
	list.add(new Item()); // <- 0번 index로 생성
	list.add(new Item()); // <- 1번 index로 생성

	// 조회
	list.get(0); // <- index가 0번째인 값을 가져옴

	// 수정
	list.set(1, new Item()); // 1번 index의 아이템을 대체

	// 삽입
	list.add(1, new Item()); // 1번부터 이후의 아이템 index를 하나씩 증가시키고
													 // 자신이 1번으로 들어감

	// 삭제
	list.remove(1); // index가 1번인 것을 삭제
}
```

## Generic 사용
```java
/**
 * 제네릭을 사용하는 방법
 * 타입<제네릭타입> 변수이름;    <- 제네릭타입은 클래스만 가능
 */
public void checkGeneric(){
	ArrayList<Item> list = new ArrayList<>();

	list.add(new Item());

	for(Item item : list) item.getMyName();
}
```

## Set
```java
/**
 * List와 유사하나 중복값을 허용하지 않는 동적 객체배열
 */
public void checkSet(){
	// 선언
	Set<String> set = new HashSet<>();

	// 입력 - 중복값은 입력되지 않음
	set.add("Hello");
	set.add("Good to see you");
	set.add("Hello");

	for(String str : set) str.length();

	// set은 iterator를 달아서 순서대로 처리해 줄 수 있음
	Iterator<String> iterator = set.iterator();
	while(iterator.hasNext()) iterator.next();
}
```

## Map
```java
/**
 * Key, Value로 구성된 동적 객체배열
 */
public void checkMap(){
	// 선언
	Map<String, Integer> map = new HashMap<>();

	// 입력
	map.put("Key01", 12344434);
	map.put("Key02", 12334);

	// 조회
	map.get("Key01");

	// map을 반복문으로 처리하기
	for(String key : map.keySet()) map.get(key);
}
```
