package third

import java.awt.*
import java.awt.List
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.*
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.exitProcess

/**
 * 30.09.2019
 * Main
 *
 * @author havlong
 * @version 1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 1"), ActionListener {
    private val exitButton = JButton("Закрыть")
    private val executeButton = JButton("Поиск")

    private val countField = JTextField("1", 4)
    private val enterLabel = JLabel("Введите количество итераций: ")

    private val myFont = Font(null, Font.BOLD, 18)

    private val keyList = List(N) { Random.nextInt(0, 65001) }

    private val hashList = List(N) { mutableListOf<Int>() }

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

        executeButton.addActionListener(this)
        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(executeButton)
        panel.addWithFont(exitButton)
    }

    override fun actionPerformed(event: ActionEvent) {

    }

    private fun Container.addWithFont(component: Component) {
        component.font = myFont
        add(component)
    }

    private fun Int.modHash(): Int {
        return (this % HASH_MOD) + 1
    }

    private fun Int.sqrHash(): Int {
        val strSqr = (this * this).toString()
        return (if (strSqr.length >= DIGITS_COUNT) {
            val toDelete = (strSqr.length - DIGITS_COUNT) / 2
            strSqr.substring(toDelete, toDelete + DIGITS_COUNT)
        } else {
            strSqr.padEnd((DIGITS_COUNT - strSqr.length + 1) / 2, '0')
        }).toInt()
    }

    private fun Int.sumHash(): Int {
        var copy = this
        var hashSum = 0
        while (copy > 0) {
            hashSum += copy
            copy /= N
        }
        return hashSum % N
    }

    private fun Int.constHash(): Int {
        return (N * (HASH_CONST * this)).toInt()
    }
}

const val N: Int = 1000
const val DIGITS_COUNT = 3
const val HASH_MOD: Int = 997
val HASH_CONST: Double = (sqrt(5.0) - 1.0) / 2.0

fun main() {
    MainFrame(1280, 720)
}