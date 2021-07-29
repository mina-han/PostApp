package com.example.postapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class Postinfo : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var tvTitle: TextView
    lateinit var tvContents: TextView
    lateinit var ivTumbsUp: ImageView
    lateinit var tvGoodNumber: TextView

    lateinit var str_title: String
    lateinit var str_contents: String
    var goodnumber: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postinfo)

        tvTitle = findViewById(R.id.edtTitle)
        tvContents = findViewById(R.id.edtContents)
        ivTumbsUp = findViewById(R.id.tumbsup)
        tvGoodNumber = findViewById(R.id.goodnumber)

        val intent = intent
        str_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "postDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post WHERE name = '"+str_title+"';",null)

        if(cursor.moveToNext()) {
            str_contents = cursor.getString((cursor.getColumnIndex("contents"))).toString()
            goodnumber = cursor.getInt((cursor.getColumnIndex("goodnumber")))
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvTitle.text = str_title
        tvContents.text = str_contents
        ivTumbsUp.imageAlpha
        tvGoodNumber.text = "" + goodnumber

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_info, menu)
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
            R.id.action_reg -> {
                val intent = Intent(this,PostReg::class.java )
                startActivity(intent)
                return true
            }
            R.id.action_remove -> {

                dbManager = DBManager(this, "postDB", null, 1)
                sqlitedb = dbManager.readableDatabase

                sqlitedb.execSQL("DELETE FROM personnel WHERE name = '" +str_title+"';")
                sqlitedb.close()
                dbManager.close()

                val intent = Intent(this,PostList::class.java )
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}