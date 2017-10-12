package bchsdr;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import bchsdr.adapter.JourneyNoteListAdapter;
import bchsdr.dao.JourneysSQLiteHelper;
import bchsdr.maps.MapsActivity;
import bchsdr.model.Journey;
import bchsdr.model.Note;
import bchsdr.tp_android_1.R;
import bchsdr.tp_android_1.databinding.JourneyDetailBinding;
import bchsdr.viewModel.JourneyViewModel;

/**
 * Created by Maxime on 09/10/2017.
 */

public class JourneyDetail extends Fragment {
    private Journey journey;
    private List<Note> notes;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        this.journey = null;
        JourneyDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.journey_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.journey = (Journey) bundle.getSerializable("journey");
            binding.setJvm(new JourneyViewModel(this.journey, getActivity()));
        }else{
            binding.setJvm(new JourneyViewModel(getActivity()));
        }
        binding.setHandler(this);

        getNotes();
        binding.journeyNoteList.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.journeyNoteList.setAdapter(new JourneyNoteListAdapter(notes, getActivity()));
        return binding.getRoot();
    }

    public void close(View view) {
        JourneysFragment journeysFragment = new JourneysFragment();
        // Debut du changement de fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack(null);
        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeysFragment);
        fragmentTransaction.commit();
    }

    private Calendar stringF2ToCal(String stringF2){
        DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime( sdf.parse(stringF2));
        } catch (ParseException e) {
           ;
        }
        return cal;
    }


    public void saveJourney (int id, String name, String start_date, String end_date) {
        try{
            String description = "TO DO";
            Journey newJourney =new Journey(name, stringF2ToCal(start_date), stringF2ToCal(end_date), id, description);
            JourneysSQLiteHelper.getInstance(getActivity()).edit_journey(newJourney);
            this.close(getView());
        }

        catch (Exception e){
            this.close(getView());
        }

    }

    public void addNote(View view) {

    }

    public void showMap(View view) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        getActivity().startActivity(intent);
    }

    public void getNotes(){
        this.notes = new ArrayList<Note>();
    }

    public void showDatePickerDialog(View view){
        final EditText editText = (EditText) view;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = new GregorianCalendar();
                calendar.set(year, month, day);
                DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                        Locale.getDefault());
                String selectedDate = sdf.format(calendar.getTime());
                editText.setText(selectedDate);
                //editText.setText(day + "/" + month +"/"+ year);
            }
        };
        Calendar calendar = Calendar.getInstance();
        if (!editText.getText().toString().equals("")) {
                calendar = convertToCalendar(editText.getText().toString());

        }
        DatePickerDialog pickerDialog = new DatePickerDialog(editText.getContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    /*String toStringDate(Calendar cal){
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(cal.getTime());
    }*/

    Calendar convertToCalendar (String date){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e2) {
            System.out.println("ERROR -- Date Conversion");
        }


        return cal;
    }
}


