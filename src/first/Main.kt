package first

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 1"), ActionListener {
    private val exitButton = JButton("Закрыть")

    private val executeButton = JButton("Поиск")
    private val unsortedButton = JRadioButton("Неупорядоченный массив", true)

    private val sortedButton = JRadioButton("Упорядоченный массив")
    private val sortedType = ButtonGroup()

    private val enterLabel = JLabel("Введите ключ: ")
    private val keyField = JTextField(Random.nextInt(MAX_N).toString(), 9)

    private val sTimeLabel = JLabel("Время оптимального поиска")
    private val sortedTimeLabel = JLabel()
    private val usTimeLabel = JLabel("Время неоптимального поиска")
    private val unsortedTimeLabel = JLabel()
    private val sPosLabel = JLabel("Позиция в массиве при оптимальном поиске")
    private val sortedPosLabel = JLabel()
    private val usPosLabel = JLabel("Позиция в массиве при неоптимальном поиске")
    private val unsortedPosLabel = JLabel()
    private val myFont = Font(null, Font.BOLD, 18)

    private var isSorted: Boolean = false
    private var isChanged: Boolean = false

    private var list: MutableList<Int> = MutableList(MAX_N + 1) { Random.nextInt(MAX_N) }

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

        keyField.addActionListener(this)

        panel.addWithFont(enterLabel)
        panel.addWithFont(keyField)

        unsortedButton.addActionListener { isSorted = false; isChanged = true }
        sortedButton.addActionListener { isSorted = true; isChanged = true }
        sortedType.add(unsortedButton)
        sortedType.add(sortedButton)

        panel.addWithFont(unsortedButton)
        panel.addWithFont(sortedButton)

        panel.addWithFont(sTimeLabel)
        panel.addWithFont(sortedTimeLabel)
        panel.addWithFont(sPosLabel)
        panel.addWithFont(sortedPosLabel)
        panel.addWithFont(usTimeLabel)
        panel.addWithFont(unsortedTimeLabel)
        panel.addWithFont(usPosLabel)
        panel.addWithFont(unsortedPosLabel)

        executeButton.addActionListener(this)
        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

    private fun checkList(key: Int) {
        if (isChanged) {
            list = if (isSorted) MutableList(MAX_N + 1) { Random.nextInt(10) }
            else MutableList(MAX_N + 1) { Random.nextInt(MAX_N) }
            if (isSorted) {
                for (i in 1 until list.size) {
                    list[i] += list[i - 1]
                }
            }
            isChanged = false
        }
        list[list.size - 1] = key
    }

    override fun actionPerformed(event: ActionEvent) {
        val keyStr = keyField.text
        if (keyStr.matches("\\s*-?[1-9]\\d*\\s*".toRegex())) {
            val key = keyStr.toInt()
            checkList(key)
            var pos = 0
            val sTime = if (isSorted) measureTimeMillis {
                for (i in 0..100) {
                    pos = 0
                    while (list[pos] < key) pos++
                }
            } else measureTimeMillis {
                for (i in 0..100) {
                    pos = 0
                    while (list[pos] != key) pos++
                }
            }
            if (pos == list.size - 1 || list[pos] != key)
                sortedPosLabel.text = "404 Not Found"
            else
                sortedPosLabel.text = "${pos + 1}"
            sortedTimeLabel.text = "$sTime ms"
            val usTime = measureTimeMillis {
                for (i in 0..100) {
                    pos = 0
                    while (pos < list.size - 1 && list[pos] != key) pos++
                }
            }
            if (pos < list.size - 1)
                unsortedPosLabel.text = "${pos + 1}"
            else
                unsortedPosLabel.text = "404 Not Found"
            unsortedTimeLabel.text = "$usTime ms"
        }
    }

    private fun Container.addWithFont(component: Component) {
        component.font = myFont
        add(component)
    }
}

const val MAX_N = 1000000

fun main() {
    MainFrame(1280, 720)
}