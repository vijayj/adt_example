package com.example.todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

    private static final String FILENAME = "listItems.txt";
	private EditText editText;
	private Button addButton;
	private ListView list;
	private ArrayList<String> items;
	private ArrayAdapter<String> adapter;
	


	public void onAddedItem(View v){
		String text = editText.getText().toString();
		adapter.add(text);
		editText.setText("");		
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        editText = (EditText) findViewById(R.id.newText);
        addButton = (Button) findViewById(R.id.btnAdd);
        list = (ListView) findViewById(R.id.listView);
        populateArrayItems();

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		list.setAdapter(adapter);
		setupListViewListener();
		 
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
	    writeFile();
	}

	

    private void populateArrayItems() {
		items = readFile();		
	}


	private void setupListViewListener() {
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parentView, View view ,int position, long rowId){
//				String item = adapter.getItem(position);
				items.remove(position);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
	}
	
	private ArrayList<String> readFile(){		
		items = new ArrayList<String>();
		BufferedReader buf = null;
		try {
			File file = new File(getFilesDir(), FILENAME);
			buf = new BufferedReader(new FileReader(file));
			
			String str;
			while((str = buf.readLine()) != null)
				items.add(str);
			buf.close();			
		} catch (IOException e) {
		  e.printStackTrace();
		} finally {		
			return items;
		}
	}
	
	private void writeFile(){		
		try {
			File file = new File(getFilesDir(), FILENAME);
			BufferedWriter buf = new BufferedWriter(new FileWriter(file));
			for (String item: items) {
				buf.write(item+"\n");	
			}
			buf.close();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
    
}
