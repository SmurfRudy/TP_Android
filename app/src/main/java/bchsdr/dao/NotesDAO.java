package bchsdr.dao;

/**
 * Created by Rudy_DEAL on 12/10/2017.
 */

import android.content.Context;

import java.text.ParseException;
import java.util.List;

import bchsdr.model.Note;


public class NotesDAO {
    private static NotesDAO notesDAO;

    public static NotesDAO getInstance() {
        if (notesDAO == null){
            notesDAO = new NotesDAO();
        }
        return notesDAO;
    }

    public List<Note> getDBNotes(Context context){
        try {
            return ConnectionSQLiteHelper.getInstance(context).getDBNotesDAO();
        } catch (ParseException e) {
            return null;
        }

    }

    public void editNote(Context context, Note note) {
        if (note.getId_notes() < 0) {
            ConnectionSQLiteHelper.getInstance(context).insertNote(note);
        }
        else{
            ConnectionSQLiteHelper.getInstance(context).updateNote(note);
        }
    }

}