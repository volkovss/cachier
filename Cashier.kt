/*
Соображения по поводу выбора архитектуры
У всех касс есть общие функции - печать чека и печать возвратного чека, поэтому имеет смысл
создать абстрактный класс Cashier. Кассы также различаются по печати на носителе или в файл , поэтому
можно разделить кассы на два неабстрактных класса PaperCashier и FileCashier.
Кроме того существует тип касс с расширенным функционалом печати, поэтому нужно
унаследовав от класса PaperCashier создать класс WrapperPaperCashier.
Поскольку некоторые различные ветки подкласов имеют общие функции (открытия и закрытия),
но другие не имеют, имеет смысл эту функциональность отразить в интерфейсе OpenClosable.
Так как работа касс выполняется через драйверы, то создадим класс обертку Driver и используя
свойства полиморфизма будем достигать необходимый результат вызывая необходимые функции и передавая
в них параметры необходимого типа.

Данная архитектура позволяет гибко добавлять новые функции, например, при появлении новых функций
у касс можно создать новый интерфейс и имплементировать его в классы, где эта функциональность
требуется.
*/

fun main() {
    val cashier1 = WrapperPaperCashier('*')
    val cashier2 = WrapperPaperCashier('+')
    val cashier3 = PaperCashier()
    val cashier4 = FileCashier()

    val driver = Driver()

    driver.open(cashier1)
    driver.print(cashier1)
    driver.printReturn(cashier1)
    driver.close(cashier1)

    driver.open(cashier2)
    driver.print(cashier2)
    driver.printReturn(cashier2)
    driver.close(cashier2)

    driver.print(cashier3)
    driver.printReturn(cashier3)

    driver.open(cashier4)
    driver.print(cashier4)
    driver.printReturn(cashier4)
    driver.close(cashier4)

}


class Driver {
    fun print(cashier: Cashier) {
        cashier.print()
    }

    fun printReturn(cashier: Cashier) {
        cashier.printReturn()
    }

    fun open(cashier: OpenClosable) {
        cashier.open()
    }

    fun close(cashier: OpenClosable) {
        cashier.close()
    }
}

interface OpenClosable {
    fun open()
    fun close()
}

abstract class Cashier {
    abstract fun print()
    abstract fun printReturn()
}

open class PaperCashier : Cashier() {
    override fun print() {
        println("print Receipt")
    }

    override fun printReturn() {
        println("print Return receipt")
    }
}

class WrapperPaperCashier(private val symbol: Char) : PaperCashier(),
    OpenClosable {
    override fun close() {
        printWrapper()
        println("print CLOSE")
        printWrapper()
    }

    override fun open() {
        printWrapper()
        println("print OPEN")
        printWrapper()
    }

    override fun print() {
        printWrapper()
        super.print()
        printWrapper()
    }

    override fun printReturn() {
        printWrapper()
        super.printReturn()
        printWrapper()
    }

    private fun printWrapper() {
        println(symbol)
    }

}

class FileCashier : Cashier(), OpenClosable {
    override fun close() {
        println("print CLOSE to file")
    }

    override fun open() {
        println("print OPEN to file")
    }

    override fun print() {
        println("print Receipt to file")
    }

    override fun printReturn() {
        println("print Return receipt to file")
    }
}

# cachier
