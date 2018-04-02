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

class MainActivity : AppCompatActivity() {

    val numberCache: MutableList<String> = arrayListOf()
    val stackNumbers: MutableList<String> = arrayListOf()

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateDisplay(mainDisplayString: String){

        //val fullCalculationString = makeString(operationList, " ")
        //var fullCalculationTextView = findViewById(R.id.fullCalculationText) as TextView
        //fullCalculationTextView.text = fullCalculationString

        val mainTextView = findViewById(R.id.formula) as TextView
        mainTextView.text = mainDisplayString
    }

    fun makeString(list: List<String>,joiner: String = "") : String {

        if (list.isEmpty()) return ""
        return list.reduce { r, s -> r + joiner + s }
    }

    fun numberClick(view: View) {
        val button = view as Button
        val numberString = button.text;

        numberCache.add(numberString.toString())
        val text = makeString(numberCache);
        updateDisplay(text)
    }

    fun enterClick(view: View) {
        //val mainTextView = findViewById(R.id.formula) as TextView
        stackNumbers.add(formula.text.toString())
        result.text = result.text.toString().plus("\n").plus(stackNumbers.last())
        //mainTextView.text = ""
        //numberCache.clear()
    }
}
