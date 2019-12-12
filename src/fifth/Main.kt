package fifth

import java.awt.Component
import java.awt.Container
import java.awt.Font
import javax.swing.SwingUtilities

/**
 * 12.12.2019
 * Main
 *
 * @author Havlong
 * @version v1.0
 */
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

fun Container.addWithFont(component: Component) {
    component.font = MY_FONT
    add(component)
}

val MY_FONT = Font(null, Font.BOLD, 18)

fun main() {
    SwingUtilities.invokeLater {
        ChooseFrame(640, 360)
    }
}