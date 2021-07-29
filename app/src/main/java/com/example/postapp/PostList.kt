package com.example.postapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView

class PostList : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqliteDB: SQLiteDatabase
    lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        dbManager = DBManager(this, "postDB", null, 1)
        sqliteDB = dbManager.readableDatabase

        layout = findViewById(R.id.post)

        var cursor: Cursor
        cursor = sqliteDB.rawQuery("SELECT * FROM post", null)

        var num: Int = 0
        while (cursor.moveToNext()) {
            var str_title = cursor.getString((cursor.getColumnIndex("title"))).toString()
            var str_contents = cursor.getString((cursor.getColumnIndex("contents"))).toString()
            var goodnumber = cursor.getInt((cursor.getColumnIndex("goodnumber")))

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvTitle: TextView = TextView(this)
            tvTitle.text = str_title
            tvTitle.textSize = 30f
            tvTitle.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvTitle)

            var tvContents: TextView = TextView(this)
            tvContents.text = str_title
            tvContents.textSize = 30f
            tvContents.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvContents)

            var tvgoodnumber: TextView = TextView(this)
            tvgoodnumber.text = goodnumber.toString()
            layout_item.addView(tvgoodnumber)

            layout_item.setOnClickListener {
                val intent = Intent(this, Postinfo::class.java)
                intent.putExtra("intent_title", str_title)
                startActivity(intent)
            }
            layout.addView(layout_item)
            num++;

        }
        cursor.close()
        sqliteDB.close()
        dbManager.close()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_reg -> {
                val intent = Intent(this, PostReg::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}