package second

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

    private val enterLabel = JLabel("Введите ключ: ")
    private val keyField = JTextField(Random.nextInt(MAX_N).toString(), 9)

    private val lTimeLabel = JLabel("Время последовательного поиска")
    private val linearTimeLabel = JLabel()
    private val fTimeLabel = JLabel("Время оптимального бинпоиска")
    private val fastTimeLabel = JLabel()
    private val eTimeLabel = JLabel("Время неоптимального бинпоиска")
    private val easyTimeLabel = JLabel()
    private val lPosLabel = JLabel("Позиция в массиве при последовательном поиске")
    private val linearPosLabel = JLabel()
    private val fPosLabel = JLabel("Позиция в массиве при оптимальном поиске")
    private val fastPosLabel = JLabel()
    private val ePosLabel = JLabel("Позиция в массиве при неоптимальном поиске")
    private val easyPosLabel = JLabel()

    private val myFont = Font(null, Font.BOLD, 18)

    private var list: MutableList<Int> = MutableList(MAX_N + 1) { Random.nextInt(10) }
    init {
        for (i in 1 until list.size) {
            list[i] += list[i - 1]
        }
    }

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

        keyField.addActionListener(this)

        panel.addWithFont(enterLabel)
        panel.addWithFont(keyField)

        panel.addWithFont(fTimeLabel)
        panel.addWithFont(fastTimeLabel)
        panel.addWithFont(fPosLabel)
        panel.addWithFont(fastPosLabel)
        panel.addWithFont(eTimeLabel)
        panel.addWithFont(easyTimeLabel)
        panel.addWithFont(ePosLabel)
        panel.addWithFont(easyPosLabel)
        panel.addWithFont(lTimeLabel)
        panel.addWithFont(linearTimeLabel)
        panel.addWithFont(lPosLabel)
        panel.addWithFont(linearPosLabel)

        executeButton.addActionListener(this)
        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

    override fun actionPerformed(event: ActionEvent) {
        val keyStr = keyField.text
        if (keyStr.matches("\\s*-?[1-9]\\d{0,8}\\s*".toRegex())) {
            val key = keyStr.toInt()

            list[list.size - 1] = key

            var pos = 0
            val sTime = measureTimeMillis {
                repeat(MAX_N / 1000) {
                    pos = 0
                    while (list[pos] < key) pos++
                }
            }
            if (pos == list.size - 1 || list[pos] != key)
                linearPosLabel.text = "404 Not Found"
            else
                linearPosLabel.text = "${pos + 1}"
            linearTimeLabel.text = "~${sTime * 1000} ms"

            var l: Int
            var r: Int
            var mid = 0
            val easyTime = measureTimeMillis {
                repeat(MAX_N) {
                    l = 0
                    r = MAX_N - 1
                    mid = (l + r) shr 1
                    loop@ while (list[mid] != key && l < r) {
                        when {
                            list[mid] > key -> {
                                r = mid - 1
                                mid = (l + r) shr 1
                            }
                            list[mid] < key -> {
                                l = mid + 1
                                mid = (l + r) shr 1
                            }
                        }
                    }
                }
            }
            if (list[mid] == key) {
                easyPosLabel.text = "${mid + 1}"
            } else {
                easyPosLabel.text = "404 Not Found"
            }
            easyTimeLabel.text = "$easyTime ms"

            val fastTime = measureTimeMillis {
                repeat(MAX_N) {
                    l = 0
                    r = MAX_N - 1
                    mid = (l + r) shr 1
                    while (l < r) {
                        when {
                            list[mid] >= key -> {
                                r = mid
                            }
                            else -> {
                                l = mid + 1
                            }
                        }
                        mid = (l + r) shr 1
                    }
                }
            }
            if (list[mid] == key) {
                fastPosLabel.text = "${mid + 1}"
            } else {
                fastPosLabel.text = "404 Not Found"
            }
            fastTimeLabel.text = "$fastTime ms"
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