package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null) {
}

fun splitEvensAndOdds(list:Node<Int>){
    val sentinela = list
    var current = list.next
    while(current != sentinela){
        val nextNode = current!!.next

        if(current.value % 2 == 0) {

            current.previous!!.next = current.next
            current.next!!.previous = current.previous
            current.next = sentinela.next
            current.previous = sentinela

            sentinela.next!!.previous = current
            sentinela.next = current
        }
        current = nextNode
    }
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    var ponteiro1 = list1.next
    var ponteiro2 = list2.next
    var Head: Node<T>? = null
    var Tail: Node<T>? = null
    var lastValue: T? = null

    while (ponteiro1 != list1 && ponteiro2 != list2){
        val comparison = cmp.compare(ponteiro1!!.value,ponteiro2!!.value)
        if (comparison < 0) {
            ponteiro1 = ponteiro1.next
        } else if (comparison > 0) {
            ponteiro2 = ponteiro2.next
        } else{
            if(lastValue == null || cmp.compare(ponteiro1.value,lastValue)!=0){
                val next1 = ponteiro1.next
                val next2 = ponteiro2.next

                ponteiro1.previous!!.next = ponteiro1.next
                ponteiro1.next!!.previous = ponteiro1.previous

                ponteiro2!!.previous!!.next = ponteiro2.next
                ponteiro2.next!!.previous = ponteiro2.previous

                ponteiro1.next = null
                ponteiro1.previous = Tail

                if(Tail==null){
                    Head = ponteiro1
                    ponteiro1.previous = null
                }else {
                    Tail.next = ponteiro1
                }
                Tail = ponteiro1
                lastValue = ponteiro1.value

                ponteiro1 = next1
                ponteiro2 = next2
            } else {
                ponteiro1 = ponteiro1.next
                ponteiro2 = ponteiro2.next
            }
        }
    }
    return Head
}