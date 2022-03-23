package kr.ac.morecontrlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher{

    private static final String TAG = MainActivity.class.getSimpleName();
    private CheckBox checkbox;
    private TextView outputTextView;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkbox = findViewById(R.id.checkbox);
        outputTextView = findViewById(R.id.outputTextView);
        nameEdit = findViewById(R.id.nameEdit);

        nameEdit.addTextChangedListener(this);
    }

    public void onCheckbox(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(TAG,"Checkbox state: " + cb.isChecked());
    }

    public void onBtnDoIt(View view) {
        Log.d(TAG,"onBtnDoIt, check" + checkbox.isChecked());
        String text = nameEdit.getText().toString();
        outputTextView.setText(text);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.v(TAG,"before");
        outputTextView.setText("Text Length: " + charSequence.length());
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.v(TAG,"textchange:" + charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.v(TAG,"after");
    }
}