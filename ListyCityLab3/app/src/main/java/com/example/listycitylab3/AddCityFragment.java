package com.example.listycitylab3;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class AddCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";
    private AddCityDialogListener listener;
    private City cityToEdit;

    public interface AddCityDialogListener{
        void addCity(City city);
        void onCityEdited (City city);
    }

    public static AddCityFragment newInstance (City city){
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY,city);
        fragment.setArguments(args);
        return fragment;
    }
    //private AddCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context){
        super .onAttach(context);
        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else{
            throw new RuntimeException(context+ " must implement AddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle saveInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String dialogTitle = "Add a city";

        if (getArguments() != null){
            cityToEdit = (City) getArguments().getSerializable(ARG_CITY);
            if (cityToEdit != null){
                editCityName.setText(cityToEdit.getName());
                editProvinceName.setText(cityToEdit.getProvince());
                dialogTitle = "Edit a city";
            }

        }
        return builder
                .setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog,which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityToEdit != null){
                        cityToEdit.setName(cityName);
                        cityToEdit.setProvince(provinceName);
                        listener.onCityEdited(cityToEdit);
                    }else{
                        listener.addCity(new City (cityName, provinceName));
                    }
                })
                .create();
    }
}