package fifth

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

/**
 * 12.12.2019
 * Main
 *
 * @author Havlong
 * @version v1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4"), ActionListener {
    companion object {
        val myFont = Font(null, Font.BOLD, 18)
        var swapCounter = 0L
        var comparisonCounter = 0L
    }

    private val exitButton = JButton("Закрыть")
    private val executeButton = JButton("Рассчитать")

    private val bubbleLabel = JLabel("Счётчик метода прямого обмена")
    private val bubbleLabelScore = JLabel("")

    private val selectionLabel = JLabel("Счётчик метода прямого выбора")
    private val selectionLabelScore = JLabel("")

    private val insertionLabel = JLabel("Счётчик метода прямого включения")
    private val insertionLabelScore = JLabel("")

    private val shellLabel = JLabel("Счётчик метода Шелла")
    private val shellLabelScore = JLabel("")

    private val linearLabel = JLabel("Счётчик линейного метода")
    private val linearLabelScore = JLabel("")

    private val enterLabel = JLabel("Введите размер массива для сортировки: ")
    private val countField = JTextField("1", 4)

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
        panel.layout = GridLayout(7, 2)

        countField.addActionListener(this)
        panel.addWithFont(enterLabel)
        panel.addWithFont(countField)

        panel.addWithFont(bubbleLabel)
        panel.addWithFont(bubbleLabelScore)

        panel.addWithFont(selectionLabel)
        panel.addWithFont(selectionLabelScore)

        panel.addWithFont(insertionLabel)
        panel.addWithFont(insertionLabelScore)

        panel.addWithFont(shellLabel)
        panel.addWithFont(shellLabelScore)

        panel.addWithFont(linearLabel)
        panel.addWithFont(linearLabelScore)

        executeButton.addActionListener(this)
        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

    override fun actionPerformed(event: ActionEvent) {
        val n = countField.text.toInt()
        if (n > 10000)
            return
        val list = randomList(n)
        var listCopy = ArrayList(list)
        var time = measureTimeMillis(listCopy::bubbleSort)
        bubbleLabelScore.text = "$time мс; $comparisonCounter сравнений; $swapCounter обменов"
        listCopy = ArrayList(list)
        time = measureTimeMillis(listCopy::selectionSort)
        selectionLabelScore.text = "$time мс; $comparisonCounter сравнений; $swapCounter обменов"
        listCopy = ArrayList(list)
        time = measureTimeMillis(listCopy::insertionSort)
        insertionLabelScore.text = "$time мс; $comparisonCounter сравнений; $swapCounter обменов"
        listCopy = ArrayList(list)
        time = measureTimeMillis(listCopy::shellSort)
        shellLabelScore.text = "$time мс; $comparisonCounter сравнений; $swapCounter обменов"
        listCopy = ArrayList(list)
        time = measureTimeMillis(listCopy::linearSort)
        linearLabelScore.text = "$time мс; $comparisonCounter сравнений; $swapCounter присваиваний"
    }

}

fun MutableList<Int>.swap(i: Int, j: Int) {
    val x = this[i]
    this[i] = this[j]
    this[j] = x
    MainFrame.swapCounter++
}

fun MutableList<Int>.bubbleSort() {
    MainFrame.swapCounter = 0
    MainFrame.comparisonCounter = 0
    for (i in size - 1 downTo 1) {
        var flag = true
        for (j in 0 until i) {
            if (this[j] > this[j + 1]) {
                swap(j, j + 1)
                flag = false
            }
            MainFrame.comparisonCounter++
        }
        if (flag) break
    }
}

fun MutableList<Int>.selectionSort() {
    MainFrame.swapCounter = 0
    MainFrame.comparisonCounter = 0
    for (i in size - 1 downTo 1) {
        var max = 0
        for (j in 1..i) {
            if (this[j] > this[max]) {
                max = j
            }
            MainFrame.comparisonCounter++
        }
        swap(max, i)
    }
}

fun MutableList<Int>.insertionSort() {
    MainFrame.swapCounter = 0
    MainFrame.comparisonCounter = 0
    for (i in 1 until size) {
        var pos = i
        while (pos > 0 && this[pos - 1] > this[i]) {
            MainFrame.comparisonCounter++
            pos--
        }
        val x = removeAt(i)
        add(pos, x)
        MainFrame.swapCounter += i - pos + 1
    }
}

fun MutableList<Int>.shellSort() {
    MainFrame.swapCounter = 0
    MainFrame.comparisonCounter = 0
    var delta = size / 2
    while (delta > 0) {
        for (i in delta until size) {
            for (j in i - delta downTo 0 step delta) {
                MainFrame.comparisonCounter++
                if (this[j] > this[j + delta])
                    swap(j, j + delta)
                else
                    break
            }
        }

        delta /= 2
    }
}

fun MutableList<Int>.linearSort() {
    MainFrame.swapCounter = 0
    MainFrame.comparisonCounter = 0
    val max: Int = max()!!
    val min: Int = min()!!
    val counter = Array(max - min + 1) { 0 }
    forEach {
        MainFrame.comparisonCounter++
        counter[it - min]++
    }
    var pos = 0
    for ((value, count) in counter.withIndex()) {
        for (i in 0 until count) {
            MainFrame.swapCounter++
            this[pos] = value + min
            pos++
        }
    }
}

fun randomList(size: Int): MutableList<Int> {
    return (1..size).shuffled().toMutableList()
}

private fun Container.addWithFont(component: Component) {
    component.font = MainFrame.myFont
    add(component)
}

fun main() {
    SwingUtilities.invokeLater {
        MainFrame(1280, 720)
    }
}