package com.example.postapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import javax.microedition.khronos.egl.EGLDisplay

class PostReg : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb:SQLiteDatabase

    lateinit var btnRegist:Button
    lateinit var edtTitle: EditText
    lateinit var edtContents: EditText
    lateinit var rg_ID: RadioGroup
    lateinit var rb_noname: RadioButton
    lateinit var rb_yesname: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_reg)

        btnRegist = findViewById(R.id.btnRegister)
        edtTitle = findViewById(R.id.edtTitle)
        edtContents = findViewById(R.id.edtContents)
        rg_ID = findViewById(R.id.ID)
        rb_noname =findViewById(R.id.noname)
        rb_yesname = findViewById(R.id.yesname)

        dbManager = DBManager(this,"postDB", null,1)

        btnRegist.setOnClickListener{
            var str_title: String=  edtTitle.text.toString()
            var str_contents: String = edtContents.text.toString()

            var str_ID: String = ""
            if(rg_ID.checkedRadioButtonId == R.id.yesname) {
                str_ID = rb_noname.text.toString()
            }
            if(rg_ID.checkedRadioButtonId == R.id.yesname){
                str_ID = rb_yesname.text.toString()
            }

            sqlitedb = dbManager.writableDatabase
            sqlitedb.execSQL("INSERT INTO post VALUES ('"+str_title+"', '"+str_contents+"','"+str_ID+"')")
            sqlitedb.close()

            val intent = Intent(this, Postinfo::class.java)
            intent.putExtra("intent_title", str_title)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_reg, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_list -> {
                val intent = Intent(this,PostList::class.java )
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}