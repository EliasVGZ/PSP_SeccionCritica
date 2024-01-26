class Persona {
    int longitud;
    String iniciales;
    String cadena;
    int num_veces;

    public Persona(int longitud, String iniciales, String cadena) {
        this.longitud = longitud;
        this.iniciales = iniciales;
        this.cadena = cadena;
    }

    public Persona(int longitud, String iniciales, String cadena, int num_veces) {
        this.longitud = longitud;
        this.iniciales = iniciales;
        this.cadena = cadena;
        this.num_veces = num_veces;
    }
}

