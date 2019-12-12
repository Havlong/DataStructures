package fifth

import java.awt.*
import javax.swing.*

/**
 * 12/12/2019
 * SortFrame
 *
 * @author havlong
 * @version 1.0
 */
class SortFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4") {
    private val sortButton = JButton("Сортировать")
    private val exitButton = JButton("Закрыть")

    private val bubbleSort = JRadioButton("Метод пузырька", true)
    private val selectionSort = JRadioButton("Метод выбора")
    private val insertionSort = JRadioButton("Метод вставок")
    private val shellSort = JRadioButton("Метод Шелла")
    private val linearSort = JRadioButton("Линейный метод")
    private val sortGroup = ButtonGroup()

    private val tableLabel = JLabel("")

    private var type = 0

    private var htmlTable = ""

    private val list = MutableList(20) { it + 1 }

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE
        fillFrame(contentPane)
        pack()
        setSize(sizeX, sizeY)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        setLocation((screenSize.width - sizeX) / 2, (screenSize.height - sizeY) / 2)
        isVisible = true
    }

    private fun fillFrame(panel: Container) {
        panel.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        panel.layout = GridBagLayout()

        val sortPanel = JPanel()
        sortPanel.layout = GridLayout(1, 5)

        bubbleSort.addActionListener { type = 0 }
        selectionSort.addActionListener { type = 1 }
        insertionSort.addActionListener { type = 2 }
        shellSort.addActionListener { type = 3 }
        linearSort.addActionListener { type = 4 }

        sortGroup.add(bubbleSort)
        sortGroup.add(selectionSort)
        sortGroup.add(insertionSort)
        sortGroup.add(shellSort)
        sortGroup.add(linearSort)

        sortPanel.addWithFont(bubbleSort)
        sortPanel.addWithFont(selectionSort)
        sortPanel.addWithFont(insertionSort)
        sortPanel.addWithFont(shellSort)
        sortPanel.addWithFont(linearSort)

        panel.add(sortPanel, GridBagConstraints().also {
            it.gridx = 0
            it.gridy = 0
            it.gridheight = 1
            it.gridwidth = 1
            it.fill = GridBagConstraints.HORIZONTAL
            it.weightx = 1.0
            it.weighty = 0.0
            it.insets = Insets(1, 1, 1, 1)
        })

        val controlPanel = JPanel()
        controlPanel.layout = GridLayout(1, 2)

        sortButton.addActionListener {
            list.shuffle()
            htmlTable = "<html><table>"
            htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
            when (type) {
                0 -> list.bubbleSort()
                1 -> list.selectionSort()
                2 -> list.insertionSort()
                3 -> list.shellSort()
                4 -> list.linearSort()
            }
            htmlTable += "</table></html>"
            tableLabel.text = htmlTable
        }
        exitButton.addActionListener {
            dispose()
        }
        controlPanel.addWithFont(sortButton)
        controlPanel.addWithFont(exitButton)
        panel.add(controlPanel, GridBagConstraints().also {
            it.gridx = 0
            it.gridy = 1
            it.gridheight = 1
            it.gridwidth = 1
            it.fill = GridBagConstraints.HORIZONTAL
            it.weightx = 1.0
            it.weighty = 0.0
            it.insets = Insets(1, 1, 1, 1)
        })
        tableLabel.font = MY_FONT
        panel.add(tableLabel, GridBagConstraints().also {
            it.gridx = 0
            it.gridy = 2
            it.gridheight = 5
            it.gridwidth = 1
            it.fill = GridBagConstraints.BOTH
            it.weightx = 1.0
            it.weighty = 1.0
            it.insets = Insets(1, 1, 1, 1)
        })
    }

    fun MutableList<Int>.swap(i: Int, j: Int) {
        val x = this[i]
        this[i] = this[j]
        this[j] = x
    }

    fun MutableList<Int>.bubbleSort() {
        for (i in size - 1 downTo 1) {
            var flag = true
            for (j in 0 until i) {
                if (this[j] > this[j + 1]) {
                    swap(j, j + 1)
                    flag = false
                }
            }
            htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
            if (flag) break
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
            htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
        }
    }

    fun MutableList<Int>.insertionSort() {
        for (i in 1 until size) {
            var pos = i
            while (pos > 0 && this[pos - 1] > this[i]) {
                pos--
            }
            val x = removeAt(i)
            add(pos, x)
            htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
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
            htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
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
                htmlTable += list.joinToString(prefix = "<tr><td>", separator = "</td><td>", postfix = "</td></tr>")
            }
        }
    }
}
