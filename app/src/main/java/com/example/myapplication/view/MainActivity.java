package com.example.myapplication.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ItemAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.ListModel;
import com.example.myapplication.viewmodel.ItemViewModel;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ActivityMainBinding activityMainBinding;
    public static ProgressDialog progressDialog;
    private static MainActivity instance;
    ItemViewModel itemViewModel;
    public static String search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        instance = MainActivity.this;
        //setting up recyclerview
        recyclerView = activityMainBinding.recyclerview;

        activityMainBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recyclerview.setHasFixedSize(true);

        //getting our ItemViewModel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        //creating the Adapter
        final ItemAdapter adapter = new ItemAdapter(this);


        //observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<ListModel>>() {
            @Override
            public void onChanged(@Nullable PagedList<ListModel> items) {

                //in case of any changes
                //submitting the items to adapter
                adapter.submitList(items);
            }
        });

        //setting the adapter
        activityMainBinding.recyclerview.setAdapter(adapter);

        activityMainBinding.etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (activityMainBinding.etSearch.getText().length() >= 2) {
                        search = activityMainBinding.etSearch.getText().toString();
                        itemViewModel.invalidate();
                        itemViewModel.invalidate();
                        hideKeyboard(MainActivity.this);
                    }
                    return true;
                }
                return false;
            }
        });
        activityMainBinding.etSearch.addTextChangedListener(textWatcher);
    }

    public void show() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait....");
                progressDialog.show();
                progressDialog.setCancelable(false);
            }
        });
    }

    public void dismiss() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    public static MainActivity getInstance() {

        return instance;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 3) {

                search = s.toString();
                itemViewModel.invalidate();
                itemViewModel.invalidate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

