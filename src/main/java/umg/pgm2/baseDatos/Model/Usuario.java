package umg.pgm2.baseDatos.Model;

public class Usuario {
    private int idusuario;
    private String carne;
    private String nombre;
    private String correo;
    private String seccion;
    private long telegramid;
    private String activo;

    public Usuario(int idusuario, String carne, String nombre, String correo, String seccion, long telegramid, String activo) {
        this.idusuario = idusuario;
        this.carne = carne;
        this.nombre = nombre;
        this.correo = correo;
        this.seccion = seccion;
        this.telegramid = telegramid;
        this.activo = activo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public long getTelegramid() {
        return telegramid;
    }

    public void setTelegramid(long telegramid) {
        this.telegramid = telegramid;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
