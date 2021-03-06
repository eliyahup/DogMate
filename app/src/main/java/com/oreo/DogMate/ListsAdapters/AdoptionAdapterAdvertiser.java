package com.oreo.DogMate.ListsAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oreo.DogMate.Objects.Adoption;
import com.oreo.DogMate.R;
import com.oreo.DogMate.Objects.Dog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdoptionAdapterAdvertiser extends ArrayAdapter<Adoption> {

    private Activity context;
    private List<Adoption> ordersList;

    public AdoptionAdapterAdvertiser(Activity context, List<Adoption> ordersList) {
        super(context, R.layout.adoption_item_advertiser, ordersList);
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.adoption_item_advertiser, null, true);
        TextView C_phone = listViewItem.findViewById(R.id.adopterPhone);
        TextView C_name = listViewItem.findViewById(R.id.adopterName);
        TextView C_email = listViewItem.findViewById(R.id.cusEmail);
        TextView C_City = listViewItem.findViewById(R.id.adopterCity);
        //TextView C_House = listViewItem.findViewById(R.id.adopterNumHouse);
        // TextView C_floor = listViewItem.findViewById(R.id.adopterFloor);
        //TextView C_appartment = listViewItem.findViewById(R.id.adopterAppartment);
        TextView date = listViewItem.findViewById(R.id.dateOrder);
        TextView nameDog = listViewItem.findViewById(R.id.dog_name);
        TextView comments = listViewItem.findViewById(R.id.Comment);

        Adoption adoption = ordersList.get(position);
        Dog dog = ordersList.get(position).getDog();
        C_email.setText("מייל המאמץ: " + adoption.getAdopter().getEmail());
        C_phone.setText("טלפון המאמץ: " + adoption.getAdopter().getPhone());
        C_name.setText("שם המאמץ: " + adoption.getAdopter().getFull_name());
        C_City.setText("עיר: " + adoption.getAdopter().getAddress().getCity());
        date.setText("תאריך: " + adoption.getDate());
        nameDog.setText("שם הכלב: " + adoption.getDog().getName());
        comments.setText(adoption.getRequest());

        return listViewItem;
    }
}
