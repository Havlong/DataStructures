package third

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import kotlin.math.sqrt
import kotlin.math.truncate
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

/**
 * 30.09.2019
 * Main
 *
 * @author havlong
 * @version 1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 3"), ActionListener {
    private val exitButton = JButton("Закрыть")
    private val executeButton = JButton("Рассчитать")

    private val enterLabel = JLabel("Введите количество итераций: ")
    private val countField = JTextField("1", 4)

    private val sumHashScoreLabel = JLabel("Счётчик метода свёртывания")
    private val sumHashScore = JLabel("")

    private val modHashScoreLabel = JLabel("Счётчик метода деления")
    private val modHashScore = JLabel("")

    private val sqrHashScoreLabel = JLabel("Счётчик метода середины квадратов")
    private val sqrHashScore = JLabel("")

    private val constHashScoreLabel = JLabel("Счётчик метода умножения")
    private val constHashScore = JLabel("")

    private val openTimeLabel = JLabel("Время поиска при методе открытой адресации")
    private val openTime = JLabel("")

    private val linkTimeLabel = JLabel("Время поиска при методе цепочек")
    private val linkTime = JLabel("")

    private val myFont = Font(null, Font.BOLD, 18)

    private val keyList = Array(N) { Random.nextInt(0, 65001) }

    private val hashMap = sortedMapOf<Int, SortedSet<Int>>()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        isUndecorated = true
        fillFrame(contentPane)
        pack()
        setSize(sizeX, sizeY)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        setLocation((screenSize.width - sizeX) / 2, (screenSize.height - sizeY) / 2)
        isVisible = true
    }

    private fun fillFrame(panel: Container) {
        panel.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        panel.layout = GridLayout(8, 2)

        countField.addActionListener(this)

        panel.addWithFont(enterLabel)
        panel.addWithFont(countField)

        panel.addWithFont(sumHashScoreLabel)
        panel.addWithFont(sumHashScore)

        panel.addWithFont(sqrHashScoreLabel)
        panel.addWithFont(sqrHashScore)

        panel.addWithFont(modHashScoreLabel)
        panel.addWithFont(modHashScore)

        panel.addWithFont(constHashScoreLabel)
        panel.addWithFont(constHashScore)

        panel.addWithFont(openTimeLabel)
        panel.addWithFont(openTime)

        panel.addWithFont(linkTimeLabel)
        panel.addWithFont(linkTime)

        executeButton.addActionListener(this)
        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

    override fun actionPerformed(event: ActionEvent) {
        val answer = mutableMapOf("mod" to 0, "sqr" to 0, "sum" to 0, "const" to 0)
        val times = countField.text.toInt()
        repeat(times) {
            val cur = mutableMapOf("mod" to 0, "sqr" to 0, "sum" to 0, "const" to 0)
            for (i in keyList.indices)
                keyList[i] = Random.nextInt(0, 65001)
            hashMap.clear()
            keyList.forEach {
                if (it.modHash(HASH_MOD) in hashMap) {
                    hashMap[it.modHash(HASH_MOD)]!!.add(it)
                } else {
                    hashMap[it.modHash(HASH_MOD)] = sortedSetOf(it)
                }
            }
            cur["mod"] = hashMap.values.sumBy { it.size - 1 }
            for (i in keyList.indices)
                keyList[i] = Random.nextInt(0, 65001)
            hashMap.clear()
            keyList.forEach {
                if (it.sqrHash(DIGITS_COUNT, N) in hashMap) {
                    hashMap[it.sqrHash(DIGITS_COUNT, N)]!!.add(it)
                } else {
                    hashMap[it.sqrHash(DIGITS_COUNT, N)] = sortedSetOf(it)
                }
            }
            cur["sqr"] = hashMap.values.sumBy { it.size - 1 }
            for (i in keyList.indices)
                keyList[i] = Random.nextInt(0, 65001)
            hashMap.clear()
            keyList.forEach {
                if (it.sumHash(N) in hashMap) {
                    hashMap[it.sumHash(N)]!!.add(it)
                } else {
                    hashMap[it.sumHash(N)] = sortedSetOf(it)
                }
            }
            cur["sum"] = hashMap.values.sumBy { it.size - 1 }
            for (i in keyList.indices)
                keyList[i] = Random.nextInt(0, 65001)
            hashMap.clear()
            keyList.forEach {
                if (it.constHash(N) in hashMap) {
                    hashMap[it.constHash(N)]!!.add(it)
                } else {
                    hashMap[it.constHash(N)] = sortedSetOf(it)
                }
            }
            cur["const"] = hashMap.values.sumBy { it.size - 1 }
            val min = cur.minBy { it.value }!!.key
            answer[min] = answer[min]!! + 1
        }
        val keyArray = Array(10000) { Random.nextInt(0, 10001) }
        val searchKeyArray = Array(10000) { Random.nextInt(0, 20001) }
        sumHashScore.text = answer["sum"]!!.toString()
        sqrHashScore.text = answer["sqr"]!!.toString()
        modHashScore.text = answer["mod"]!!.toString()
        constHashScore.text = answer["const"]!!.toString()
        val open = Array(10000) { -1 }
        val links = Array(10000) { sortedSetOf<Int>() }
        val (listO, listL) = when (answer.maxBy { it.value }!!.key) {
            "sum" -> {
                keyArray.forEach {
                    var i = it.sumHash(10000)
                    links[i].add(it)
                    while (open[i] != it && open[i] != -1)
                        i = (i + 1) % 10000
                    open[i] = it
                }
                var countO = 0L
                var foundO = 0L
                val timeO = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.sumHash(10000)
                        var i = hash
                        countO++
                        if (open[i] != it) {
                            i = (i + 1) % 10000
                            while (i != hash && open[i] != it) {
                                i = (i + 1) % 10000
                                countO++
                            }
                            if (i != hash)
                                foundO++
                        } else {
                            foundO++
                        }
                    }
                }
                var countL = 10000L
                var foundL = 0L
                val timeL = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.sumHash(10000)
                        for (x in links[hash]) {
                            countL++
                            if (x == it)
                                break
                        }
                        if (it in links[hash])
                            foundL++
                    }
                }
                listOf(timeO, countO, foundO) to listOf(timeL, countL, foundL)
            }
            "sqr" -> {
                keyArray.forEach {
                    var i = it.sqrHash(4, 10000)
                    links[i].add(it)
                    while (open[i] != it && open[i] != -1)
                        i = (i + 1) % 10000
                    open[i] = it
                }
                var countO = 0L
                var foundO = 0L
                val timeO = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.sqrHash(4, 10000)
                        var i = hash
                        countO++
                        if (open[i] != it) {
                            i = (i + 1) % 10000
                            while (i != hash && open[i] != it) {
                                i = (i + 1) % 10000
                                countO++
                            }
                            if (i != hash)
                                foundO++
                        } else {
                            foundO++
                        }
                    }
                }
                var countL = 10000L
                var foundL = 0L
                val timeL = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.sqrHash(4, 10000)
                        for (x in links[hash]) {
                            countL++
                            if (x == it)
                                break
                        }
                        if (it in links[hash])
                            foundL++
                    }
                }
                listOf(timeO, countO, foundO) to listOf(timeL, countL, foundL)
            }
            "const" -> {
                keyArray.forEach {
                    var i = it.constHash(10000)
                    links[i].add(it)
                    while (open[i] != it && open[i] != -1)
                        i = (i + 1) % 10000
                    open[i] = it
                }
                var countO = 0L
                var foundO = 0L
                val timeO = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.constHash(10000)
                        var i = hash
                        countO++
                        if (open[i] != it) {
                            i = (i + 1) % 10000
                            while (i != hash && open[i] != it) {
                                i = (i + 1) % 10000
                                countO++
                            }
                            if (i != hash)
                                foundO++
                        } else {
                            foundO++
                        }
                    }
                }
                var countL = 10000L
                var foundL = 0L
                val timeL = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.constHash(10000)
                        for (x in links[hash]) {
                            countL++
                            if (x == it)
                                break
                        }
                        if (it in links[hash])
                            foundL++
                    }
                }
                listOf(timeO, countO, foundO) to listOf(timeL, countL, foundL)
            }
            "mod" -> {
                keyArray.forEach {
                    var i = it.modHash(9973)
                    links[i].add(it)
                    while (open[i] != it && open[i] != -1)
                        i = (i + 1) % 10000
                    open[i] = it
                }
                var countO = 0L
                var foundO = 0L
                val timeO = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.modHash(9973)
                        var i = hash
                        countO++
                        if (open[i] != it) {
                            i = (i + 1) % 10000
                            while (i != hash && open[i] != it) {
                                i = (i + 1) % 10000
                                countO++
                            }
                            if (i != hash)
                                foundO++
                        } else {
                            foundO++
                        }
                    }
                }
                var countL = 10000L
                var foundL = 0L
                val timeL = measureTimeMillis {
                    searchKeyArray.forEach {
                        val hash = it.modHash(9973)
                        for (x in links[hash]) {
                            countL++
                            if (x == it)
                                break
                        }
                        if (it in links[hash])
                            foundL++
                    }
                }
                listOf(timeO, countO, foundO) to listOf(timeL, countL, foundL)
            }
            else -> listOf(-1L, -1L, -1L) to listOf(-1L, -1L, -1L)
        }
        val (timeO, countO, foundO) = listO
        val (timeL, countL, foundL) = listL
        linkTime.text = "$timeL ms ${countL / 10000.0} сравнений $foundL элементов найдено"
        openTime.text = "$timeO ms ${countO / 10000.0} сравнений $foundO элементов найдено"
    }

    private fun Container.addWithFont(component: Component) {
        component.font = myFont
        add(component)
    }

    private fun Int.modHash(MOD: Int): Int {
        return (this % MOD) + 1
    }

    private fun Int.sqrHash(COUNT: Int, N: Int): Int {
        val sqr = this * this
        var x = 10
        var count = 1
        while (sqr / x > 0) {
            count++
            x *= 10
        }
        count -= COUNT
        return if (count < 0) {
            sqr % N
        } else {
            count /= 2
            x = 1
            for (i in 1..count) {
                x *= 10
            }
            sqr / x % N
        }
    }

    private fun Int.sumHash(N: Int): Int {
        var copy = this
        var hashSum = 0
        while (copy > 0) {
            hashSum += copy
            copy /= N
        }
        return hashSum % N
    }

    private fun Int.constHash(N: Int): Int {
        val x = (HASH_CONST * this)
        return (N * (x - truncate(x))).toInt()
    }
}

const val N: Int = 1000
const val DIGITS_COUNT = 3
const val HASH_MOD: Int = 997
val HASH_CONST: Double = (sqrt(5.0) - 1.0) / 2.0

fun main() {
    MainFrame(1280, 720)
}