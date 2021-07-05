package sg.edu.rp.c346.id20041162.sa_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private static final String TAG = "ProductAdapter";
    private Context mContext;
    int mResource;

    public ProductAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String date = getItem(position).getDate();
        int year = getItem(position).getYear();
        int month = getItem(position).getMonth();
        int day = getItem(position).getDay();

        Product product = new Product(name,date,year,month,day);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        tvDate.setText(date);

        return convertView;
    }
}
