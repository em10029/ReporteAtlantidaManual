/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.manual.main;

import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.manual.control.Controlador;
import reporte.atlantida.manual.vista.Vista;

/**
 *
 * Clase principla de aplicación.
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 30-ene-2019
 */
public class Main {

    /**
     * Función de ejecución.
     * @param args the command line arguments
     * @throws reporte.atlantida.estructura.ReporteAtlantidaExcepcion
     */
    public static void main(String[] args) throws ReporteAtlantidaExcepcion {

        Controlador.configurar();

        //--------- VISTA -------------//        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        final Vista vista = new Vista();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                vista.setVisible(true);
            }
        });

        //--------- CONTROLADOR -------------//
        Controlador controlador = new Controlador(vista);

    }

}
