##### 数据类型
1. Boolean
	
	val aBoolean:Boolean = true

2. Number
- 浮点型：Double,Float
- 整型:Long,Int,Short
- 字节:Byte

3. Char
- 字符对应java的Character
- 占两个字节，表示一个16位Unicode字符
- 字符用''引起来

	val aChar:Char = 'A'

4. 基本类型转换
- 不可隐式转换
	
	val anInt:Int = 5
	val aLong:Long = anInt.toLong()

5. 字符串String
kotlin中：
'=='等于java中的'eqluse'
'==='等于java中的'=='

##### 字符串模版

	val a:Int = 1
	val b:Int = 1
	println("$a + $b = ${a + b}")

	//原始字符串，支持换行和缩进，无法转义
	val rawString:String = """
	\t
	\n
	$a
	"""

6. 类和对象

	class PeopleA(XG: String, ZX: String, SY: String) : People(XG, ZX, SY)
	open class People(var XG: String, var ZX: String, var SY: String) {
	    init {
	        println("${this.javaClass.simpleName},性格：$XG,长相：$ZX,声音：$SY")
	    }
	}
	fun main(args: Array<String>) {
	    val test: PeopleA = PeopleA("温柔", "甜美", "动人")
	}

7. 空类型和智能类型转换

	fun getName():String?{
		return null
	}
	main{
		//如果为空会return
		val name:String = getName()?:return
		println(name.length)
		val a:String? = "Hello"
		//告诉编译器这个值肯定不是null
		println(name!!.length)
	}

8. 区间Range

	val range:IntRange = 1..1024
	//区间内是否包含这个值
	range.contains(2)//返回true
	2 in range //同上

9. 数组Array

	val arrayOfInt:IntArray = intArrayOf(1,2,3,4)
	//CharArray,Array<String>, 