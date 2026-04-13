package pojonormalizado.modelo;

public class Curso {

    private String codigo;
    private String codigoNormalizado;
    private int horas;
    private String ciclo;

    // getters y setters


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoNormalizado() {
        return codigoNormalizado;
    }

    public void setCodigoNormalizado(String codigoNormalizado) {
        this.codigoNormalizado = codigoNormalizado;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
}

