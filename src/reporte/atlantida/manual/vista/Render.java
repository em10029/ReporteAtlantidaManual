/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */

package reporte.atlantida.manual.vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Erick Fabricio Martínez Castellanos
 * (<a href='mailto:efmartinez@bancatlan.hn'>efmartinez@bancatlan.hn</a>)
 * @version 1.0 31-ene-2019
 */
public class Render extends DefaultTableCellRenderer{
    
    private static final Color VERDE = new Color(204,255,204);
    private static final Color ROJO = new Color(255,153,153);
    
    private final int columna; //Columna a evaluar
    //private final String valor; //El valor correcto para ser coloreado en verde
    private final boolean colorear; //Indica si se coloreara la fila

    /*
    public Render(int columna, String valor, boolean colorear) {
        this.columna = columna;
        this.valor = valor;
        this.colorear = colorear;
    }
    
    //Para que acepte labels y modifique el color de las filas
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        //Alinear encabezado de tabla
        //Cabezera
        TableCellRenderer renderHeadertable = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabeltable = (JLabel) renderHeadertable;
        headerLabeltable.setHorizontalAlignment(JLabel.CENTER);
                
        //Aceptar JLabel 
        //Si se quiere otro componente solo se cambia JLabel
        if(value instanceof JLabel){
            JLabel label = (JLabel) value;
            label.setHorizontalAlignment(JLabel.CENTER); //Alineacion horizontal
            label.setVerticalAlignment(JLabel.CENTER); //Alineacion vertical
            return label;
        }
        
        //Evaluacion de celda
        if(this.colorear){
            //Evalua el color de la fila
            if (table.getValueAt(row, this.columna).toString().equals(this.valor)){
               this.setOpaque(true);
               this.setBackground(VERDE);
               this.setForeground(Color.BLACK);
            } else {
               this.setOpaque(true);
               this.setBackground(ROJO);
               this.setForeground(Color.BLACK);
            }
        }
        
        setHorizontalAlignment(SwingConstants.CENTER);//Alineaciones de celdas

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    */
    
    private Object obj;
    
    public Render(int columna, Object valor, boolean colorear) {
        this.columna = columna;
        this.obj = valor;
        this.colorear = colorear;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        //Alinear encabezado de tabla       
        TableCellRenderer renderHeadertable = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabeltable = (JLabel) renderHeadertable;
        headerLabeltable.setHorizontalAlignment(JLabel.CENTER);
                
        //Aceptar JLabel 
        //Si se quiere otro componente solo se cambia JLabel
        if(value instanceof JLabel){
            JLabel label = (JLabel) value;
            label.setHorizontalAlignment(JLabel.CENTER); //Alineacion horizontal
            label.setVerticalAlignment(JLabel.CENTER); //Alineacion vertical
            return label;
        }
        
        //Evaluacion de celda
        if(this.colorear){
            
            boolean equal = false;
            
            switch(table.getValueAt(row, this.columna).getClass().toString()){
                case "class java.lang.String":
                    equal = table.getValueAt(row, this.columna).equals(String.valueOf(obj));
                    
                    break;
                case "class java.lang.Boolean":                                        
                    equal = (boolean) table.getValueAt(row, this.columna);
                    
                    break;
                default:
                    break;                    
            }
            
            //Evalua el color de la fila
            if (equal){
               this.setOpaque(true);
               this.setBackground(VERDE);
               this.setForeground(Color.BLACK);
            } else {
               this.setOpaque(true);
               this.setBackground(ROJO);
               this.setForeground(Color.BLACK);
            }
        }
        
        setHorizontalAlignment(SwingConstants.CENTER);//Alineaciones de celdas

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    //----------------------
    
    public static void main(String[] args){
        Boolean bool = true;
        System.out.println(bool.getClass().toString());
        if(bool instanceof Boolean){
        
        }
        
    }
    
}


