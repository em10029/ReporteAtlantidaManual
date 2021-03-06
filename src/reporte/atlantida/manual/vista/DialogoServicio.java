/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.manual.vista;

import javax.swing.table.DefaultTableModel;
import reporte.atlantida.manual.control.Data.Concepto;
import reporte.atlantida.manual.control.Data.Empresa;
import reporte.atlantida.manual.control.Data.Servicio;
import reporte.atlantida.manual.utilitario.Util;

/**
 *
 * @author efmartinez
 */
public class DialogoServicio extends java.awt.Dialog {

    private final javax.swing.table.DefaultTableModel modeloIdentificador;
    private final javax.swing.table.DefaultTableModel modeloConcepto;

    /**
     * Creates new form DialogoServicio
     *
     * @param parent
     * @param modal
     */
    public DialogoServicio(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        
        //Modelos
        this.modeloIdentificador = (DefaultTableModel) jTableIdentificador.getModel();
        this.modeloConcepto = (DefaultTableModel) jTableConcepto.getModel();
        
        //Renderizacion        
        Render render = new Render(0, "null", false); //Default
        
        //******************* Tabla Identificador *******************//
        this.jTableIdentificador.setDefaultRenderer(Object.class, render);
        
        //******************* Tabla Concepto *******************//
        this.jTableConcepto.setDefaultRenderer(Object.class, render);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelContenedor = new javax.swing.JPanel();
        jScrollPaneConcepto = new javax.swing.JScrollPane();
        jTableConcepto = new javax.swing.JTable();
        jLabelServicio = new javax.swing.JLabel();
        jSeparatorServicio = new javax.swing.JSeparator();
        jScrollPaneIdentificador = new javax.swing.JScrollPane();
        jTableIdentificador = new javax.swing.JTable();

        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanelContenedor.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPaneConcepto.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneConcepto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Conceptos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), java.awt.Color.darkGray)); // NOI18N

        jTableConcepto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Concepto", "Descripción", "Operador", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConcepto.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableConcepto.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneConcepto.setViewportView(jTableConcepto);

        jLabelServicio.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabelServicio.setForeground(new java.awt.Color(102, 102, 102));
        jLabelServicio.setText("Servicio");

        jScrollPaneIdentificador.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneIdentificador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificadores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14), java.awt.Color.darkGray)); // NOI18N

        jTableIdentificador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Descripción", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableIdentificador.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableIdentificador.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneIdentificador.setViewportView(jTableIdentificador);

        javax.swing.GroupLayout jPanelContenedorLayout = new javax.swing.GroupLayout(jPanelContenedor);
        jPanelContenedor.setLayout(jPanelContenedorLayout);
        jPanelContenedorLayout.setHorizontalGroup(
            jPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContenedorLayout.createSequentialGroup()
                .addGroup(jPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelContenedorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparatorServicio))
                    .addGroup(jPanelContenedorLayout.createSequentialGroup()
                        .addGroup(jPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelContenedorLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabelServicio))
                            .addGroup(jPanelContenedorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPaneIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanelContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanelContenedorLayout.setVerticalGroup(
            jPanelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelContenedorLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelServicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jScrollPaneConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        add(jPanelContenedor, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleName("Detalle de servicio");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    public void visualizar(Empresa empresa, Servicio servicio) {
        
        Util.limpiarTabla(this.modeloIdentificador);
        Util.limpiarTabla(this.modeloConcepto);

        this.setTitle(empresa.nombre);
        this.jLabelServicio.setText(servicio.nombre);

        //Identificadores
        //ID1
        Object[] dataId1 = {"ID1", servicio.id1Descripcion, servicio.id1Estado};
        this.modeloIdentificador.addRow(dataId1);
        //ID2
        Object[] dataId2 = {"ID2", servicio.id2Descripcion, servicio.id2Estado};
        this.modeloIdentificador.addRow(dataId2);
        //ID3
        Object[] dataId3 = {"ID3", servicio.id3Descripcion, servicio.id3Estado};
        this.modeloIdentificador.addRow(dataId3);

        //Conceptos
        for (Concepto concepto : servicio.conceptos) {
            Object[] data = {
                concepto.numero,
                concepto.descripcion,
                concepto.operador,
                concepto.estado
            };
            this.modeloConcepto.addRow(data);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelServicio;
    private javax.swing.JPanel jPanelContenedor;
    private javax.swing.JScrollPane jScrollPaneConcepto;
    private javax.swing.JScrollPane jScrollPaneIdentificador;
    private javax.swing.JSeparator jSeparatorServicio;
    private javax.swing.JTable jTableConcepto;
    private javax.swing.JTable jTableIdentificador;
    // End of variables declaration//GEN-END:variables
}
