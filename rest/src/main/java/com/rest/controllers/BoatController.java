package com.rest.controllers;

import com.domain.interfaces.rest.controllers.IBoatController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.domain.interfaces.logic.handlers.IUserHandler;
import com.domain.interfaces.rest.IRestCRUD;
import com.domain.models.Boat;
import com.domain.models.User;
import com.logic.handlers.BoatHandler;
import com.google.gson.Gson;
import com.logic.handlers.BoatHandler;
import com.persistence.repositories.IBoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/boats")
public abstract class BoatController implements IBoatController {

    private BoatHandler handler;

    Gson gson = new Gson();

    public BoatController() {
    }

    @Autowired
    public BoatController(BoatHandler handler) {
        this.handler = handler;
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Boat JsonEntity) {
        System.out.println("HALLO");
        System.out.println(JsonEntity);
        Boat result = handler.create(JsonEntity);
//        Boat result = handler.create(gson.fromJson(JsonEntity, Project.class));
        return new ResponseEntity<>(result,HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity read(Long id) {
        Boat result = handler.read(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity(null, HttpStatus.valueOf(400));
        }
    }



    @Override
    public ResponseEntity readAll() {
        List<Boat> boats = handler.readAll();
        List<Boat> tmpboat = new ArrayList<>();
        for (Boat boat : boats) {
            tmpboat.add(boat);
        }
        return new ResponseEntity<>(tmpboat,HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity update(String JsonEntity) {
        Boat boat = handler.update(gson.fromJson(JsonEntity,Boat.class));
        return new ResponseEntity<>(boat, HttpStatus.valueOf(200));
    }

    @Override
    public ResponseEntity delete(Long id) {
        if (handler.delete(id)){
            return new ResponseEntity<>(true, HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity<>(false, HttpStatus.valueOf(404));
        }
    }

//    @Override
//    public ResponseEntity readByUser(String jsonUser) {
//        User user = gson.fromJson(jsonUser, User.class);
//        return new ResponseEntity<>(handler.readByUser(user), HttpStatus.valueOf(200));
//    }
}