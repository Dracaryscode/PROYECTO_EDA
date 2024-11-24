

package Models;


public class Reporte {
    private String nombreEleccion;
    private int totalVotos;
    private int votosNulos;
    private int votosBlancos;
    private String ganador;
    private String fechaGeneracion;

    public Reporte(String nombreEleccion, int totalVotos, int votosNulos, int votosBlancos, String ganador, String fechaGeneracion) {
        this.nombreEleccion = nombreEleccion;
        this.totalVotos = totalVotos;
        this.votosNulos = votosNulos;
        this.votosBlancos = votosBlancos;
        this.ganador = ganador;
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getNombreEleccion() {
        return nombreEleccion;
    }

    public void setNombreEleccion(String nombreEleccion) {
        this.nombreEleccion = nombreEleccion;
    }

    public int getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(int totalVotos) {
        this.totalVotos = totalVotos;
    }

    public int getVotosNulos() {
        return votosNulos;
    }

    public void setVotosNulos(int votosNulos) {
        this.votosNulos = votosNulos;
    }

    public int getVotosBlancos() {
        return votosBlancos;
    }

    public void setVotosBlancos(int votosBlancos) {
        this.votosBlancos = votosBlancos;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "nombreEleccion='" + nombreEleccion + '\'' +
                ", totalVotos=" + totalVotos +
                ", votosNulos=" + votosNulos +
                ", votosBlancos=" + votosBlancos +
                ", ganador='" + ganador + '\'' +
                ", fechaGeneracion='" + fechaGeneracion + '\'' +
                '}';
    }
}

