import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LambdaInterfaceBasic {
	
	/**
	 * 단항 인터페이
	 */
	public void run(){
		// 1. Supplier : 입력값이 없고, 반환값이 있을때 사용
		Supplier<Integer> sp = () -> 180 + 20;
		System.out.println(sp.get());
		
		// 2. Consumer : 입력값이 있고, 반환값이 없을때 사용
		Consumer<Integer> cs = System.out::println;
		cs.accept(100);
		
		// 3. Function<파라미터타입,리턴타입> : 입력값도 있고, 반환값도 있을때 사용
		Function<Integer, Double> fn = x -> x * 0.5;
		System.out.println(fn.apply(100));
		
		// 4. Predicate : 입력값에 대한 참 거짓을 판단
		Predicate<Integer> pd = x -> x < 100;
		System.out.println(pd.test(200));
		
		// 5. 입력과 반환 타입이 동일할 때 사용
		UnaryOperator<Integer> uo = x -> x + 20;
		System.out.println(uo.apply(100));
	}
	
	public void run2(){
		// 1. BiConsumer : Consumer에 입력값이 두개
		BiConsumer<Integer, Integer> cs = (x, y) -> System.out.println(x + y);
		cs.accept(100, 1);
		// 2. BiFunction : Function에 입력값이 두개
		
		// 3. BiPredicate : Predicate에 입력값이 두개
		
		// 4. BinaryOperator : 입력값이 두개
	}
}