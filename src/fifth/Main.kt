package fifth

import java.awt.*
import javax.swing.*
import kotlin.system.exitProcess

/**
 * 12.12.2019
 * Main
 *
 * @author Havlong
 * @version v1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4") {
    companion object {
        val myFont = Font(null, Font.BOLD, 18)
    }

    private val exitButton = JButton("Закрыть")
    private val executeButton = JButton("Рассчитать")

    private val enterLabel = JLabel("Введите количество итераций: ")
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
        panel.layout = GridLayout(8, 2)

        panel.addWithFont(enterLabel)
        panel.addWithFont(countField)



        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

}

fun MutableList<Int>.swap(i: Int, j: Int) {
    val x = this[i]
    this[i] = this[j]
    this[j] = x
}

fun MutableList<Int>.bubbleSort() {
    for (i in size - 1 downTo 1) {
        for (j in 0 until i) {
            if (this[j] > this[j + 1])
                swap(j, j + 1)
        }
    }
}

fun MutableList<Int>.selectionSort() {
    for (i in size - 1 downTo 1) {
        var max = 0
        for (j in 1..i) {
            if (this[j] > this[max]) {
                max = j
            }
        }
        swap(max, i)
    }
}

fun MutableList<Int>.insertionSort() {
    for (i in 1 until size) {
        var pos = i
        while (pos > 0 && this[pos - 1] > this[i])
            pos--
        val x = removeAt(i)
        add(pos, x)
        println(this)
    }
}

fun MutableList<Int>.shellSort() {
    var delta = size / 2
    while (delta > 0) {
        for (i in delta until size) {
            for (j in i - delta downTo 0 step delta) {
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
    val max: Int = max()!!
    val min: Int = min()!!
    val counter = Array(max - min + 1) { 0 }
    forEach {
        counter[it - min]++
    }
    var pos = 0
    for ((value, count) in counter.withIndex()) {
        for (i in 0 until count) {
            this[pos] = value + min
            pos++
        }
    }
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