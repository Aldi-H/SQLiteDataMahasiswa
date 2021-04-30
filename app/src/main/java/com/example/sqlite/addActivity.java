package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class addActivity extends AppCompatActivity {

    DBHelper helper;
    EditText TxNim, TxNama, TxKelas;
    Spinner SpProdi;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxNim = (EditText)findViewById(R.id.txNim_Add);
        TxNama = (EditText)findViewById(R.id.txNama_Add);
        TxKelas = (EditText)findViewById(R.id.txKelas_Add);
        SpProdi = (Spinner)findViewById(R.id.spProdi_Add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String nim = TxNim.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();
                String kelas = TxKelas.getText().toString().trim();
                String prodi = SpProdi.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.row_nim, nim);
                values.put(DBHelper.row_nama, nama);
                values.put(DBHelper.row_kelas, kelas);
                values.put(DBHelper.row_prodi, prodi);

                if (nim.equals("") || nama.equals("") || kelas.equals("")){
                    Toast.makeText(addActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertData(values);
                    Toast.makeText(addActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
