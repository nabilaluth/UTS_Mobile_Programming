package com.mobileprogramming.utsmobpro1904436;

import static android.provider.MediaStore.Video.VideoColumns.DESCRIPTION;
import static com.mobileprogramming.utsmobpro1904436.db.DatabaseContract.NoteColumns.CATEGORY;
import static com.mobileprogramming.utsmobpro1904436.db.DatabaseContract.NoteColumns.FOOD;
import static com.mobileprogramming.utsmobpro1904436.db.DatabaseContract.NoteColumns.TITLE;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mobileprogramming.utsmobpro1904436.db.CoffeeDrinkNoteHelper;
import com.mobileprogramming.utsmobpro1904436.entity.CoffeeDrinkNote;


public class NoteAddUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtTitle, edtDesc;
    private Spinner spnrCategory;
    private CheckBox chCake, chBrownies, chDalgona;

    private boolean isEdit = false;
    private CoffeeDrinkNote coffeeDrinkNote;
    private int position;
    private CoffeeDrinkNoteHelper coffeeDrinkNoteHelper;
    private String menuMakanan1, menuMakanan2, menuMakanan3;

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_update);

        edtTitle = findViewById(R.id.edt_title);
        spnrCategory = findViewById(R.id.spnr_category);
        edtDesc = findViewById(R.id.edt_desc);
        chCake = findViewById(R.id.ch_cake);
        chBrownies = findViewById(R.id.ch_brownies);
        chDalgona = findViewById(R.id.ch_dalgona);
        Button btnSubmit = findViewById(R.id.btn_submit);

        coffeeDrinkNoteHelper = CoffeeDrinkNoteHelper.getInstance(getApplicationContext());
        coffeeDrinkNoteHelper.open();

        coffeeDrinkNote = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (coffeeDrinkNote != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            coffeeDrinkNote = new CoffeeDrinkNote();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = "Edit";
            btnTitle = "Ubah";

            if (coffeeDrinkNote != null) {
                edtTitle.setText(coffeeDrinkNote.getTitle());
                edtDesc.setText(coffeeDrinkNote.getDesc());
                SharedPreferences desk = getSharedPreferences("Kategori",MODE_PRIVATE);
                int spinnerValue = desk.getInt("userChoiceSpinner",-1);
                if(spinnerValue != -1) {
                    // set the selected value of the spinner
                    spnrCategory.setSelection(spinnerValue);
                }

                String makananPelengkap = coffeeDrinkNote.getFood();
                String [] arrMakanan = makananPelengkap.split("\\s");

                for(String a : arrMakanan){
                    switch (a) {
                        case "Cake":
                            chCake.setChecked(true);
                            break;
                        case "Brownies":
                            chBrownies.setChecked(true);
                            break;
                        case "Dalgona":
                            chDalgona.setChecked(true);
                            break;
                    }
                }
            }
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            String title = edtTitle.getText().toString().trim();
            String desc = edtDesc.getText().toString().trim();

            String category = spnrCategory.getSelectedItem().toString().trim();

            if (TextUtils.isEmpty(title)) {
                edtTitle.setError("Field can not be blank");
                return;
            }

            if(chCake.isChecked())
            {
                menuMakanan1="Cake ";
            }
            else if(chCake.isChecked())
            {
                menuMakanan1=" ";
            }
            if(chBrownies.isChecked())
            {
                menuMakanan2="Brownies ";
            }
            else if(chCake.isChecked())
            {
                menuMakanan2=" ";
            }
            if(chDalgona.isChecked())
            {
                menuMakanan3="Dalgona Candy";
            }
            else if(chCake.isChecked())
            {
                menuMakanan3=" ";
            }

            String food = menuMakanan1+menuMakanan2+menuMakanan3;

            if (TextUtils.isEmpty(title)) {
                edtTitle.setError("Field can not be blank");
                return;
            }

            coffeeDrinkNote.setTitle(title);
            coffeeDrinkNote.setCategory(category);
            coffeeDrinkNote.setDesc(desc);
            coffeeDrinkNote.setFood(food);


            Intent intent = new Intent();
            intent.putExtra(EXTRA_NOTE, coffeeDrinkNote);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(TITLE, title);
            values.put(CATEGORY, category);

            int userChoice = spnrCategory.getSelectedItemPosition();
            SharedPreferences sharedPref = getSharedPreferences("Kategori",0);
            SharedPreferences.Editor prefEditor = sharedPref.edit();
            prefEditor.putInt("userChoiceSpinner", userChoice);
            prefEditor.commit();

            values.put(DESCRIPTION, desc);
            values.put(FOOD, food);



            if (isEdit) {
                long result = coffeeDrinkNoteHelper.update(String.valueOf(coffeeDrinkNote.getId()), values);
                if (result > 0) {
                    setResult(RESULT_UPDATE, intent);
                    finish();
                } else {
                    Toast.makeText(NoteAddUpdateActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                }
            } else {
                long result = coffeeDrinkNoteHelper.insert(values);

                if (result > 0) {
                    coffeeDrinkNote.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(NoteAddUpdateActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (id == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Note";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (isDialogClose) {
                        finish();
                    } else {
                        long result = coffeeDrinkNoteHelper.deleteById(String.valueOf(coffeeDrinkNote.getId()));
                        if (result > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        } else {
                            Toast.makeText(NoteAddUpdateActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
