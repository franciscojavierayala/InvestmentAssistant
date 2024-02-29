package paquete1;

public class Activo {
    private static int contadorActivos = 0;
    private int id;
    private double precio;
    private double inversion;
    private double comision;
    private String fecha;

    public Activo(double precio, double inversion, double comision, String fecha) {
        this.id = ++contadorActivos;
        this.precio = precio;
        this.inversion = inversion;
        this.comision = comision;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public double getInversionBruta() {
        return inversion;
    }

    public double getComision() {
        return comision;
    }

    public double getInversionNeta() {
        return inversion - (inversion * (comision / 100));
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Activo " + id + " - Precio: " + precio + ", Inversión Bruta: " + inversion + ", Inversión Neta: " + getInversionNeta() + ", Comisión: " + comision + "%, Fecha: " + fecha;
    }
    
    
    
}
