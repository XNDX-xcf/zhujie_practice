package 注解和实战.实战

import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

//模型数据
@Entity class User(
    @Column var id:Int,
    @Column var name:String,
    @Column var icon:String
){
    override fun toString(): String {
        return "User(id=$id,name=$name,icon=$icon)"
    }
}

class Student
//Table  --  类名
//类注解
@Retention
@Target(AnnotationTarget.CLASS)
annotation class Entity

//属性注解
@Retention
@Target(AnnotationTarget.PROPERTY)
annotation class Column

//模拟数据的查询
fun selectdata():Map<String,Map<String,Any>>{
    //模拟有两个表 User Student
    //使用Map封装数据 k-v
    val userData= mapOf(
        Pair("id",1),
        Pair("name","向往"),
        Pair("icon","www.baidu.com")
    )
    val studentData= mapOf(
        Pair("sId",1),
        Pair("name","小王"),
        Pair("address","西南大学")
    )

    val datas= mapOf(
        Pair("User",userData),
        Pair("Student",studentData)
    )
    return datas
}

fun autoParseFromTable(model:KClass<out Any>): Any?{
   //先从数据库里面读取出表对应的数据
    val datas= selectdata()
    //判断传递过来的KClass对象有没有Entity注解
    val entity=model.findAnnotation<Entity>()
    if (entity==null){
        //传递过来的类 没有Entity注解
        //不能自动转换
        return null
    }else{
        //这个类可以被自动转化
        //获取 类名 ----  表名
        val tableName=model.simpleName

        //使用这个表名去数据中获取这个表对应的数据
        val info=datas[tableName]
        //创建对象 再将info的数据 对应的 填充到对象的属性中
        //使用默认的主构造函数创建
        val constructor=model.primaryConstructor

        //创建一个数组保存解析的属性值
        //创建的数组元数个数 和 构造函数中参数的个数相同 初始值为null
        val params= arrayOfNulls<Any>(constructor?.parameters?.size!!)

        //遍历构造函数的参数
        constructor.parameters.forEach {
            //从数据源中获取这个参数对应的值
            val value=info?.get(it.name)
            //将这个值保存到数组中
            params[it.index]=value
        }

        //调用构造函数 创建对象
        val obj=constructor?.call(*params)
        return obj
    }

}

//vararg 对应的是Arrary类型的数组 不能是List
fun show(vararg e:Int){
    e.forEach { println(it) }
}

fun main() {
    //User -> Any -> User
    val user=autoParseFromTable(User::class) as User
     println(user.name)
    /*
    val temp= arrayOf(10,20,30).toIntArray()
    show(*temp)*/

    //网络数据  -> gson 对象
}