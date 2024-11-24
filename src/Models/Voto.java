

package Models;


public class Voto {
    private String idMesa;
    private String dniVotante;
    private String candidato;
    private String fecha;

    public Voto(String idMesa, String dniVotante, String candidato, String fecha) {
        this.idMesa = idMesa;
        this.dniVotante = dniVotante;
        this.candidato = candidato;
        this.fecha = fecha;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getDniVotante() {
        return dniVotante;
    }

    public void setDniVotante(String dniVotante) {
        this.dniVotante = dniVotante;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "idMesa='" + idMesa + '\'' +
                ", dniVotante='" + dniVotante + '\'' +
                ", candidato='" + candidato + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
