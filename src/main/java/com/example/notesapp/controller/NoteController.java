package com.example.notesapp.controller;


import com.example.notesapp.entity.Note;
import com.example.notesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @PostMapping("/notes")
    public String save(@RequestBody Note note) throws ExecutionException, InterruptedException {

        return noteService.saveNote(note);
    }

    @GetMapping("/notes/{name}")
    public Note getOne(@PathVariable String name) throws ExecutionException, InterruptedException {

        return noteService.searchNote(name);
    }

    @GetMapping("/notes")
    public List<Note> getAll() throws ExecutionException, InterruptedException {

        return noteService.getListOfNotes();
    }

    @PutMapping("/notes")
    public String update(@RequestBody Note note) throws ExecutionException, InterruptedException {

        return noteService.updateNote(note);
    }

    @DeleteMapping("/notes/{name}")
    public String delete(@PathVariable String name) throws ExecutionException, InterruptedException {

        return noteService.deleteNote(name);
    }
}
