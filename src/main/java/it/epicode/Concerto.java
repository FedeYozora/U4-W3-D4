package it.epicode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "concerti")
public class Concerto extends Evento {

    @Enumerated(EnumType.STRING)
    @Column(name = "genere")
    private GenereMusicale genere;

    @Column(name = "in_streaming")
    private boolean inStreaming;

    // costruttori, getters, setters


    public Concerto(String titolo, String descrizione, LocalDate dataEvento, TipoEvento tipoEvento, Integer numeroMassimoPartecipanti, Location location, GenereMusicale genere, boolean inStreaming) {
        super(titolo, descrizione, dataEvento, tipoEvento, numeroMassimoPartecipanti, location);
        this.genere = genere;
        this.inStreaming = inStreaming;
    }

    public GenereMusicale getGenere() {
        return genere;
    }

    public void setGenere(GenereMusicale genere) {
        this.genere = genere;
    }

    public boolean isInStreaming() {
        return inStreaming;
    }

    public void setInStreaming(boolean inStreaming) {
        this.inStreaming = inStreaming;
    }

    @Override
    public String toString() {
        return "Concerto{" +
                "genere=" + genere +
                ", inStreaming=" + inStreaming +
                '}';
    }
}