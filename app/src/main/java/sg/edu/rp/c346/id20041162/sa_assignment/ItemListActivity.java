package sg.edu.rp.c346.id20041162.sa_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ItemListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name;
    Button btnAdd, btnShow, btnUpdate, btnCancel;
    ListView lvItem;
    DatePicker dp;
    Spinner spinnerFilter;

    int indexPos = 0;
    ArrayList<Product> warrantyList = new ArrayList<Product>();
    ProductAdapter adapter;
    ProductAdapter filterAdapter;

    boolean editing = false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        btnAdd = findViewById(R.id.btnAdd);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        lvItem = findViewById(R.id.lvItem);
        dp = findViewById(R.id.datePicker);
        spinnerFilter = findViewById(R.id.spinnerFilter);

        ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapterFilter);
        spinnerFilter.setOnItemSelectedListener(this);

        //List view adapter, warrantyList array
        adapter = new ProductAdapter(this, R.layout.adapter_view_layout, warrantyList);
        lvItem.setAdapter(adapter);

        //making list view long click
        registerForContextMenu(lvItem);

        //Show inputs
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add new input, interface
                if(editing == false){
                    Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH)+1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    dp.updateDate(mYear,mMonth-1,mDay);
                    btnAdd.setVisibility(View.VISIBLE);
                }
                //Update input, interface
                else if (editing == true){
                    String itemName = (warrantyList.get(indexPos)).getName();
                    int itemYear = (warrantyList.get(indexPos)).getYear();
                    int itemMonth = (warrantyList.get(indexPos)).getMonth();
                    int itemDay = (warrantyList.get(indexPos)).getDay();
                    dp.updateDate(itemYear,itemMonth-1,itemDay);

                    name.setText(itemName);
                    btnUpdate.setVisibility(View.VISIBLE);
                }

                //Call showInput method
                showInput();
            }
        });

        //Add input to object and array
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = name.getText().toString();

                int day = dp.getDayOfMonth();
                int month = dp.getMonth()+1;
                int year = dp.getYear();

                String date = year + "-" +month +"-"+day;

                warrantyList.add(new Product(itemName,date,year,month,day));

                Collections.sort(warrantyList, Product.ProductNameComparator);

                name.setText(null);
                adapter.notifyDataSetChanged();

                //Hide input interface after insert
                hideInput();
            }
        });


        //Get position when on Long click.
        lvItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                indexPos = position;
                return false;
            }
        });

        //Update input.
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = name.getText().toString();

                int day = dp.getDayOfMonth();
                int month = dp.getMonth()+1;
                int year = dp.getYear();
                String date = year + "-" +month +"-"+day;

                warrantyList.set(indexPos,new Product(itemName,date,year,month,day));
                Collections.sort(warrantyList, Product.ProductNameComparator);
                adapter.notifyDataSetChanged();

                name.setText(null);
                hideInput();

                //After editing change back to non-editing mode
                editing = false;
            }
        });

        //Cancel button to hide interface.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editing == true){
                    editing = false;
                }
                name.setText(null);
                hideInput();
            }
        });
    }

    //List view long click options
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"Edit");
        menu.add(0,1,1,"Delete");
    }

    //List view long click function
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Edit option
        if(item.getItemId()==0){
            editing = true;
            btnShow.performClick();
        }
        //Delete option
        else if(item.getItemId()==1){
            warrantyList.remove(indexPos);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    //Show input method
    public void showInput(){
        name.setVisibility(View.VISIBLE);
        dp.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        btnShow.setVisibility(View.GONE);
    }

    //hide input method
    public void hideInput(){
        name.setVisibility(View.GONE);
        dp.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        btnShow.setVisibility(View.VISIBLE);
    }

    //Spinner function
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Filter off
        if (position == 0){
            btnShow.setVisibility(View.VISIBLE);
            lvItem.setEnabled(true);
            adapter = new ProductAdapter(this, R.layout.adapter_view_layout, warrantyList);
            lvItem.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,"Edit/Delete enabled",Toast.LENGTH_SHORT).show();
        }
        //Filter on
        else{

            btnShow.setVisibility(View.GONE);
            lvItem.setEnabled(false);
            int filterNum = Integer.parseInt(parent.getItemAtPosition(position).toString());
            ArrayList<Product> filterList = new ArrayList<Product>();

            Calendar cal = Calendar.getInstance();
            int mYear = cal.get(Calendar.YEAR);
            int mMonth = cal.get(Calendar.MONTH)+1;

            for (int i=0;i < warrantyList.size();i++){
                int a = warrantyList.get(i).getMonth() - mMonth;
                int b = warrantyList.get(i).getYear() - mYear;
                int c = b*12;
                int d = c+a;

                if(d >= filterNum){
                    filterList.add(new Product(warrantyList.get(i).getName(),warrantyList.get(i).getDate(),0,0,0));
                }

            }
            filterAdapter = new ProductAdapter(this, R.layout.adapter_view_layout, filterList);
            lvItem.setAdapter(filterAdapter);
            filterAdapter.notifyDataSetChanged();
            Toast.makeText(this,"Showing Products warranty expiring "+filterNum+" month(s) or later",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}