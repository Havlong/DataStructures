package fifth

import java.awt.ComponentOrientation
import java.awt.Container
import java.awt.GridLayout
import java.awt.Toolkit
import javax.swing.JButton
import javax.swing.JFrame
import kotlin.system.exitProcess

/**
 * 12/12/2019
 * ChooseFrame
 *
 * @author havlong
 * @version 1.0
 */
class ChooseFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4") {
    private val sortsButton = JButton("Демонстрация сортировок")
    private val mainButton = JButton("Характеристики сортировок")
    private val exitButton = JButton("Закрыть")
    private val mainFrame = MainFrame(1280, 720).also { it.dispose() }
    private val sortFrame = SortFrame(1280, 720).also { it.dispose() }

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        fillFrame(contentPane)
        pack()
        setSize(sizeX, sizeY)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        setLocation((screenSize.width - sizeX) / 2, (screenSize.height - sizeY) / 2)
        isVisible = true
        requestFocus()
    }

    private fun fillFrame(panel: Container) {
        panel.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT
        panel.layout = GridLayout(3, 1)
        sortsButton.addActionListener {
            sortFrame.also {
                it.isVisible = true
                it.requestFocus()
            }
        }
        mainButton.addActionListener {
            mainFrame.also {
                it.isVisible = true
                it.requestFocus()
            }
        }
        exitButton.addActionListener {
            exitProcess(0)
        }
        panel.addWithFont(sortsButton)
        panel.addWithFont(mainButton)
        panel.addWithFont(exitButton)
    }
}
