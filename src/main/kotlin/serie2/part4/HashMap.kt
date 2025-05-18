package serie2.part4

class HashMap<K, V>(
    initialCapacity: Int = 16,
    private val loadFactor: Float = 0.75f
) : MutableMap<K, V> {

    private class HashNode<K, V>(
        override val key: K,
        override var value: V,
        var next: HashNode<K, V>? = null
    ) : MutableMap.MutableEntry<K, V> {
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)
    private var count = 0

    override val size: Int
        get() = count
    override val capacity: Int
        get() = table.size
    override fun get(key: K): V? {
        val index = indexFor(key)
        var node = table[index]
        while (node != null) {
            if (node.key == key) return node.value
            node = node.next
        }
        return null
    }
    override fun put(key: K, value: V): V? {
        val index = indexFor(key)
        var node = table[index]

        while (node != null) {
            if (node.key == key) {
                val old = node.value
                node.value = value
                return old
            }
            node = node.next
        }
        val newNode = HashNode(key, value, table[index])
        table[index] = newNode
        count++

        if (count >= capacity * loadFactor) expand()

        return null
    }

    private fun indexFor(key: K): Int {
        val hc = key.hashCode()
        return (hc and 0x7FFFFFFF) % table.size
    }

    private fun expand() {
        val oldTable = table
        table = arrayOfNulls(oldTable.size * 2)
        count = 0

        for (head in oldTable) {
            var node = head
            while (node != null) {
                put(node.key, node.value)
                node = node.next
            }
        }
    }
    fun clear() {
        table = arrayOfNulls(table.size)
        count = 0
    }



    override fun iterator(): Iterator<MutableMap.MutableEntry<K, V>> {
        return object : Iterator<MutableMap.MutableEntry<K, V>> {
            private var bucketIndex = 0
            private var currentNode: HashNode<K, V>? = null

            init {
                advanceToNextBucket()
            }

            private fun advanceToNextBucket() {
                while (bucketIndex < table.size && table[bucketIndex] == null) {
                    bucketIndex++
                }
                currentNode = if (bucketIndex < table.size) table[bucketIndex] else null
            }

            override fun hasNext(): Boolean = currentNode != null

            override fun next(): MutableMap.MutableEntry<K, V> {
                val result = currentNode ?: throw NoSuchElementException()
                currentNode = currentNode?.next
                if (currentNode == null) {
                    bucketIndex++
                    advanceToNextBucket()
                }
                return result
            }
        }
    }
}
