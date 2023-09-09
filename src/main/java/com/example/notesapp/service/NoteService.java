package com.example.notesapp.service;

import com.example.notesapp.entity.Note;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class NoteService {
    private static final String COLLECTION_NAME = "notes";

    public String saveNote(Note note) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApi = dbFirestore.collection(COLLECTION_NAME).document(note.getTitle()).set(note);

        return collectionApi.get().getUpdateTime().toString();
    }

    public List<Note> getListOfNotes() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<Note> noteList = new ArrayList<>();
        Note note = null;

        while (iterator.hasNext()) {
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();

            note = document.toObject(Note.class);
            noteList.add(note);

        }
        return noteList;

    }

    public Note searchNote(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Note note = null;
        if (document.exists()) {
            note = document.toObject(Note.class);
            return note;
        } else {
            return null;
        }

    }

    public String updateNote(Note note) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApi = dbFirestore.collection(COLLECTION_NAME).document(note.getTitle()).set(note);

        return collectionApi.get().getUpdateTime().toString();
    }

    public String deleteNote(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApi = dbFirestore.collection(COLLECTION_NAME).document(name).delete();

        return "Note with title " + name + " has been deleted successfully";
    }
}
