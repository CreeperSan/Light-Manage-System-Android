package creepersan.smartlight.conpoment

import java.util.*

class Stack<T>(){
    private var linkedList:LinkedList<T>
    get
    private var stackName:String = "Undefine"
    get set
    private var listener:OnEditListener<T>? = null
    get set

    public constructor(name:String) : this() {
        stackName = name
    }

    init {
        linkedList = LinkedList<T>()
    }

    /**
     *      基础操作
     */
    //把对象压入站栈顶
    fun push(obj:T){

    }
    //移除栈顶对象
    fun pop(){

    }
    //查看栈顶对象
    fun peek(){

    }
    //查找索引
    fun indexOf(obj:T):Int{
        return linkedList.indexOf(obj)
    }
    fun lastIndexOf(obj:T):Int{
        return linkedList.lastIndexOf(obj)
    }
    fun size():Int{
        return linkedList.size
    }

    /**
     *      覆写操作
     */
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("现在打印栈 $stackName    栈的大小为${linkedList.size}")
        for ((index,obj) in linkedList.withIndex()){
            stringBuilder.append("第${index}个 : ${obj.toString()}")
        }
        stringBuilder.append("栈 $stackName 打印完毕!")
        return stringBuilder.toString()
    }

    /**
     *      接口事务
     */
    interface OnEditListener<T>{
        fun onEdit(type:Int,position:Int,element:T)

        object EditType{
            val TYPE_ADD = 1
            val TYPE_REMOVE = 0
        }
    }
    fun hasOnEditListener():Boolean{
        return listener == null
    }
}
