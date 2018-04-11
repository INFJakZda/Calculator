package com.zdano.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.graphics.Color.*

class MainActivity : AppCompatActivity() {

    var numberCache: MutableList<String> = arrayListOf()
    var stackNumbers: MutableList<String> = arrayListOf()
    var isEnter: Boolean = false
    var isCompute: Boolean = false
    var bckColor: Int = WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuWhite -> {
                item.isChecked = !item.isChecked
                bckColor = WHITE
                layoutView.setBackgroundColor(bckColor)
                return true
            }
            R.id.menuRed -> {
                item.isChecked = !item.isChecked
                bckColor = RED
                layoutView.setBackgroundColor(bckColor)
                return true
            }
            R.id.menuGreen -> {
                item.isChecked = !item.isChecked
                bckColor = GREEN
                layoutView.setBackgroundColor(bckColor)
                return true
            }
            R.id.menuYellow -> {
                item.isChecked = !item.isChecked
                bckColor = YELLOW
                layoutView.setBackgroundColor(bckColor)
                return true
            }
            R.id.menuBlue -> {
                item.isChecked = !item.isChecked
                bckColor = BLUE
                layoutView.setBackgroundColor(bckColor)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putStringArrayList("formula", ArrayList(numberCache))
        savedInstanceState.putStringArrayList("stack", ArrayList(stackNumbers))
        savedInstanceState.putBoolean("enter", isEnter)
        savedInstanceState.putBoolean("compute", isCompute)
        savedInstanceState.putInt("color", bckColor)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)

        numberCache = savedInstanceState.getStringArrayList("formula")
        updateDisplay(makeString(numberCache))

        stackNumbers = savedInstanceState.getStringArrayList("stack")
        result.text = makeString(stackNumbers, "\n")

        isEnter = savedInstanceState.getBoolean("enter")
        isCompute = savedInstanceState.getBoolean("compute")

        bckColor = savedInstanceState.getInt("color")
        layoutView.setBackgroundColor(bckColor)
    }

    fun updateDisplay(mainDisplayString: String){

        val mainTextView = findViewById(R.id.formula) as TextView
        mainTextView.text = mainDisplayString
    }

    fun makeString(list: List<String>,joiner: String = "") : String {

        if (list.isEmpty()) return ""
        return list.reduce { r, s -> r + joiner + s }
    }

    fun numberClick(view: View) {
        if(isEnter) {
            numberCache.clear()
            isEnter = false
        }
        if(isCompute) {
            enterClick(view)
            numberCache.clear()
            isEnter = false
            isCompute = false
        }
        val button = view as Button
        val numberString = button.text

        numberCache.add(numberString.toString())
        val text = makeString(numberCache)
        updateDisplay(text)
    }

    fun enterClick(view: View) {
        isEnter = true
        if(formula.text.toString().length > 0) {
            stackNumbers.add(formula.text.toString())
            result.text = makeString(stackNumbers, "\n")
        }
    }

    fun clearClick(view: View) {
        numberCache.clear()
        updateDisplay("")
    }

    fun dropClick(view: View) {
        if (!stackNumbers.isEmpty()) {
            val item =  stackNumbers.count() - 1
            stackNumbers.removeAt(item)
        }
        result.text = makeString(stackNumbers, "\n")
    }

    fun swapClick(view: View) {
        if(stackNumbers.size != 0 && numberCache.size != 0) {
            var tmp: String
            tmp = makeString(numberCache)
            numberCache.clear()
            val item =  stackNumbers.count() - 1
            for(let in stackNumbers[item])
                numberCache.add(let.toString())
            stackNumbers.removeAt(item)
            stackNumbers.add(item, tmp)
            result.text = makeString(stackNumbers, "\n")
            val text = makeString(numberCache)
            updateDisplay(text)
        }
    }

    fun compute(fstArg: Double, secArg: Double, sign: String): Double {
        var currentNumber: Double
        currentNumber = fstArg
        when (sign) {
            "-" -> currentNumber -= secArg
            "÷" -> currentNumber /= secArg
            "*" -> currentNumber *= secArg
            "+" -> currentNumber += secArg
            "^" -> currentNumber = Math.pow(currentNumber, secArg)
            "√" -> currentNumber = Math.pow(currentNumber, 1 / secArg)
        }
        return currentNumber
    }

    fun signClick(view: View) {
        if(stackNumbers.size != 0 && numberCache.size != 0) {
            var button = view as Button
            var sign: String
            sign = button.text.toString()
            var secArg: Double
            var fstArg: Double
            secArg = makeString(numberCache).toDouble()
            val item =  stackNumbers.count() - 1
            fstArg = stackNumbers[item].toDouble()
            stackNumbers.removeAt(item)
            var res: String
            res = compute(fstArg, secArg, sign).toString()
            numberCache.clear()
            for(let in res) {
                numberCache.add(let.toString())
            }
            result.text = makeString(stackNumbers, "\n")
            val text = makeString(numberCache)
            updateDisplay(text)
            isCompute = true
            isEnter = false
        }
    }

    fun plusMinusClick(view: View) {
        if(numberCache.size != 0) {
            if (numberCache[0].equals("-")) {
                numberCache.remove("-")
            } else {
                numberCache.add(0, "-")
            }
        }
        val text = makeString(numberCache)
        updateDisplay(text)
    }
}
