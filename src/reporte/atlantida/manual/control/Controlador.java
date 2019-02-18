/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.manual.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.control.Control;
import reporte.atlantida.control.Proceso;
import reporte.atlantida.data.Conexion;
import reporte.atlantida.data.Query;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.manual.vista.Vista;
import reporte.atlantida.utilitario.Configuracion;
import reporte.atlantida.utilitario.Util;

/**
 *
 * 
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 31-ene-2019
 */
public class Controlador {

    /**
     * Conexión auxiliar de aplicación.
     */
    public static Conexion DB2;

    /**
     * Prepara la configuración de la aplicación, carga variables de
     * propiedades, crea el directorio de trabajo, carga la metadata y establece
     * una conexión auxiliar.
     *
     * @return boolean
     * @throws ReporteAtlantidaExcepcion
     */
    public static boolean configurar() throws ReporteAtlantidaExcepcion {
        boolean config = false;
        Control.estado = "F";
        if (Control.configurar()) {
            //******** Conexion DB2 de aplicacion ******** //
            DB2 = new Conexion(Configuracion.CONEXION_URL,
                    Configuracion.CONEXION_USER,
                    Configuracion.CONEXION_PASSWORD);

            Data.cargar(Control.conexion);
            config = true;
        }
        return config;
    }
    
    /**
     * Registra en el archivo log, el proceso de petición.
     * @param reporte 
     */
    public static void registrarProceso(Reporte reporte){
        String info = Util.info(reporte);
        System.out.println(info);
        Control.registrarProceso(info);
    }

    //********************************************************************************//
    /**
     * Pantalla de reportes fallidos.
     */
    private final Vista vista;


    /**
     * Inicializador de aplicación: configuración, procesamiento y control de
     * ejecución.
     *
     * @param vista
     */
    public Controlador(Vista vista){
        this.vista = vista;        
        this.buscar();
    }

    /**
     * Inicio ciclo de ejecución.
     */
    private void buscar() {
        while (true) {
            this.iniciar();
            //PAUSAR-MOTOR
            try {
                Runtime garbage = Runtime.getRuntime();
                garbage.gc();
                //System.out.println("Espera... Tiempo: " + Configuracion.CONTROL_TIEMPO + " minutos");
                Thread.sleep(Configuracion.CONTROL_TIEMPO * 60000); //Pausa en milisegundos
            } catch (InterruptedException ex) {
                Logger.getLogger(reporte.atlantida.control.Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Búsqueda y procesamiento de peticiones de reportes.
     */
    private void iniciar() {
        //Busqueda de solicitudes
        if (Control.conexion.abrir()) {
            //INICIO-BUQUEDA
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = Control.conexion.getConexion().prepareStatement(Query.SELECT_REPORTE);
                ps.setString(1, Control.estado);
                rs = ps.executeQuery();

                while (rs.next()) {
                    //Validacion de existencia
                    String fecha = rs.getString("CEAFEC").trim();
                    String hora = rs.getString("CEAHOR").trim();
                    String canal = rs.getString("CEACAN").trim();
                    String correlativo = rs.getString("CEACOR").trim();
                    String key = fecha + '-' + hora + '-' + canal + '-' + correlativo;
                    if (!this.vista.reportes.containsKey(key)) {

                        //Contruir peticion de reporte                                                
                        Reporte reporte = Control.getReporte(rs); //Obtiene la estructura de la peticion
                        this.vista.reportes.put(key, reporte); //Agregandolo al diccionario
                                                
                        //***********************************************
                        this.vista.agregarReporte(reporte); //Agregandolo a la tabla                        
                        //***********************************************

                        //***********************************************
                        //Procesar peticion 
                        Proceso.procesar(Control.conexion, reporte); 
                        registrarProceso(reporte);
                        //***********************************************
                        
                        //***********************************************
                        this.vista.actualizarReporte(reporte); //Actualiza la tabla despues del proceso                        
                        //***********************************************
                        
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs);
                Query.cerrar(ps);
            }
            Control.conexion.cerrar();
        }
    }

}
