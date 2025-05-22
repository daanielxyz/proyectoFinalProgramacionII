package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import java.io.*;

public class Persistencia {
    public static void serializarObjeto(String ruta, Object objeto) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
        }
    }

    public static Object deserializarObjeto(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}