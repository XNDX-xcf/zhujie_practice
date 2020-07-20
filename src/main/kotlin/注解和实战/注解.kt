package 注解和实战

//如何去标识一个类或者一个属性 一个方法 一个方法
//如何注解Annotation来实现标识功能
//Kotlin的注解完全继承于Java
//注解只是一个标识 不会影响类的任何运行
/**
 * AnnotationTarget CLASS 标识一个类
 *                  CONSTRUCTOR 标识构造函数
 *                  PROPERTY  标识一个属性
 *                  FUNCTION 标识一个函数
 *                  VALUE_PARAMETER 标识一个函数的参数
 *
 *
 * AnnotationRetention  注解的作用域(生命周期)
 *                      SOURCE
 *                      BINARY
 *                      RUNTIME
 *注意：注解类里面定义参数时 只能使用val
 * */

//User UserTable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Tablename(val name:String)

@Target(AnnotationTarget.CONSTRUCTOR)
annotation class MyCons

//name -> firtname
@Target(AnnotationTarget.PROPERTY)
annotation class MyParam(val column_name:String)

@Target(AnnotationTarget.FUNCTION)
annotation class MyFunc

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class MyFuncParam

@Tablename(name = "IIIIITable") class IIII @MyCons constructor(@MyParam(column_name = "age") var age:Int){
    @MyParam(column_name = "firstname") val name:String="jack"
    @MyCons constructor(name:String):this(20){}
    @MyFunc fun show(@MyFuncParam des:String){

    }
}

fun main() {

}