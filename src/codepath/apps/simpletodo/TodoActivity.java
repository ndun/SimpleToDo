package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends Activity {

	private static final int REQUEST_CODE = 20;
    private static final Logger logger = Logger.getLogger("TodoActivity");

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        
        setupListViewListener();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch(IOException e) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    	saveItems();
    }
    
    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			return true;
    		}
    	});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener() {
		 
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    			// selected item 
                String listValue = items.get(pos);
                 
                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), EditItemActivity.class);
                // sending data to new activity
                i.putExtra("listValue", listValue);
                i.putExtra("index", pos);
                startActivityForResult(i, REQUEST_CODE);
    		}
    		

    	});

    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     String newValue = data.getExtras().getString("newValue");
	     int index = data.getExtras().getInt("index");
	     String item = itemsAdapter.getItem(index);	     
	     items.remove(index);
	     items.add(index, newValue);

	     saveItems();
	     item = newValue;

	     itemsAdapter.notifyDataSetChanged();
	     Toast.makeText(this, item + "has been edited", Toast.LENGTH_SHORT).show();
	  }
	}
}
 