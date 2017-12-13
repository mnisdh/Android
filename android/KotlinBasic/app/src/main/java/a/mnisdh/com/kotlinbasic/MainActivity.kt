package a.mnisdh.com.kotlinbasic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

/**
 * class 클래스명 : (extends) 부모객체의 생성자 호출
 */
class MainActivity : AppCompatActivity() {
    //final
    val PI = 3.14

    var i:Int = 0
    var f:Float = 0f
    var d:Double = 0.0

    var s:String = "string"
    var c:Char = 'a'

    private fun testArray(s:String){
        // 배열 만들기
        var array = intArrayOf(1,2,3,4,5)
        var arrayStr = arrayOf<String>()

        // 1부터 100까지 입력되는 배열 만들기
        var array100 = Array<Int>(100, {i -> i+1})

        // abc를 100개 갖는 배열 만들기
        var arrayABC = Array<String>(100, {i -> "abc"})
    }

    private fun testImmutable(){
        // Immutable collection  값수정 불가
        var list = List<String>(100, {i -> "abc"})
    }

    private fun testMutable(){
        // 값을 변경 가능한 Collection만들기 > MutableList, MutableMap, MutableSet
        var mutableList = mutableListOf<String>()
        var mutableList2 = MutableList<String>(100, {i -> "abc"})

        mutableList.add("");
    }

    private fun testJavaCollection(){
        var list = ArrayList<String>();
        list.add("")
    }

    private fun testReturn() : Int{

        return 0
    }

    private fun test(){
        fun calc():Int{
            return 1
        }

        var cal = calc()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.text)
    }
}


class KotlinOne constructor(param:String){
    var paramTest =""

    init {
        paramTest = param
        println(param)
    }

    fun aaa(){
        paramTest
    }
}

class KotlinTwo {
    var value = 0
    var value2 = ""

    constructor(param: String){
        println(param)
    }
    constructor(param: String, param2:String){
        println("param of two=$param")
        println("param of two=${param2 + (2 + 1)}")
    }

    /**
     * 생성자 다음으로 무조건 호출됨
     */
    init {

    }
}


open class Parent {
    constructor(param: String){

    }
    constructor(param: String, param2: String){

    }

    open fun one(){

    }
    open fun two(){

    }
}

class Child constructor(param:String) : Parent(param){
    override fun one() {
        super.one()
    }

    override fun two() {
        super.two()
    }
}

class ChildTwo : Parent{
    constructor():super(""){

    }
    constructor(param: String):super(param){

    }
    constructor(param: String, param2: String):super(param, param2){

    }
}


abstract class AbsChild : Parent{
    constructor(param: String):super(param){

    }

    // 비추상 함수를 오버라이드해서 추상 함수로 만들수 있다
    override abstract fun one()
}


interface IParent{
    // 인터페이스에 변수선언 가능
    var value:String?
    fun get():String
    fun set(a:String)
}

class ChildInterface : IParent{
    override var value: String?
        get() = value
        set(value) {this.value = value}


    override fun get(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(a: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


// 접근제한자
// private, protected, public

// internal - 같은 모듈에 있는 파일만 접근할 수 있다


// Extension 이미 컴파일 된 클래스에 함수를 추가할 수 있다
fun String.plus():String{
    return "(" + this + ")"
}
var aaaaa = ""
fun test(){

    println(aaaaa.plus())
}


// 데이터 클래스 - getter와 setter를 쓰기 쉽게 지원
data class User(var name:String, var age:Int, var tel:String)

class User2(var name:String, var age:Int, var tel:String)

fun test2(){
    var user = User("가나다",12,"222-2222-2222")
    var user2 = User2("가나다",12,"222-2222-2222")

    println(user) // 실제 값이 출력
    println(user2) // 메모리 주소가 출력
}

fun test3(param: String?){
    var nullable:String? // nullable
    nullable = null

    var nonNull:String
    //nonNull = null
}

fun test4(){
    test3(null)
}

// 엘비스 expression 널체크를 짧은 코드로 할 수 있다
fun testElvis(param:String?){
    param?.plus()

    if(param != null) param.plus()

    var pLength:Int = param?.length?:0
}











