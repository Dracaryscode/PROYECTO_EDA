

package Models;


public class Candidato {
    private String nombre;
    private String partido;
    private String dni;

    public Candidato(String nombre, String partido, String dni) {
        this.nombre = nombre;
        this.partido = partido;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "nombre='" + nombre + '\'' +
                ", partido='" + partido + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
