package com.example.voivodeshipcities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@RequestMapping("api/voivodeshipcities")
public class VCControler {

    @Autowired
    VCRepository vcRepository;

    private static final String[] NAMEARRAY = {"Białystok", "Bydgoszcz", "Gdańsk", "Gorzów Wielkopolski", "Katowice", "Kielce",
            "Kraków", "Lublin", "Łódź", "Olsztyn", "Opole" ,"Poznań", "Rzeszów","Szczecin", "Toruń", "Warszawa", "Wrocław",
            "Zielona Góra"};

    private static final String[] VOIVODESHIPARRAY = {"podlaskie", "kujawsko-pomorskie", "pomorskie", "lubuskie", "śląskie",
            "świętokrzyskie", "małopolskie", "lubelskie", "łódzkie", "warmińsko-mazurskie", "opolskie", "wielkopolskie",
            "podkarpackie", "zachodniopomorskie", "kujawsko-pomorskie", "mazowieckie", "dolnośląskie", "lubuskie"};

    private static final int[] NUMBERRESIDENTSARRAY = {297459, 350178, 466631, 124295, 294510, 195774, 774839, 339682, 682679, 173125,
            128208, 535802, 195734, 402100, 201798, 1777972, 641607, 140874};

    @PostConstruct
    private void set(){

        VoivodeshipCities vArray[] = new VoivodeshipCities[NAMEARRAY.length];

        for (int i = 0; i<vArray.length; i++){
            vArray[i] = new VoivodeshipCities();
            vArray[i].setName(NAMEARRAY[i]);
            vArray[i].setVoivodeship(VOIVODESHIPARRAY[i]);
            vArray[i].setNumberResidents(NUMBERRESIDENTSARRAY[i]);
            this.vcRepository.save(vArray[i]);
        }

    }

    @GetMapping
    private ArrayList<VoivodeshipCities> getAllItems(){
        return (ArrayList<VoivodeshipCities>) this.vcRepository.findAll();
    }

    @GetMapping("/{id}")
    private Optional<VoivodeshipCities> getItem(@PathVariable long id) {
        return this.vcRepository.findById(id);
    }

    @GetMapping("/select")
    private ArrayList<VoivodeshipCities> getItems(@RequestParam("from") long from, @RequestParam("to") long to) {
        return (ArrayList<VoivodeshipCities>) this.vcRepository.findAllById(LongStream.rangeClosed(from, to).boxed().collect(Collectors.toList()));
    }

    @PostMapping
    private VoivodeshipCities addItem(@RequestBody VoivodeshipCities voivodeshipCities){
        return this.vcRepository.save(voivodeshipCities);
    }

    @PutMapping
    private VoivodeshipCities updateItem(@RequestBody VoivodeshipCities voivodeshipCities) {
        return this.vcRepository.save(voivodeshipCities);
    }
    @DeleteMapping("/delete/{id}")
    private void deleteItem(@PathVariable long id){
        this.vcRepository.deleteById(id);
    }
}

