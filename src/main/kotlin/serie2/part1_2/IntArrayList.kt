package serie2.part1_2

class IntArrayList(private val capacity:Int) : Iterable <Int> {
    private val data = IntArray(capacity)
    private var head = 0
    private var size = 0
    private var tail = 0


    fun append(x:Int):Boolean {
       if(size==capacity)return false
        data[tail] = x
        tail = (tail + 1) % capacity
        size++
        return true
    }

    fun get(n:Int):Int?  {
       if(n>=size||n<0)return null
        val index = (head + n) % capacity
            return data[index]
    }

    fun addToAll(x:Int)   {
        for (i in 0..size){
            val index = (head + 1) % capacity
            data[index] += x
        }
    }

    fun remove(): Boolean {
        if (size == 0) return false
        head = (head + 1) % capacity ; size--
        return true
    }

    override fun iterator(): Iterator<Int> {
        TODO("Not yet implemented")
    }
}