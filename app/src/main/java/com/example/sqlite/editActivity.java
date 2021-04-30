package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class editActivity extends AppCompatActivity {

    DBHelper helper;
    EditText TxNim, TxNama, TxKelas;
    Spinner SpProdi;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxNim = (EditText)findViewById(R.id.txNim_Edit);
        TxNama = (EditText)findViewById(R.id.txNama_Edit);
        TxKelas = (EditText)findViewById(R.id.txKelas_Edit);
        SpProdi = (Spinner)findViewById(R.id.spJK_Edit);

        getData();
    }

    private void getData(){
        Cursor cursor = helper.oneData(id);
        if(cursor.moveToFirst()){
            String nim = cursor.getString(cursor.getColumnIndex(DBHelper.row_nim));
            String nama = cursor.getString(cursor.getColumnIndex(DBHelper.row_nama));
            String prodi = cursor.getString(cursor.getColumnIndex(DBHelper.row_prodi));
            String kelas = cursor.getString(cursor.getColumnIndex(DBHelper.row_kelas));

            TxNim.setText(nim);
            TxNama.setText(nama);

            if (prodi.equals("Teknologi Informasi")){
                SpProdi.setSelection(0);
            }else if(prodi.equals("Sistem Informasi")){
                SpProdi.setSelection(1);
            } else if (prodi.equals("Teknik Komputer")){
                SpProdi.setSelection(2);
            }else if(prodi.equals("Teknik Informatika")){
                SpProdi.setSelection(3);
            } else if(prodi.equals("Pendidikan Teknologi Informasi")){
                SpProdi.setSelection(4);
            }

            TxKelas.setText(kelas);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit:
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
                    Toast.makeText(editActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT);
                }else{
                    helper.updateData(values, id);
                    Toast.makeText(editActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(editActivity.this);
                builder.setMessage("Data ini akan dihapus.");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteData(id);
                        Toast.makeText(editActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
