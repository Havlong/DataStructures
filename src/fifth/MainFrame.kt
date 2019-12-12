package fifth

import java.awt.ComponentOrientation
import java.awt.Container
import java.awt.GridLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import kotlin.system.measureTimeMillis

/**
 * 12/12/2019
 * MainFrame
 *
 * @author havlong
 * @version 1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4"), ActionListener {
    companion object {
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
            dispose()
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