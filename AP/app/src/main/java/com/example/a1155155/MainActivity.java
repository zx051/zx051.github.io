package com.example.a1155155;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {
    private ListView noteListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteListView = findViewById(R.id.noteListView);
        Button addButton = findViewById(R.id.addButton);

        notes = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        noteListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(-1);
            }
        });

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showNoteDialog(position);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteNote(position);
                return true;
            }
        });
    }

    private void showNoteDialog(final int position) {
        final EditText noteEditText = new EditText(this);
        if (position != -1) {
            noteEditText.setText(notes.get(position));
        }

        // 創建對話框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add/Edit Note")
                .setView(noteEditText)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String note = noteEditText.getText().toString();
                        if (!note.isEmpty()) {
                            if (position != -1) {
                                // 編輯現有的記事
                                notes.set(position, note);
                            } else {
                                // 添加新記事
                                notes.add(note);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        builder.create().show();// 實現添加和編輯記事的對話框
    }

    private void deleteNote(int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();// 刪除記事
    }
}
