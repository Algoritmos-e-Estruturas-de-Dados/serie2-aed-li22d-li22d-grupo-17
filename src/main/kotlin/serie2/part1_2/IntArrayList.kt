package serie2.part1_2

class IntArrayList(val capacity:Int) : Iterable <Int> {
    val data = IntArray(capacity)
    var head = 0
    var size = 0
    var tail = 0


    fun append(x:Int):Boolean {
       if(size==capacity)return false //Se a lista tiver cheia
        data[tail] = x
        tail = (tail + 1) % capacity
        size++
        return true
    }

    fun get(n:Int):Int?  {
       if(n>=size||n<0)return null //Indice inválido
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

    override fun iterator(): Iterator<Int> { // Opcional
        TODO("Not yet implemented")
    }
}