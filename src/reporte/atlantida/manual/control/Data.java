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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reporte.atlantida.control.Control;
import reporte.atlantida.data.Conexion;
import reporte.atlantida.data.Query;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;

/**
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 04-feb-2019
 */
public class Data {

    public static ArrayList<Empresa> empresas;
    
    public static final String QUERY_SELECT_EMPRESA;
    
    public static final String QUERY_SELECT_SERVICIO;
    
    public static final String QUERY_SELECT_CONCEPTO;
    
    public static final String QUERY_INSERT_CAECEA;
    
    public static final String QUERY_INSERT_CAECEAS;
    
    static {
        empresas = new ArrayList<>();
        QUERY_SELECT_EMPRESA = "SELECT EMPNUM, EMPDES, EMPAVP, EMPANP, EMPAVS, EMPANS, EMPCOR, EMPNUS FROM CAEDTA.CAEEMP WHERE EMPEST = 'A' ORDER BY EMPDES ASC";
        QUERY_SELECT_SERVICIO = "SELECT SERNUM, SERDES, SEREST, SERCOR, SERI1D, SERI1U, SERI2D, SERI2U, SERI3D, SERI3U FROM CAEDTA.CAESER WHERE EMPNUM = ? ORDER BY SERNUM ASC";
        QUERY_SELECT_CONCEPTO = "";
        QUERY_INSERT_CAECEA = "INSERT INTO CAEDTA.CAECEA (CEAFEC, CEAHOR, CEACAN, CEACOR, CEATIP, CEASTA, CEATIF, CEAFEI, CEAFEF, CEAEMP, CEATCO, CEACOE, CEATGE ) VALUES (?, ?, 'F', 9999, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        QUERY_INSERT_CAECEAS = "INSERT INTO CAEDTA.CAECEAS (CEAFEC, CEAHOR, CEACAN, CEACOR, CEASER, CEASERE ) VALUES (?, ?, 'F', 9999, ?, ?)";
        
    }

    public static class Empresa {

        public String identificador;
        public String nombre;
        public ArrayList<Servicio> servicios;        
        public String versionPago;
        public String nivelPago;
        public String versionSaldo;
        public String nivelSaldo;
        public String concepto;
        public String correos;

        public Empresa() {
            this.servicios = new ArrayList<>();
        }
    }

    public static class Servicio {
        public String identificador;
        public String nombre;        
        public boolean estado;
        public boolean seleccionado;
        public String correos;
        public ArrayList<Concepto> conceptos;
        
        public String id1Descripcion;
        public String id1Estado;
        public String id2Descripcion;
        public String id2Estado;
        public String id3Descripcion;
        public String id3Estado;
        
        public Servicio() {
            this.conceptos = new ArrayList<>();
        }
    }
    
    public static class Concepto {
        public String numero;
        public String descripcion;        
        public String estado;
        public String operador;        
    }
    
    public static void cargar(Conexion conexion) {

        PreparedStatement ps1 = null;
        ResultSet rs1 = null;

        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        
        PreparedStatement ps3 = null;
        ResultSet rs3 = null;

        
        if (conexion.abrir()) {
            try {
                ps1 = conexion.getConexion().prepareStatement(QUERY_SELECT_EMPRESA);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {

                    //EMPRESA
                    Empresa empresa = new Empresa();
                    empresa.identificador = rs1.getString("EMPNUM").trim();
                    empresa.nombre = rs1.getString("EMPDES").trim();                    
                    empresa.versionPago = rs1.getString("EMPAVP").trim();
                    empresa.nivelPago = rs1.getString("EMPANP").trim();
                    empresa.versionSaldo = rs1.getString("EMPAVS").trim();
                    empresa.nivelSaldo = rs1.getString("EMPANS").trim();
                    empresa.concepto = rs1.getString("EMPNUS").trim();
                    empresa.correos = rs1.getString("EMPCOR").trim();
                    empresas.add(empresa);
                    

                    //SERVICIOS
                    try {
                        ps2 = conexion.getConexion().prepareStatement(QUERY_SELECT_SERVICIO);
                        ps2.setString(1, empresa.identificador);
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            Servicio servicio = new Servicio();
                            servicio.identificador = rs2.getString("SERNUM").trim();
                            servicio.nombre = rs2.getString("SERDES").trim();
                            servicio.estado = rs2.getString("SEREST").trim().equals("A");
                            servicio.correos = rs2.getString("SERCOR").trim();
                            servicio.seleccionado = false;
                            servicio.id1Descripcion = rs2.getString("SERI1D").trim();
                            servicio.id1Estado = rs2.getString("SERI1U").trim();
                            servicio.id2Descripcion = rs2.getString("SERI2D").trim();
                            servicio.id2Estado = rs2.getString("SERI2U").trim();
                            servicio.id3Descripcion = rs2.getString("SERI3D").trim();
                            servicio.id3Estado = rs2.getString("SERI3U").trim();
                            empresa.servicios.add(servicio);
                            
                            //CONCEPTOS
                            try {
                                ps3 = conexion.getConexion().prepareStatement(Query.SELECT_CONCEPTOS);
                                ps3.setString(1, empresa.identificador);
                                ps3.setString(2, servicio.identificador);
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    Concepto concepto = new Concepto();
                                    concepto.numero = rs3.getString("SCONUM").trim();
                                    concepto.descripcion = rs3.getString("SCODES").trim();
                                    concepto.estado = rs3.getString("SCOEST").trim();
                                    concepto.operador = rs3.getString("SCOOPE").trim();
                                    servicio.conceptos.add(concepto);
                                    
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                Query.cerrar(rs3);
                                Query.cerrar(ps3);
                            }
                                                        
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        Query.cerrar(rs2);
                        Query.cerrar(ps2);
                    }
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(rs1);
                Query.cerrar(ps1);
            }

            conexion.cerrar();
        }

    }

    public static void main(String[] args) throws ReporteAtlantidaExcepcion {
        if (Control.configurar()) {
            //System.out.println("HOLA");
            cargar(Control.conexion);
            System.out.println(empresas.size());
        }

    }

}
