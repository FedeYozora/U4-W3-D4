package it.epicode;

import javax.persistence.*;
import java.util.List;

public class EventoDao {

    private final EntityManager em;

    public EventoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Evento evento) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(evento);
        transaction.commit();
        System.out.println("Evento " + evento.getTitolo() + " generato!");
    }

    public void delete(Long id) {
        Evento found = this.findById(id);
        if(found != null){
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Evento " + found.getTitolo() + " cancellato!");
        } else {
            System.out.println("L'Evento con id " + id + " non è stato trovato");
        }
    }

    private Evento findById(Long id) {
        return em.find(Evento.class, id);
    }
    // nuovi metodi

    public List<Concerto> getConcertiInStreaming(boolean inStreaming) {
        String jpql = "SELECT c FROM Concerto c WHERE c.inStreaming = :inStreaming";
        TypedQuery<Concerto> query = em.createQuery(jpql, Concerto.class);
        query.setParameter("inStreaming", inStreaming);
        return query.getResultList();
    }

    public List<Concerto> getConcertiPerGenere(GenereMusicale genere) {
        String jpql = "SELECT c FROM Concerto c WHERE c.genere = :genere";
        TypedQuery<Concerto> query = em.createQuery(jpql, Concerto.class);
        query.setParameter("genere", genere);
        return query.getResultList();
    }

    public List<PartitaDiCalcio> getPartiteVinteInCasa() {
        String jpql = "SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraDiCasa";
        return em.createQuery(jpql, PartitaDiCalcio.class).getResultList();
    }

    public List<PartitaDiCalcio> getPartiteVinteInTrasferta() {
        String jpql = "SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente = p.squadraOspite";
        return em.createQuery(jpql, PartitaDiCalcio.class).getResultList();
    }

    public List<PartitaDiCalcio> getPartitePareggiate() {
        String jpql = "SELECT p FROM PartitaDiCalcio p WHERE p.squadraVincente IS NULL";
        return em.createQuery(jpql, PartitaDiCalcio.class).getResultList();
    }

    public List<GaraDiAtletica> getGareDiAtleticaPerVincitore(Persona vincitore) {
        String jpql = "SELECT g FROM GaraDiAtletica g WHERE g.vincitore = :vincitore";
        TypedQuery<GaraDiAtletica> query = em.createQuery(jpql, GaraDiAtletica.class);
        query.setParameter("vincitore", vincitore);
        return query.getResultList();
    }

    public List<GaraDiAtletica> getGareDiAtleticaPerPartecipante(Persona partecipante) {
        String jpql = "SELECT g FROM GaraDiAtletica g JOIN g.atleti a WHERE a = :partecipante";
        TypedQuery<GaraDiAtletica> query = em.createQuery(jpql, GaraDiAtletica.class);
        query.setParameter("partecipante", partecipante);
        return query.getResultList();
    }

    public List<Evento> getEventiSoldOut() {
        String jpql = "SELECT e FROM Evento e WHERE e.numeroMassimoPartecipanti = (SELECT COUNT(p) FROM Partecipazione p WHERE p.evento = e)";
        return em.createQuery(jpql, Evento.class).getResultList();
    }
}
