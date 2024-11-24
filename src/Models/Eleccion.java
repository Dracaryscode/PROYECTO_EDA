

package Models;

import TDA.ListaEnlazada;
import TDA.Nodo;

public class Eleccion {
    private String nombre;
    private String fecha;
    private String tipo;
    private ListaEnlazada<Candidato> candidatos;
    private ListaEnlazada<Voto> votos;

    public Eleccion(String nombre, String fecha, String tipo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipo = tipo;
        this.candidatos = new ListaEnlazada<>();
        this.votos = new ListaEnlazada<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarCandidato(Candidato candidato) {
        candidatos.agregarFinal(candidato);
    }

    public void registrarVoto(Voto voto) {
        votos.agregarFinal(voto);
    }

    public int contarVotosTotales() {
        int contador = 0;
        Nodo<Voto> actual = votos.getCabeza();
        while (actual != null) {
            contador++;
            actual = actual.getPtr();
        }
        return contador;
    }

    public int contarVotosNulos() {
        int nulos = 0;
        Nodo<Voto> actual = votos.getCabeza();
        while (actual != null) {
            boolean valido = false;
            Nodo<Candidato> candidato = candidatos.getCabeza();
            while (candidato != null) {
                if (actual.getData().getCandidato().equalsIgnoreCase(candidato.getData().getNombre())) {
                    valido = true;
                    break;
                }
                candidato = candidato.getPtr();
            }
            if (!valido) nulos++;
            actual = actual.getPtr();
        }
        return nulos;
    }

    public int contarVotosBlancos() {
        int blancos = 0;
        Nodo<Voto> actual = votos.getCabeza();
        while (actual != null) {
            if (actual.getData().getCandidato().isEmpty()) blancos++;
            actual = actual.getPtr();
        }
        return blancos;
    }

    public Candidato obtenerGanador() {
        if (candidatos.estaVacia()) return null;

        Candidato ganador = null;
        int maxVotos = 0;
        Nodo<Candidato> actual = candidatos.getCabeza();
        while (actual != null) {
            int votos = contarVotosPorCandidato(actual.getData().getNombre());
            if (votos > maxVotos) {
                maxVotos = votos;
                ganador = actual.getData();
            }
            actual = actual.getPtr();
        }
        return ganador;
    }

    public int contarVotosPorCandidato(String nombreCandidato) {
        int contador = 0;
        Nodo<Voto> actual = votos.getCabeza();
        while (actual != null) {
            if (actual.getData().getCandidato().equalsIgnoreCase(nombreCandidato)) {
                contador++;
            }
            actual = actual.getPtr();
        }
        return contador;
    }
}
