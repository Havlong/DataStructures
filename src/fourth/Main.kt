package fourth

import java.awt.*
import javax.swing.*
import kotlin.system.exitProcess

/**
 * 30/10/2019
 * Main
 *
 * @author havlong
 * @version 1.0
 */
class MainFrame(sizeX: Int, sizeY: Int) : JFrame("Data Structures № 4") {
    private val exitButton = JButton("Закрыть")
    private val searchStatus = JLabel("")

    private val enterLabel = JLabel("Введите новое слово: ")
    private val enterField = JTextField("")

    private val searchLabel = JLabel("Введите слово для поиска: ")
    private val searchField = JTextField("")

    private val listLabel = JLabel("<html><h1>Слова:</h1><ul></ul></html>")

    private val myFont = Font(null, Font.BOLD, 22)

    private val tree = mutableListOf<MutableList<Pair<Char, Int>>>()

    private val wordSet = sortedSetOf<String>()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        isUndecorated = true
        fillFrame(contentPane)
        pack()
        setSize(sizeX, sizeY)
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        setLocation((screenSize.width - sizeX) / 2, (screenSize.height - sizeY) / 2)
        isVisible = true
        clearTree()
    }

    private fun fillFrame(panel: Container) {
        panel.layout = GridBagLayout()
        panel.componentOrientation = ComponentOrientation.LEFT_TO_RIGHT


        enterField.addActionListener {
            addNewWord((it.source as JTextField).text.toLowerCase())
        }

        panel.addWithFont(enterLabel, 0, 0, fill = GridBagConstraints.VERTICAL)
        panel.addWithFont(enterField, 0, 1, weightX = 1.0)

        searchField.addActionListener {
            findWord((it.source as JTextField).text.toLowerCase())
        }
        panel.addWithFont(searchLabel, 0, 2, fill = GridBagConstraints.VERTICAL)
        panel.addWithFont(searchField, 0, 3, weightX = 1.0)

        listLabel.verticalTextPosition = JLabel.CENTER
        panel.addWithFont(listLabel, 0, 4, weightY = 1.0)


        exitButton.addActionListener {
            exitProcess(0)
        }

        panel.addWithFont(searchStatus, 0, 5, fill = GridBagConstraints.VERTICAL)
        panel.addWithFont(exitButton, 0, 6)
    }

    private fun addNewWord(word: String) {
        var pos = 0
        var prevPos: Int
        for (i in (0 until word.length - 1)) {
            val c = word[i]
            prevPos = pos
            for ((char, nextPos) in tree[pos]) {
                if (char == c && nextPos != -1) {
                    pos = nextPos
                    break
                }
            }
            if (pos == prevPos) {
                pos = tree.size
                tree.add(mutableListOf())
                tree[prevPos].add(c to pos)
            }
        }
        if ((word.last() to -1) in tree[pos]) {
            searchStatus.text = "Слово существует"
        } else {
            tree[pos].add((word.last() to -1))
            searchStatus.text = "Слово добавлено"
        }
        wordSet.add(word)
        listLabel.text = wordSet.joinToString(
            separator = "</li><li>",
            prefix = "<html><h1>Слова:</h1><ul><li>",
            postfix = "</li></ul></html>"
        )
    }

    private fun findWord(word: String) {
        var pos = 0
        var prevPos: Int
        for (i in (0 until word.length - 1)) {
            val c = word[i]
            prevPos = pos
            for ((char, nextPos) in tree[pos]) {
                if (char == c && nextPos != -1) {
                    pos = nextPos
                    break
                }
            }
            if (pos == prevPos) {
                searchStatus.text = "Слово не найдено"
                return
            }
        }
        if ((word.last() to -1) in tree[pos]) {
            searchStatus.text = "Слово найдено"
        } else {
            searchStatus.text = "Слово не найдено"
        }
    }

    private fun clearTree() {
        tree.clear()
        wordSet.clear()
        listLabel.text = ""
        tree.add(mutableListOf())
    }

    private fun Container.addWithFont(
        component: Component,
        gridX: Int = GridBagConstraints.RELATIVE,
        gridY: Int = GridBagConstraints.RELATIVE,
        weightX: Double = 0.0,
        weightY: Double = 0.0,
        width: Int = 1,
        height: Int = 1,
        fill: Int = GridBagConstraints.BOTH
    ) {
        component.font = myFont
        val constraints = GridBagConstraints()
        constraints.insets.set(10, 6, 10, 6)
        constraints.gridwidth = width
        constraints.gridheight = height
        constraints.fill = fill
        constraints.gridx = gridX
        constraints.gridy = gridY
        constraints.weightx = weightX
        constraints.weighty = weightY
        add(component, constraints)
    }
}

fun main() {
    SwingUtilities.invokeLater {
        MainFrame(1280, 720)
    }
}