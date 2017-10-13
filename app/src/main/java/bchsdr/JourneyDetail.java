package bchsdr;

import android.app.DatePickerDialog;
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
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import bchsdr.adapter.JourneyNoteListAdapter;
import bchsdr.dao.JourneysDAO;
import bchsdr.dao.NotesDAO;
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
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
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
            JourneysDAO.getInstance().editJourney(getActivity(),newJourney);
            this.close(getView());
        }

        catch (Exception e){
            this.close(getView());
        }

    }

    public void addNote(View view) {
        Note newNote = new Note();
        newNote.setTitle("New Note " + (this.notes.size() + 1));
        JourneyNote journeyNote = new JourneyNote();
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Pour passer des paramètres on utilise un bundle
        Bundle bundle = new Bundle();
        // On passe un objet (qui doit etre serializable
        bundle.putSerializable("note", newNote);
        // On passe le bundle (avec l'objet) au nouveau fragment
        journeyNote.setArguments(bundle);

        // à comprendre - on sauvegarde l'état lorsque le fragment que l'on rajoute sera supprimé
        fragmentTransaction.addToBackStack("journey_detail");
        // On remplace le fragment dans le container par le nouveau fragment
        fragmentTransaction.replace(R.id.fragment_container, journeyNote);

        fragmentTransaction.commit();
    }

    public void showMap(View view) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        getActivity().startActivity(intent);
    }

    public void getNotes(){
        this.notes = NotesDAO.getInstance().getDBNotes(getActivity());
    }

    public void showDatePickerDialog(View view){
        final TextView textView = (TextView) view;

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = new GregorianCalendar();
                calendar.set(year, month, day);
                DateFormat sdf = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM,
                        Locale.getDefault());
                String selectedDate = sdf.format(calendar.getTime());
                textView.setText(selectedDate);
                //editText.setText(day + "/" + month +"/"+ year);
            }
        };
        Calendar calendar = Calendar.getInstance();
        if (!textView.getText().toString().equals("")) {
                calendar = convertToCalendar(textView.getText().toString());

        }
        DatePickerDialog pickerDialog = new DatePickerDialog(textView.getContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

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

    public Journey getJourney (){
        return this.journey;
    }
}


