package it.epicode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestioneEventi");

    public static void main(String[] args) {

        EntityManager em = emf.createEntityManager();

        EventoDao eventDao = new EventoDao(em);
        LocationDao locationDao = new LocationDao(em);
        PersonaDao personDao = new PersonaDao(em);
        PartecipazioneDao participationDao = new PartecipazioneDao(em);


        // crea Location
        Location location = new Location("Casa di Mario", "Via Roma 12");
        locationDao.save(location);

        // crea Persona
        Persona person = new Persona("Mario", "Placeholder", "test@test.com", LocalDate.of(2000, 7, 15), "male");
        Persona person2 = new Persona("Luigi", "Placeholder", "test@test.com", LocalDate.of(2001, 5, 8), "male");
        Persona person3 = new Persona("Peach", "Placeholder", "test@test.com", LocalDate.of(2000, 2, 20), "female");

        personDao.save(person);
        personDao.save(person2);
        personDao.save(person3);

        // crea evento GaraDiAtletica e aggiungi atleti
        List<Persona> atleti = new ArrayList<>();
        atleti.add(person);
        atleti.add(person2);
        atleti.add(person3);

        // crea Evento
//        Evento event = new Evento("Evento Test", "Descrizione Test", LocalDate.of(2023, 2, 1), TipoEvento.PRIVATO, 5, location);
        GaraDiAtletica garaDiAtletica = new GaraDiAtletica("Gara Di Atletica", "Descrizione Gara Di Atletica", LocalDate.of(2023, 3, 1), TipoEvento.PUBBLICO, 10, location, atleti, person2);
        Concerto concerto = new Concerto("Concerto", "Descrizione Concerto", LocalDate.of(2023, 4, 15), TipoEvento.PUBBLICO, 2000, location, GenereMusicale.Rock, true);
        PartitaDiCalcio partitaDiCalcio = new PartitaDiCalcio("Partita Di Calcio", "Descrizione Partita Di Calcio", LocalDate.of(2023, 5, 30), TipoEvento.PUBBLICO, 50000, location, "Milan", "Juve", "Milan", 3, 2);
//        eventDao.save(event);
        eventDao.save(garaDiAtletica);
        eventDao.save(concerto);
        eventDao.save(partitaDiCalcio);


        // crea Partecipazioni
//        Partecipazione participation = new Partecipazione(person, event, StatoPartecipazione.CONFERMATA);
        Partecipazione participation1 = new Partecipazione(person, garaDiAtletica, StatoPartecipazione.CONFERMATA);
        Partecipazione participation2 = new Partecipazione(person2, concerto, StatoPartecipazione.DA_CONFERMARE);
        Partecipazione participation4 = new Partecipazione(person3, concerto, StatoPartecipazione.CONFERMATA);
        Partecipazione participation3 = new Partecipazione(person3, partitaDiCalcio, StatoPartecipazione.CONFERMATA);
//        participationDao.save(participation);
        participationDao.save(participation1);
        participationDao.save(participation2);
        participationDao.save(participation3);
        participationDao.save(participation4);

        // print di Partecipazioni
//        System.out.println(participation);

        // print di partecipazioni da confermare per l'evento
        List<Partecipazione> participationsToConfirm = participationDao.getPartecipazioniDaConfermarePerEvento(concerto);
        for (Partecipazione p : participationsToConfirm) {
            System.out.println(p);
        }

        // close Entity Manager
        em.close();
        emf.close();
    }
}
