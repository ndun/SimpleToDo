package codepath.apps.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	//private String itemValue;
	private EditText editText;
	private int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String itemValue = getIntent().getStringExtra("listValue");
		index = getIntent().getIntExtra("index", 0);
		editText = (EditText)findViewById(R.id.etEditListItemValue);
		editText.setText(itemValue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	public void saveEdit(View v) {
        Intent data = new Intent();//new Intent(getApplicationContext(), TodoActivity.class);
		String newValue = editText.getText().toString();
		data.putExtra("newValue", newValue);
		data.putExtra("index", index);
		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish();
		
	}
}
