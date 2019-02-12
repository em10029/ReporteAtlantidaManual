/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */

package reporte.atlantida.manual.utilitario;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 30-ene-2019
 */
public class Util {
    
    /**
     * Limpia el contenido de una tabla.
     * @param modelo 
     */
    public static void limpiarTabla(DefaultTableModel modelo){        
        int filas = modelo.getRowCount() - 1;
        for(int i = filas ;  i >= 0  ; i--){
            modelo.removeRow(i);
        }
    }
    
    
    
}

