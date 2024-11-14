package com.example.db_sqlite_mi2a

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.db_sqlite_mi2a.helper.DBHelper

class MainActivity : AppCompatActivity() {
    private lateinit var etNamaLengkap :EditText
    private lateinit var etNamaKampus :EditText
    private lateinit var etEmail :EditText
    private lateinit var etPhone :EditText
    private lateinit var btnSubmit : Button
    private lateinit var btnShowData : Button
    private lateinit var txtNama : TextView
    private lateinit var txtKampus : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etNamaLengkap = findViewById(R.id.etNamaLengkap)
        etNamaKampus = findViewById(R.id.etNamaKampus)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnShowData = findViewById(R.id.btnShowData)
        txtNama = findViewById(R.id.txtNama)
        txtKampus = findViewById(R.id.txtKampus)

        //add data
        btnSubmit.setOnClickListener(){
            val dbHelper = DBHelper(this, null)

            //get data dari widget edit teks
            val name = etNamaLengkap.text.toString()
            val kampus = etNamaKampus.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            //masukan ke db sqlite
            dbHelper.addName(
                name,
                kampus,
                email,
                phone
            )

            //kita tambahkan toast untuk notif berhasil masuk
            Toast.makeText(this, name + " Data berhasil input ke db",
                Toast.LENGTH_LONG )

            //ketika berhasil input, kita clear semua isisan di widget
            etNamaLengkap.text.clear()
            etNamaKampus.text.clear()
            etEmail.text.clear()
            etPhone.text.clear()

        }

        btnShowData.setOnClickListener(){
            val db = DBHelper(this, null)

            val cursor = db.getName()
            cursor!!.moveToFirst() // mengambil data yag pertama atau yang terbaru
            txtNama.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_LENGKAP)) + "\n")
            txtKampus.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_KAMPUS)) + "\n")
            while (cursor.moveToNext()){
                txtNama.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_LENGKAP)) + "\n")
                txtKampus.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAMA_KAMPUS)) + "\n")
            }


        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}