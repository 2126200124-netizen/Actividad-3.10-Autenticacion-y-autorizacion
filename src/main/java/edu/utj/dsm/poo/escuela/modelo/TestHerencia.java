//Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

/**
 *
 * @author jorgi
 */

import java.util.Scanner;
public class TestHerencia {
    private Alumno alum;
        private Docente doc;
        private administrador admin;
        
        public void capturaDatosAdmin(){
            Scanner entrada = new Scanner(System.in);{
                System.out.println("\n Captura Datos de Administrativo");
                System.out.println("Nombre: ");
                String nom = entrada.nextLine();
                System.out.println("Apellido: ");
                String ape = entrada.nextLine();
                System.out.println("Edad :");
                int eda = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Turno: ");
                String tur = entrada.nextLine();
                System.out.println("Area: ");
                String are = entrada.nextLine();
                System.out.println("Telefono: ");
                String tel = entrada.nextLine();
                this.admin = new administrador(nom, ape, eda, tur, are, tel);
            }
        }
        public void capturaDatosDoc(){
            Scanner entrada = new Scanner(System.in);{
                System.out.println("\n Captura Datos del Docente");
                System.out.println("Nombre: ");
                String nom = entrada.nextLine();
                System.out.println("Apellido: ");
                String ape = entrada.nextLine();
                System.out.println("Edad :");
                int eda = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Especialidad: ");
                String esp = entrada.nextLine();
                System.out.println("Num de docente: ");
                String num = entrada.nextLine();
                System.out.println("Telefono: ");
                String tel = entrada.nextLine();
                this.doc = new Docente(nom, ape, eda, esp, num, tel);
        
    }
        }
        public void capturaDatosAlumn(){
            Scanner entrada = new Scanner(System.in);{
                System.out.println("\n Captura Datos del Alumno");
                System.out.println("Nombre: ");
                String nom = entrada.nextLine();
                System.out.println("Apellido: ");
                String ape = entrada.nextLine();
                System.out.println("Edad :");
                int eda = entrada.nextInt();
                System.out.println("Grado: ");
                String gra = entrada.nextLine();
                entrada.nextLine();
                System.out.println("Matricula: ");
                String mat = entrada.nextLine();
                System.out.println("Calificacion: ");
                int cal = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Telefono: ");
                String tel = entrada.nextLine();
                this.alum = new Alumno(nom, ape, eda, tel, mat, gra, cal);
}
        
        }
        public void mostarDatosAdmin(){
            System.out.println("Datos del administrador registrado");
            System.out.println("Nombre: "+admin.getNombre());
            System.out.println("Apellido: "+admin.getApellido());
            System.out.println("Edad: "+admin.getEdad());
            System.out.println("Turno: "+admin.getTurno());
            System.out.println("Area: "+admin.getArea());
            System.out.println("Telefono: "+admin.getTelefono());
        }
        public void mostrarDatosDoc(){
            System.out.println("Datos del docente registrado");
            System.out.println("Nombre: "+doc.getNombre());
            System.out.println("Apellido: "+doc.getApellido());
            System.out.println("Edad: "+doc.getEdad());
            System.out.println("Especialidad: "+doc.getespecialidad());
            System.out.println("Num del docente: "+doc.getNumDocente());
            System.out.println("Telefono: "+doc.getTelefono());
        }
        public void mostrarDatosAlum(){
            System.out.println("Datos del alumno registrado");
            System.out.println("Nombre: "+alum.getNombre());
            System.out.println("Apellido: "+alum.getApellido());
            System.out.println("Edad: "+alum.getEdad());
            System.out.println("Matricula: "+alum.getMatricula());
            System.out.println("Grado: "+alum.getGrado());
            System.out.println("Calificacion: "+alum.getCalificacion());
            System.out.println("Telefono: "+alum.getTelefono());
        }
        
        
        // Menu
        public static void main(String[] args) {
        TestHerencia test = new TestHerencia();
        Scanner entrada = new Scanner(System.in);
        int opcion;
        
            do {
                System.out.println("Menu Interactivo");
                System.out.println(" 1. Docente");
                System.out.println(" 2. Administrativo");
                System.out.println(" 3. Alumno");
                System.out.println(" 0. Salida del programa");
                System.out.println("Seleccione la opcion a utilizar");
                opcion=entrada.nextInt();
                
                switch (opcion) {
                    case 0:
                        System.out.println("Salida del programa");
                        break;
                    case 1:
                        test.capturaDatosDoc();
                        test.mostrarDatosDoc();
                        break;
                    case 2:
                        test.capturaDatosAdmin();
                        test.mostarDatosAdmin();
                        break;
                    case 3:
                        test.capturaDatosAlumn();
                        test.mostrarDatosAlum();
                        break;
                    default:
                        System.out.println("Opcion no valida");
                        break;
                }
                
            } while (opcion !=0);
            System.out.println("Morones Torres Jorge Arnulfo");
 
    }
        
}