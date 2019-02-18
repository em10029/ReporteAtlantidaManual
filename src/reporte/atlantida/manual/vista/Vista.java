/*
 * ©Informática Atlántida 2019.
 * Derechos Reservados.
 * 
 * Este software es propiedad intelectual de Informática Atlántida (Infatlan). La información contenida
 * es de carácter confidencial y no deberá revelarla. Solamente podrá utilizarlo de conformidad con los
 * términos del contrato suscrito con Informática Atlántida S.A.
 */
package reporte.atlantida.manual.vista;

import java.awt.CardLayout;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import reporte.atlantida.control.Control;
import reporte.atlantida.control.Proceso;
import reporte.atlantida.data.Query;
import reporte.atlantida.envio.FTP;
import reporte.atlantida.estructura.Empresa;
import reporte.atlantida.estructura.Reporte;
import reporte.atlantida.estructura.ReporteAtlantidaExcepcion;
import reporte.atlantida.estructura.Servicio;
import reporte.atlantida.fichero.Archivo;
import reporte.atlantida.manual.control.Controlador;
import static reporte.atlantida.manual.control.Controlador.DB2;
import reporte.atlantida.manual.control.Data;
import static reporte.atlantida.manual.control.Data.QUERY_INSERT_CAECEA;
import static reporte.atlantida.manual.control.Data.QUERY_INSERT_CAECEAS;
import reporte.atlantida.manual.utilitario.Util;
import reporte.atlantida.utilitario.Configuracion;

/**
 *
 * @author efmartinez
 */
public class Vista extends javax.swing.JFrame {

    private java.awt.CardLayout cotroladorVisual; //Contolador de cartas Cuerpo Principal

    public HashMap<String, Reporte> reportes;

    private javax.swing.table.DefaultTableModel modeloReporte;

    private javax.swing.table.DefaultTableModel modeloVerServicio;
    private javax.swing.table.DefaultTableModel modeloVerArchivo;

    private Reporte reporteRef;
    private javax.swing.table.DefaultTableModel modeloEditarCorreoServicio;
    private javax.swing.table.DefaultTableModel modeloEditarCorreoEmpresaServicio;

    private java.awt.CardLayout cotroladorEditar; //Contolador de cartas Editar

    private javax.swing.table.DefaultTableModel modeloContingenciaServicio;

    private javax.swing.table.DefaultTableModel modeloEmpresaServicio;

    private Data.Empresa empresaRef;
    DialogoServicio dialogo = new DialogoServicio(this, true);

    /**
     * Constructor.
     */
    public Vista() {
        initComponents();
        this.init();
    }

    /**
     * Inicializador de variables.
     */
    private void init() {
        //Controlado del cartas
        this.cotroladorVisual = (CardLayout) jPanelCuerpo.getLayout();

        //Reportes
        this.reportes = new HashMap<>();

        //Reporte
        this.modeloReporte = (DefaultTableModel) jTableReporte.getModel();

        //Ver
        this.modeloVerServicio = (DefaultTableModel) jTableVerServicio.getModel();
        this.modeloVerArchivo = (DefaultTableModel) jTableVerArchivo.getModel();

        //Editar
        this.cotroladorEditar = (CardLayout) jPanelEditarCuerpo.getLayout();
        this.modeloEditarCorreoServicio = (DefaultTableModel) jTableEditarCorreoServicio.getModel();
        this.modeloEditarCorreoEmpresaServicio = (DefaultTableModel) jTableEditarCorreoEmpresaServicio.getModel();

        //Contingencia
        this.modeloContingenciaServicio = (DefaultTableModel) jTableContingenciaServicio.getModel();

        //Fechas default
        Calendar calendar = Calendar.getInstance();
        this.jDateChooserContingenciaDesde.setCalendar(calendar);
        this.jDateChooserContingenciaHasta.setCalendar(calendar);

        //Empresa
        this.modeloEmpresaServicio = (DefaultTableModel) jTableEmpresaServicio.getModel();
        this.jLabelEmpresaIBS.setText("");

        //Carga lista de empresas.
        for (Data.Empresa empresa : Data.empresas) {
            this.jComboContingenciaBoxEmpresa.addItem(empresa.nombre);
            this.jComboEmpresaBoxEmpresa.addItem(empresa.nombre);
        }

        //Rederizacion
        this.renderizar();

        //Configuracion 
        this.jLabelConfiguracionConexion.setText(Configuracion.CONEXION_URL);
        this.jLabelConfiguracionUsuario.setText(Configuracion.CONEXION_USER);
        this.jLabelConfiguracionDirectorioInterno.setText(Control.directorio.getUbicacion());
        this.jLabelConfiguracionDirectorioExterno.setText(Control.directorio.getURL());
        this.jLabelConfiguracionTiempo.setText(String.valueOf(Configuracion.CONTROL_TIEMPO) + " minutos");
        this.jLabelConfiguracionProducto.setText(Configuracion.SERVICIO_PRODUCTO);
                
        //Panel por defecto
        this.cambiarPanel("Reporte");
    }

    /**
     * Rederización de tablas.
     */
    private void renderizar() {
                
        Render render = new Render(0, "null", false); //Default

        //******************* Tabla Reporte *******************//        
        this.jTableReporte.setDefaultRenderer(Object.class, new Render(8, "A", true));

        //******************* Tabla VerServicio *******************//
        this.jTableVerServicio.setDefaultRenderer(Object.class, new Render(3, "A", true));
        
        //******************* Tabla VerArchivo *******************//
        this.jTableVerArchivo.setDefaultRenderer(Object.class, render);
        
        //******************* Tabla EditarCorreoServicio *******************//
        this.jTableEditarCorreoServicio.setDefaultRenderer(Object.class, new Render(3, Boolean.TRUE, true));

        //******************* Tabla EditarCorreoServicioEmpresa *******************//        
        this.jTableEditarCorreoEmpresaServicio.setDefaultRenderer(Object.class, render);
        
        //******************* Tabla ContingenciaServicio *******************//        
        this.jTableContingenciaServicio.setDefaultRenderer(Object.class, render);
        
        //******************* Tabla EmpresaServicio *******************//        
        this.jTableEmpresaServicio.setDefaultRenderer(Object.class, render);
    }

    /**
     * Cambiar de panel central.
     *
     * @param card
     */
    private void cambiarPanel(String card) {
        this.cotroladorVisual.show(this.jPanelCuerpo, card);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupContingenciaArchivo = new javax.swing.ButtonGroup();
        buttonGroupContingenciaEnvio = new javax.swing.ButtonGroup();
        buttonGroupContingenciaInformacion = new javax.swing.ButtonGroup();
        jPanelEncabezado = new javax.swing.JPanel();
        jPanelLogo = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jPanelDescripsion = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelMenu = new javax.swing.JPanel();
        jButtonReporte = new javax.swing.JButton();
        jButtonContingencia = new javax.swing.JButton();
        jButtonEmpresa = new javax.swing.JButton();
        jButtonConfiguracion = new javax.swing.JButton();
        jButtonAyuda = new javax.swing.JButton();
        jPanelCuerpo = new javax.swing.JPanel();
        jPanelReporte = new javax.swing.JPanel();
        jScrollPaneReporte = new javax.swing.JScrollPane();
        jTableReporte = new javax.swing.JTable();
        jPanelVer = new javax.swing.JPanel();
        jPanelVerEncabezado = new javax.swing.JPanel();
        jLabelVerEmpresa = new javax.swing.JLabel();
        jSeparatorVer = new javax.swing.JSeparator();
        jPanelVerCuerpo = new javax.swing.JPanel();
        jPanelVerInfo = new javax.swing.JPanel();
        jLabelTextID = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jLabelTextEstado = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();
        jLabelTextIBS = new javax.swing.JLabel();
        jLabelIBS = new javax.swing.JLabel();
        jLabeTextInformacion = new javax.swing.JLabel();
        jLabelInformacion = new javax.swing.JLabel();
        jLabelTextFechas = new javax.swing.JLabel();
        jLabelFechas = new javax.swing.JLabel();
        jLabelTextConcepto = new javax.swing.JLabel();
        jLabelConcepto = new javax.swing.JLabel();
        jLabelTextVersion = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jLabelTextNivel = new javax.swing.JLabel();
        jLabelNivel = new javax.swing.JLabel();
        jLabelTextGeneracion = new javax.swing.JLabel();
        jLabelGeneracion = new javax.swing.JLabel();
        jLabelTextContenido = new javax.swing.JLabel();
        jLabelContenido = new javax.swing.JLabel();
        jLabelTextDestino = new javax.swing.JLabel();
        jLabelDestino = new javax.swing.JLabel();
        jScrollPaneVerServicio = new javax.swing.JScrollPane();
        jTableVerServicio = new javax.swing.JTable();
        jPanelVerArchivo = new javax.swing.JPanel();
        jLabelVerTextDirectorio = new javax.swing.JLabel();
        jLabelVerDirectorio = new javax.swing.JLabel();
        jScrollPaneVerArchivo = new javax.swing.JScrollPane();
        jTableVerArchivo = new javax.swing.JTable();
        jPanelEditar = new javax.swing.JPanel();
        jPanelEditarEncabezado = new javax.swing.JPanel();
        jLabelEditarEmpresa = new javax.swing.JLabel();
        jSeparatorEditar = new javax.swing.JSeparator();
        jPanelEditarInfo = new javax.swing.JPanel();
        jLabelTextEditarDestino = new javax.swing.JLabel();
        jLabelEditarDestino = new javax.swing.JLabel();
        jLabelTextEditarEnvio = new javax.swing.JLabel();
        jLabelEditarEnvio = new javax.swing.JLabel();
        jLabelTextEditarPeticion = new javax.swing.JLabel();
        jLabelEditarPeticion = new javax.swing.JLabel();
        jPanelEditarCuerpo = new javax.swing.JPanel();
        jPanelEditarCuerpoCorreoDefinido = new javax.swing.JPanel();
        jLabelCorreoDefinido = new javax.swing.JLabel();
        jPanelCorreoDefinidoLista = new javax.swing.JPanel();
        jScrollPaneEditarCuerpoCorreo = new javax.swing.JScrollPane();
        jTextAreaEditarCuerpoCorreo = new javax.swing.JTextArea();
        jPanelCorreoDefinidoEnvio = new javax.swing.JPanel();
        jButtonReintentarCorreoDefinido = new javax.swing.JButton();
        jPanelEditarCuerpoCorreoServicio = new javax.swing.JPanel();
        jLabelEditarCorreoServicio = new javax.swing.JLabel();
        jScrollPaneEditarCorreoServicio = new javax.swing.JScrollPane();
        jTableEditarCorreoServicio = new javax.swing.JTable();
        jPanelCorreoServicioEnvio = new javax.swing.JPanel();
        jButtonReintentarCorreoServicio = new javax.swing.JButton();
        jPanelEditarCuerpoCorreoEmpresaServicio = new javax.swing.JPanel();
        jLabelEditarCorreoEmpresaServicio = new javax.swing.JLabel();
        jPanelEditarCorreoEmpresaServicio = new javax.swing.JPanel();
        jScrollPaneEditarCuerpoCorreoEmpresaServicio = new javax.swing.JScrollPane();
        jTextAreaEditarCorreoEmpresaServicio = new javax.swing.JTextArea();
        jScrollPaneEditarCorreoEmpresaServicio = new javax.swing.JScrollPane();
        jTableEditarCorreoEmpresaServicio = new javax.swing.JTable();
        jPanelCorreoEmpresaServicioEnvio = new javax.swing.JPanel();
        jButtonReintentarCorreoEmpresaServicio = new javax.swing.JButton();
        jPanelEditarCuerpoFTP = new javax.swing.JPanel();
        jLabelFTPServidor = new javax.swing.JLabel();
        jTextFieldFTPServidor = new javax.swing.JTextField();
        jLabelFTPDirectorio = new javax.swing.JLabel();
        jTextFieldFTPDirectorio = new javax.swing.JTextField();
        jLabelFTPUsuario = new javax.swing.JLabel();
        jTextFieldUsuario = new javax.swing.JTextField();
        jLabelFTPClave = new javax.swing.JLabel();
        jTextFieldClave = new javax.swing.JTextField();
        jPanelFTPEnvio = new javax.swing.JPanel();
        jButtonReintentarFTP = new javax.swing.JButton();
        jLabelFTPAyuda = new javax.swing.JLabel();
        jPanelContingencia = new javax.swing.JPanel();
        jPanelContingenciaFecha = new javax.swing.JPanel();
        jDateChooserContingenciaDesde = new com.toedter.calendar.JDateChooser();
        jDateChooserContingenciaHasta = new com.toedter.calendar.JDateChooser();
        jPanelContingenciaEmpresa = new javax.swing.JPanel();
        jComboContingenciaBoxEmpresa = new javax.swing.JComboBox<>();
        jPanelContingenciaReporte = new javax.swing.JPanel();
        jPanelContingenciaArchivo = new javax.swing.JPanel();
        jRadioButtonContingenciaReportePago = new javax.swing.JRadioButton();
        jRadioButtonContingenciaReporteSaldo = new javax.swing.JRadioButton();
        jPanelContingenciaInformacion = new javax.swing.JPanel();
        jRadioButtonContingenciaGeneracionDiario = new javax.swing.JRadioButton();
        jRadioButtonContingenciaGeneracionHistorico = new javax.swing.JRadioButton();
        jRadioButtonContingenciaGeneracionAmbos = new javax.swing.JRadioButton();
        jScrollPaneContingenciaServicio = new javax.swing.JScrollPane();
        jTableContingenciaServicio = new javax.swing.JTable();
        jPanelContingenciaEnvio = new javax.swing.JPanel();
        jRadioButtonContingenciaEnvioEmpresa = new javax.swing.JRadioButton();
        jRadioButtonContingenciaEnvioUsuario = new javax.swing.JRadioButton();
        jButtonContingenciaGenerarEnviar = new javax.swing.JButton();
        jScrollPaneContingenciaCorreo = new javax.swing.JScrollPane();
        jTextAreaContingenciaCorreo = new javax.swing.JTextArea();
        jPanelEmpresa = new javax.swing.JPanel();
        jPanelEmpresaComboBox = new javax.swing.JPanel();
        jComboEmpresaBoxEmpresa = new javax.swing.JComboBox<>();
        jLabelEmpresaIBS = new javax.swing.JLabel();
        jScrollPaneEmpresaCorreo = new javax.swing.JScrollPane();
        jTextPaneEmpresaCorreo = new javax.swing.JTextPane();
        jScrollPaneEmpresaServicio = new javax.swing.JScrollPane();
        jTableEmpresaServicio = new javax.swing.JTable();
        jPanelEmpresaInformacion = new javax.swing.JPanel();
        jLabelTextEmpresaVersionPago = new javax.swing.JLabel();
        jLabelEmpresaVersionPago = new javax.swing.JLabel();
        jLabelTextEmpresaNivelPago = new javax.swing.JLabel();
        jLabelEmpresaNivelPago = new javax.swing.JLabel();
        jLabelTextEmpresaVersionSaldo = new javax.swing.JLabel();
        jLabelEmpresaVersionSaldo = new javax.swing.JLabel();
        jLabelTextEmpresaNivelSaldo = new javax.swing.JLabel();
        jLabelEmpresaNivelSaldo = new javax.swing.JLabel();
        jLabelTextEmpresaConcepto = new javax.swing.JLabel();
        jLabelEmpresaConcepto = new javax.swing.JLabel();
        jPanelAyuda = new javax.swing.JPanel();
        jPanelConfiguracion = new javax.swing.JPanel();
        jPanelConfiguracionInfo = new javax.swing.JPanel();
        jLabelTextConfiguracionConexion = new javax.swing.JLabel();
        jLabelConfiguracionConexion = new javax.swing.JLabel();
        jLabelTextConfiguracionUsuario = new javax.swing.JLabel();
        jLabelConfiguracionUsuario = new javax.swing.JLabel();
        jLabelTextConfiguracionDirectorioInterno = new javax.swing.JLabel();
        jLabelConfiguracionDirectorioInterno = new javax.swing.JLabel();
        jLabelTextConfiguracionTiempo = new javax.swing.JLabel();
        jLabelConfiguracionTiempo = new javax.swing.JLabel();
        jLabelTextConfiguracionProducto = new javax.swing.JLabel();
        jLabelConfiguracionProducto = new javax.swing.JLabel();
        jLabelTextConfiguracionDirectorioExterno = new javax.swing.JLabel();
        jLabelConfiguracionDirectorioExterno = new javax.swing.JLabel();
        jPanelConfiguracionDoc = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        reporteMenuItem = new javax.swing.JMenuItem();
        contingenciaMenuItem = new javax.swing.JMenuItem();
        empresaAsMenuItem = new javax.swing.JMenuItem();
        salirMenuItem = new javax.swing.JMenuItem();
        ayudaMenu = new javax.swing.JMenu();
        ayudaMenuItem = new javax.swing.JMenuItem();
        soporteMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte Atlántida");
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        getContentPane().setLayout(new java.awt.BorderLayout(1, 2));

        jPanelEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEncabezado.setLayout(new java.awt.BorderLayout());

        jPanelLogo.setBackground(new java.awt.Color(255, 255, 255));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/logo.png"))); // NOI18N
        jPanelLogo.add(jLabelLogo);

        jPanelEncabezado.add(jPanelLogo, java.awt.BorderLayout.PAGE_START);

        jPanelDescripsion.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Control de reportes fallidos caja empresarial");
        jPanelDescripsion.add(jLabel1);

        jPanelEncabezado.add(jPanelDescripsion, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanelEncabezado, java.awt.BorderLayout.PAGE_START);

        jPanelMenu.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMenu.setLayout(new java.awt.GridLayout(5, 1));

        jButtonReporte.setBackground(new java.awt.Color(255, 255, 255));
        jButtonReporte.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonReporte.setForeground(new java.awt.Color(227, 30, 48));
        jButtonReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/formulario-chequeado.png"))); // NOI18N
        jButtonReporte.setText("Reportes");
        jButtonReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonReporte.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReporteMouseClicked(evt);
            }
        });
        jPanelMenu.add(jButtonReporte);

        jButtonContingencia.setBackground(new java.awt.Color(255, 255, 255));
        jButtonContingencia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonContingencia.setForeground(new java.awt.Color(227, 30, 48));
        jButtonContingencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/pluma (1).png"))); // NOI18N
        jButtonContingencia.setText("Contingencia");
        jButtonContingencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonContingenciaMouseClicked(evt);
            }
        });
        jPanelMenu.add(jButtonContingencia);

        jButtonEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        jButtonEmpresa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonEmpresa.setForeground(new java.awt.Color(227, 30, 48));
        jButtonEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/lupa-para-buscar.png"))); // NOI18N
        jButtonEmpresa.setText("Empresa");
        jButtonEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonEmpresaMouseClicked(evt);
            }
        });
        jPanelMenu.add(jButtonEmpresa);

        jButtonConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        jButtonConfiguracion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonConfiguracion.setForeground(new java.awt.Color(227, 30, 48));
        jButtonConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/configuracion.png"))); // NOI18N
        jButtonConfiguracion.setText("Configuración");
        jButtonConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonConfiguracionMouseClicked(evt);
            }
        });
        jPanelMenu.add(jButtonConfiguracion);

        jButtonAyuda.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAyuda.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButtonAyuda.setForeground(new java.awt.Color(227, 30, 48));
        jButtonAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/informacion (1).png"))); // NOI18N
        jButtonAyuda.setText("Ayuda");
        jButtonAyuda.setEnabled(false);
        jButtonAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAyudaMouseClicked(evt);
            }
        });
        jPanelMenu.add(jButtonAyuda);

        getContentPane().add(jPanelMenu, java.awt.BorderLayout.LINE_START);

        jPanelCuerpo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCuerpo.setLayout(new java.awt.CardLayout());

        jPanelReporte.setBackground(new java.awt.Color(255, 255, 255));
        jPanelReporte.setLayout(new java.awt.BorderLayout());

        jScrollPaneReporte.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneReporte.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de reportes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(102, 102, 102))); // NOI18N

        jTableReporte.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTableReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Petición", "Empresa", "IBS", "Archivo", "Contenido", "Fechas", "Generación", "Estado", "Ver", "Corregir"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableReporte.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableReporte.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableReporte.getTableHeader().setReorderingAllowed(false);
        jTableReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableReporteMouseClicked(evt);
            }
        });
        jScrollPaneReporte.setViewportView(jTableReporte);
        if (jTableReporte.getColumnModel().getColumnCount() > 0) {
            jTableReporte.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTableReporte.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTableReporte.getColumnModel().getColumn(2).setPreferredWidth(170);
            jTableReporte.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTableReporte.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTableReporte.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTableReporte.getColumnModel().getColumn(6).setPreferredWidth(120);
            jTableReporte.getColumnModel().getColumn(7).setPreferredWidth(60);
            jTableReporte.getColumnModel().getColumn(8).setPreferredWidth(60);
            jTableReporte.getColumnModel().getColumn(9).setPreferredWidth(50);
            jTableReporte.getColumnModel().getColumn(10).setPreferredWidth(50);
        }

        jPanelReporte.add(jScrollPaneReporte, java.awt.BorderLayout.CENTER);

        jPanelCuerpo.add(jPanelReporte, "Reporte");

        jPanelVer.setBackground(new java.awt.Color(255, 255, 255));

        jPanelVerEncabezado.setBackground(new java.awt.Color(255, 255, 255));

        jLabelVerEmpresa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelVerEmpresa.setForeground(new java.awt.Color(227, 30, 48));
        jLabelVerEmpresa.setText("Nombre de la empresa");
        jLabelVerEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jSeparatorVer.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanelVerEncabezadoLayout = new javax.swing.GroupLayout(jPanelVerEncabezado);
        jPanelVerEncabezado.setLayout(jPanelVerEncabezadoLayout);
        jPanelVerEncabezadoLayout.setHorizontalGroup(
            jPanelVerEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerEncabezadoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanelVerEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVerEncabezadoLayout.createSequentialGroup()
                        .addComponent(jLabelVerEmpresa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparatorVer))
                .addContainerGap())
        );
        jPanelVerEncabezadoLayout.setVerticalGroup(
            jPanelVerEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerEncabezadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelVerEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorVer, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelVerCuerpo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelVerCuerpo.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        jPanelVerInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelVerInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), java.awt.Color.darkGray)); // NOI18N
        jPanelVerInfo.setLayout(new java.awt.GridLayout(11, 2, 5, 5));

        jLabelTextID.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextID.setText("Petición: ");
        jLabelTextID.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelVerInfo.add(jLabelTextID);

        jLabelID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelID.setText("ID");
        jPanelVerInfo.add(jLabelID);

        jLabelTextEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEstado.setText("Estado: ");
        jPanelVerInfo.add(jLabelTextEstado);

        jLabelEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelEstado.setText("Estado");
        jPanelVerInfo.add(jLabelEstado);

        jLabelTextIBS.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextIBS.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextIBS.setText("IBS: ");
        jPanelVerInfo.add(jLabelTextIBS);

        jLabelIBS.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelIBS.setText("IBS");
        jPanelVerInfo.add(jLabelIBS);

        jLabeTextInformacion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabeTextInformacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabeTextInformacion.setText("Información: ");
        jPanelVerInfo.add(jLabeTextInformacion);

        jLabelInformacion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelInformacion.setText("Información");
        jPanelVerInfo.add(jLabelInformacion);

        jLabelTextFechas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextFechas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextFechas.setText("Fechas: ");
        jPanelVerInfo.add(jLabelTextFechas);

        jLabelFechas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelFechas.setText("Fechas");
        jPanelVerInfo.add(jLabelFechas);

        jLabelTextConcepto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextConcepto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConcepto.setText("Concepto: ");
        jPanelVerInfo.add(jLabelTextConcepto);

        jLabelConcepto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelConcepto.setText("Concepto");
        jPanelVerInfo.add(jLabelConcepto);

        jLabelTextVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextVersion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextVersion.setText("Versión: ");
        jPanelVerInfo.add(jLabelTextVersion);

        jLabelVersion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelVersion.setText("Versión");
        jPanelVerInfo.add(jLabelVersion);

        jLabelTextNivel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextNivel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextNivel.setText("Nivel: ");
        jPanelVerInfo.add(jLabelTextNivel);

        jLabelNivel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelNivel.setText("Nivel");
        jPanelVerInfo.add(jLabelNivel);

        jLabelTextGeneracion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextGeneracion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextGeneracion.setText("Generación: ");
        jPanelVerInfo.add(jLabelTextGeneracion);

        jLabelGeneracion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelGeneracion.setText("Generación");
        jPanelVerInfo.add(jLabelGeneracion);

        jLabelTextContenido.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextContenido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextContenido.setText("Contenido: ");
        jPanelVerInfo.add(jLabelTextContenido);

        jLabelContenido.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelContenido.setText("Contenido");
        jPanelVerInfo.add(jLabelContenido);

        jLabelTextDestino.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelTextDestino.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextDestino.setText("Destino: ");
        jPanelVerInfo.add(jLabelTextDestino);

        jLabelDestino.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelDestino.setText("Destino");
        jPanelVerInfo.add(jLabelDestino);

        jPanelVerCuerpo.add(jPanelVerInfo);

        jScrollPaneVerServicio.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneVerServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Servicios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), java.awt.Color.darkGray)); // NOI18N

        jTableVerServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableVerServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Descripción", "Transacciones", "Estado de envío"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVerServicio.setGridColor(new java.awt.Color(255, 255, 255));
        jTableVerServicio.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableVerServicio.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneVerServicio.setViewportView(jTableVerServicio);

        jPanelVerCuerpo.add(jScrollPaneVerServicio);

        jPanelVerArchivo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelVerArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Archivos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), java.awt.Color.darkGray)); // NOI18N

        jLabelVerTextDirectorio.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelVerTextDirectorio.setText("Directorio: ");

        jLabelVerDirectorio.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelVerDirectorio.setText("Carpeta");

        jTableVerArchivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Archivo", "Generado", "Enviado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVerArchivo.setGridColor(new java.awt.Color(255, 255, 255));
        jTableVerArchivo.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableVerArchivo.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneVerArchivo.setViewportView(jTableVerArchivo);
        if (jTableVerArchivo.getColumnModel().getColumnCount() > 0) {
            jTableVerArchivo.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTableVerArchivo.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTableVerArchivo.getColumnModel().getColumn(2).setPreferredWidth(10);
            jTableVerArchivo.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanelVerArchivoLayout = new javax.swing.GroupLayout(jPanelVerArchivo);
        jPanelVerArchivo.setLayout(jPanelVerArchivoLayout);
        jPanelVerArchivoLayout.setHorizontalGroup(
            jPanelVerArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerArchivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVerArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneVerArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                    .addGroup(jPanelVerArchivoLayout.createSequentialGroup()
                        .addComponent(jLabelVerTextDirectorio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelVerDirectorio)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelVerArchivoLayout.setVerticalGroup(
            jPanelVerArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerArchivoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelVerArchivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVerTextDirectorio)
                    .addComponent(jLabelVerDirectorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneVerArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanelVerLayout = new javax.swing.GroupLayout(jPanelVer);
        jPanelVer.setLayout(jPanelVerLayout);
        jPanelVerLayout.setHorizontalGroup(
            jPanelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelVerArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelVerCuerpo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanelVerEncabezado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(55, 55, 55))
        );
        jPanelVerLayout.setVerticalGroup(
            jPanelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVerLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanelVerEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jPanelVerCuerpo, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanelVerArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        jPanelCuerpo.add(jPanelVer, "Ver");

        jPanelEditar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEditar.setLayout(new java.awt.BorderLayout());

        jPanelEditarEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEditarEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Editar Petición", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jLabelEditarEmpresa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelEditarEmpresa.setForeground(new java.awt.Color(227, 30, 48));
        jLabelEditarEmpresa.setText("Nombre de la empresa");
        jLabelEditarEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jSeparatorEditar.setForeground(new java.awt.Color(102, 102, 102));

        jPanelEditarInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEditarInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N

        jLabelTextEditarDestino.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextEditarDestino.setText("Destino:");

        jLabelEditarDestino.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelEditarDestino.setText("Destino");

        jLabelTextEditarEnvio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextEditarEnvio.setText("Envió:");

        jLabelEditarEnvio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelEditarEnvio.setText("Envió");

        jLabelTextEditarPeticion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextEditarPeticion.setText("Petición:");

        jLabelEditarPeticion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelEditarPeticion.setText("Petición");

        javax.swing.GroupLayout jPanelEditarInfoLayout = new javax.swing.GroupLayout(jPanelEditarInfo);
        jPanelEditarInfo.setLayout(jPanelEditarInfoLayout);
        jPanelEditarInfoLayout.setHorizontalGroup(
            jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanelEditarInfoLayout.createSequentialGroup()
                        .addComponent(jLabelTextEditarPeticion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelEditarPeticion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelEditarInfoLayout.createSequentialGroup()
                        .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelTextEditarEnvio)
                            .addComponent(jLabelTextEditarDestino))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelEditarEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEditarDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanelEditarInfoLayout.setVerticalGroup(
            jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarInfoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextEditarPeticion)
                    .addComponent(jLabelEditarPeticion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextEditarDestino)
                    .addComponent(jLabelEditarDestino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEditarEnvio)
                    .addComponent(jLabelTextEditarEnvio))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanelEditarEncabezadoLayout = new javax.swing.GroupLayout(jPanelEditarEncabezado);
        jPanelEditarEncabezado.setLayout(jPanelEditarEncabezadoLayout);
        jPanelEditarEncabezadoLayout.setHorizontalGroup(
            jPanelEditarEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarEncabezadoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanelEditarEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelEditarInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparatorEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelEditarEncabezadoLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabelEditarEmpresa)))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanelEditarEncabezadoLayout.setVerticalGroup(
            jPanelEditarEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarEncabezadoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelEditarEmpresa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparatorEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelEditarInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanelEditar.add(jPanelEditarEncabezado, java.awt.BorderLayout.PAGE_START);

        jPanelEditarCuerpo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEditarCuerpo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Corregir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N
        jPanelEditarCuerpo.setLayout(new java.awt.CardLayout());

        jPanelEditarCuerpoCorreoDefinido.setBackground(new java.awt.Color(255, 255, 255));

        jLabelCorreoDefinido.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelCorreoDefinido.setText("Verifique los correo destinarios y separelos por una coma (,)");

        jPanelCorreoDefinidoLista.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCorreoDefinidoLista.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de correos detinatarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), java.awt.Color.darkGray)); // NOI18N
        jPanelCorreoDefinidoLista.setLayout(new java.awt.BorderLayout());

        jTextAreaEditarCuerpoCorreo.setColumns(20);
        jTextAreaEditarCuerpoCorreo.setRows(5);
        jScrollPaneEditarCuerpoCorreo.setViewportView(jTextAreaEditarCuerpoCorreo);

        jPanelCorreoDefinidoLista.add(jScrollPaneEditarCuerpoCorreo, java.awt.BorderLayout.CENTER);

        jPanelCorreoDefinidoEnvio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCorreoDefinidoEnvio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reintentar enviar los archivos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jButtonReintentarCorreoDefinido.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButtonReintentarCorreoDefinido.setForeground(new java.awt.Color(227, 30, 48));
        jButtonReintentarCorreoDefinido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/reenviar-mensaje.png"))); // NOI18N
        jButtonReintentarCorreoDefinido.setText("Reintentar");
        jButtonReintentarCorreoDefinido.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReintentarCorreoDefinido.setIconTextGap(13);
        jButtonReintentarCorreoDefinido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReintentarCorreoDefinidoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCorreoDefinidoEnvioLayout = new javax.swing.GroupLayout(jPanelCorreoDefinidoEnvio);
        jPanelCorreoDefinidoEnvio.setLayout(jPanelCorreoDefinidoEnvioLayout);
        jPanelCorreoDefinidoEnvioLayout.setHorizontalGroup(
            jPanelCorreoDefinidoEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCorreoDefinidoEnvioLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButtonReintentarCorreoDefinido)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanelCorreoDefinidoEnvioLayout.setVerticalGroup(
            jPanelCorreoDefinidoEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCorreoDefinidoEnvioLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButtonReintentarCorreoDefinido, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEditarCuerpoCorreoDefinidoLayout = new javax.swing.GroupLayout(jPanelEditarCuerpoCorreoDefinido);
        jPanelEditarCuerpoCorreoDefinido.setLayout(jPanelEditarCuerpoCorreoDefinidoLayout);
        jPanelEditarCuerpoCorreoDefinidoLayout.setHorizontalGroup(
            jPanelEditarCuerpoCorreoDefinidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoCorreoDefinidoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelEditarCuerpoCorreoDefinidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCorreoDefinidoEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCorreoDefinido)
                    .addComponent(jPanelCorreoDefinidoLista, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanelEditarCuerpoCorreoDefinidoLayout.setVerticalGroup(
            jPanelEditarCuerpoCorreoDefinidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoCorreoDefinidoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelCorreoDefinido)
                .addGap(18, 18, 18)
                .addComponent(jPanelCorreoDefinidoLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelCorreoDefinidoEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanelEditarCuerpo.add(jPanelEditarCuerpoCorreoDefinido, "CorreoDefinido");

        jPanelEditarCuerpoCorreoServicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabelEditarCorreoServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelEditarCorreoServicio.setText("Verifique los correo destinarios y separelos por una coma (,)");

        jScrollPaneEditarCorreoServicio.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneEditarCorreoServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Envió a nivel de servicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), java.awt.Color.darkGray)); // NOI18N

        jTableEditarCorreoServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Descripción", "Correos", "Enviado", "Editar Correos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEditarCorreoServicio.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableEditarCorreoServicio.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableEditarCorreoServicio.getTableHeader().setReorderingAllowed(false);
        jTableEditarCorreoServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarCorreoServicioMouseClicked(evt);
            }
        });
        jScrollPaneEditarCorreoServicio.setViewportView(jTableEditarCorreoServicio);
        if (jTableEditarCorreoServicio.getColumnModel().getColumnCount() > 0) {
            jTableEditarCorreoServicio.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTableEditarCorreoServicio.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTableEditarCorreoServicio.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableEditarCorreoServicio.getColumnModel().getColumn(4).setPreferredWidth(10);
        }

        jPanelCorreoServicioEnvio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCorreoServicioEnvio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reintentar enviar los archivos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jButtonReintentarCorreoServicio.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButtonReintentarCorreoServicio.setForeground(new java.awt.Color(227, 30, 48));
        jButtonReintentarCorreoServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/reenviar-mensaje.png"))); // NOI18N
        jButtonReintentarCorreoServicio.setText("Reintentar");
        jButtonReintentarCorreoServicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReintentarCorreoServicio.setIconTextGap(13);
        jButtonReintentarCorreoServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReintentarCorreoServicioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCorreoServicioEnvioLayout = new javax.swing.GroupLayout(jPanelCorreoServicioEnvio);
        jPanelCorreoServicioEnvio.setLayout(jPanelCorreoServicioEnvioLayout);
        jPanelCorreoServicioEnvioLayout.setHorizontalGroup(
            jPanelCorreoServicioEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCorreoServicioEnvioLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButtonReintentarCorreoServicio)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanelCorreoServicioEnvioLayout.setVerticalGroup(
            jPanelCorreoServicioEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCorreoServicioEnvioLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButtonReintentarCorreoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEditarCuerpoCorreoServicioLayout = new javax.swing.GroupLayout(jPanelEditarCuerpoCorreoServicio);
        jPanelEditarCuerpoCorreoServicio.setLayout(jPanelEditarCuerpoCorreoServicioLayout);
        jPanelEditarCuerpoCorreoServicioLayout.setHorizontalGroup(
            jPanelEditarCuerpoCorreoServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoCorreoServicioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelEditarCuerpoCorreoServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCorreoServicioEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEditarCorreoServicio)
                    .addComponent(jScrollPaneEditarCorreoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEditarCuerpoCorreoServicioLayout.setVerticalGroup(
            jPanelEditarCuerpoCorreoServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoCorreoServicioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelEditarCorreoServicio)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneEditarCorreoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanelCorreoServicioEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelEditarCuerpo.add(jPanelEditarCuerpoCorreoServicio, "CorreoServicio");

        jPanelEditarCuerpoCorreoEmpresaServicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabelEditarCorreoEmpresaServicio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelEditarCorreoEmpresaServicio.setText("Verifique los correo destinarios y separelos por una coma (,)");

        jPanelEditarCorreoEmpresaServicio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEditarCorreoEmpresaServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Destinatarios Según Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), java.awt.Color.darkGray)); // NOI18N
        jPanelEditarCorreoEmpresaServicio.setLayout(new java.awt.BorderLayout());

        jTextAreaEditarCorreoEmpresaServicio.setColumns(20);
        jTextAreaEditarCorreoEmpresaServicio.setRows(5);
        jScrollPaneEditarCuerpoCorreoEmpresaServicio.setViewportView(jTextAreaEditarCorreoEmpresaServicio);

        jPanelEditarCorreoEmpresaServicio.add(jScrollPaneEditarCuerpoCorreoEmpresaServicio, java.awt.BorderLayout.CENTER);

        jScrollPaneEditarCorreoEmpresaServicio.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneEditarCorreoEmpresaServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Destinatarios Según Servicios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), java.awt.Color.darkGray)); // NOI18N

        jTableEditarCorreoEmpresaServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Descripción", "Correos", "Editar Correos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEditarCorreoEmpresaServicio.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableEditarCorreoEmpresaServicio.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableEditarCorreoEmpresaServicio.getTableHeader().setReorderingAllowed(false);
        jTableEditarCorreoEmpresaServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEditarCorreoEmpresaServicioMouseClicked(evt);
            }
        });
        jScrollPaneEditarCorreoEmpresaServicio.setViewportView(jTableEditarCorreoEmpresaServicio);
        if (jTableEditarCorreoEmpresaServicio.getColumnModel().getColumnCount() > 0) {
            jTableEditarCorreoEmpresaServicio.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTableEditarCorreoEmpresaServicio.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTableEditarCorreoEmpresaServicio.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableEditarCorreoEmpresaServicio.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        jPanelCorreoEmpresaServicioEnvio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCorreoEmpresaServicioEnvio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reintentar enviar los archivos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jButtonReintentarCorreoEmpresaServicio.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButtonReintentarCorreoEmpresaServicio.setForeground(new java.awt.Color(227, 30, 48));
        jButtonReintentarCorreoEmpresaServicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/reenviar-mensaje.png"))); // NOI18N
        jButtonReintentarCorreoEmpresaServicio.setText("Reintentar");
        jButtonReintentarCorreoEmpresaServicio.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReintentarCorreoEmpresaServicio.setIconTextGap(13);
        jButtonReintentarCorreoEmpresaServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReintentarCorreoEmpresaServicioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCorreoEmpresaServicioEnvioLayout = new javax.swing.GroupLayout(jPanelCorreoEmpresaServicioEnvio);
        jPanelCorreoEmpresaServicioEnvio.setLayout(jPanelCorreoEmpresaServicioEnvioLayout);
        jPanelCorreoEmpresaServicioEnvioLayout.setHorizontalGroup(
            jPanelCorreoEmpresaServicioEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCorreoEmpresaServicioEnvioLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButtonReintentarCorreoEmpresaServicio)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanelCorreoEmpresaServicioEnvioLayout.setVerticalGroup(
            jPanelCorreoEmpresaServicioEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCorreoEmpresaServicioEnvioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReintentarCorreoEmpresaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelEditarCuerpoCorreoEmpresaServicioLayout = new javax.swing.GroupLayout(jPanelEditarCuerpoCorreoEmpresaServicio);
        jPanelEditarCuerpoCorreoEmpresaServicio.setLayout(jPanelEditarCuerpoCorreoEmpresaServicioLayout);
        jPanelEditarCuerpoCorreoEmpresaServicioLayout.setHorizontalGroup(
            jPanelEditarCuerpoCorreoEmpresaServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanelEditarCorreoEmpresaServicio, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneEditarCorreoEmpresaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createSequentialGroup()
                .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabelEditarCorreoEmpresaServicio))
                    .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jPanelCorreoEmpresaServicioEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelEditarCuerpoCorreoEmpresaServicioLayout.setVerticalGroup(
            jPanelEditarCuerpoCorreoEmpresaServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEditarCuerpoCorreoEmpresaServicioLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelEditarCorreoEmpresaServicio)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditarCuerpoCorreoEmpresaServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneEditarCorreoEmpresaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanelEditarCorreoEmpresaServicio, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelCorreoEmpresaServicioEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanelEditarCuerpo.add(jPanelEditarCuerpoCorreoEmpresaServicio, "CorreoEmpresaServicio");

        jPanelEditarCuerpoFTP.setBackground(new java.awt.Color(255, 255, 255));

        jLabelFTPServidor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelFTPServidor.setText("Servidor: ");

        jTextFieldFTPServidor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabelFTPDirectorio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelFTPDirectorio.setText("Carpeta: ");

        jTextFieldFTPDirectorio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabelFTPUsuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelFTPUsuario.setText("Usiario: ");

        jTextFieldUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabelFTPClave.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelFTPClave.setText("Contraseña: ");

        jTextFieldClave.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanelFTPEnvio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFTPEnvio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reintentar enviar los archivos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jButtonReintentarFTP.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jButtonReintentarFTP.setForeground(new java.awt.Color(227, 30, 48));
        jButtonReintentarFTP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/reenviar-mensaje.png"))); // NOI18N
        jButtonReintentarFTP.setText("Reintentar");
        jButtonReintentarFTP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButtonReintentarFTP.setIconTextGap(13);
        jButtonReintentarFTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReintentarFTPMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelFTPEnvioLayout = new javax.swing.GroupLayout(jPanelFTPEnvio);
        jPanelFTPEnvio.setLayout(jPanelFTPEnvioLayout);
        jPanelFTPEnvioLayout.setHorizontalGroup(
            jPanelFTPEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFTPEnvioLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButtonReintentarFTP)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanelFTPEnvioLayout.setVerticalGroup(
            jPanelFTPEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFTPEnvioLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButtonReintentarFTP, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelFTPAyuda.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelFTPAyuda.setForeground(new java.awt.Color(51, 51, 51));
        jLabelFTPAyuda.setText("FTP");

        javax.swing.GroupLayout jPanelEditarCuerpoFTPLayout = new javax.swing.GroupLayout(jPanelEditarCuerpoFTP);
        jPanelEditarCuerpoFTP.setLayout(jPanelEditarCuerpoFTPLayout);
        jPanelEditarCuerpoFTPLayout.setHorizontalGroup(
            jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoFTPLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelFTPEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelEditarCuerpoFTPLayout.createSequentialGroup()
                        .addComponent(jLabelFTPServidor)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldFTPServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelEditarCuerpoFTPLayout.createSequentialGroup()
                        .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelFTPDirectorio)
                            .addComponent(jLabelFTPUsuario)
                            .addComponent(jLabelFTPClave))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldFTPDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldClave, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabelFTPAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanelEditarCuerpoFTPLayout.setVerticalGroup(
            jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditarCuerpoFTPLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFTPServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFTPServidor)
                    .addComponent(jLabelFTPAyuda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFTPDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFTPDirectorio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFTPUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEditarCuerpoFTPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFTPClave))
                .addGap(35, 35, 35)
                .addComponent(jPanelFTPEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanelEditarCuerpo.add(jPanelEditarCuerpoFTP, "FTP");

        jPanelEditar.add(jPanelEditarCuerpo, java.awt.BorderLayout.CENTER);

        jPanelCuerpo.add(jPanelEditar, "Editar");

        jPanelContingencia.setBackground(new java.awt.Color(255, 255, 255));

        jPanelContingenciaFecha.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaFecha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fechas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaFecha.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanelContingenciaFecha.setLayout(new javax.swing.BoxLayout(jPanelContingenciaFecha, javax.swing.BoxLayout.X_AXIS));

        jDateChooserContingenciaDesde.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserContingenciaDesde.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Desde", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 13), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaFecha.add(jDateChooserContingenciaDesde);

        jDateChooserContingenciaHasta.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserContingenciaHasta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hasta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 13), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaFecha.add(jDateChooserContingenciaHasta);

        jPanelContingenciaEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaEmpresa.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanelContingenciaEmpresa.setLayout(new java.awt.BorderLayout());

        jComboContingenciaBoxEmpresa.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jComboContingenciaBoxEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccionar -" }));
        jComboContingenciaBoxEmpresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboContingenciaBoxEmpresaItemStateChanged(evt);
            }
        });
        jPanelContingenciaEmpresa.add(jComboContingenciaBoxEmpresa, java.awt.BorderLayout.CENTER);

        jPanelContingenciaReporte.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaReporte.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reporte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaReporte.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanelContingenciaReporte.setLayout(new java.awt.BorderLayout(5, 15));

        jPanelContingenciaArchivo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaArchivo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Archivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 13), java.awt.Color.darkGray)); // NOI18N

        jRadioButtonContingenciaReportePago.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaArchivo.add(jRadioButtonContingenciaReportePago);
        jRadioButtonContingenciaReportePago.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jRadioButtonContingenciaReportePago.setSelected(true);
        jRadioButtonContingenciaReportePago.setText("Pagos");
        jPanelContingenciaArchivo.add(jRadioButtonContingenciaReportePago);

        jRadioButtonContingenciaReporteSaldo.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaArchivo.add(jRadioButtonContingenciaReporteSaldo);
        jRadioButtonContingenciaReporteSaldo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jRadioButtonContingenciaReporteSaldo.setText("Saldos");
        jRadioButtonContingenciaReporteSaldo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonContingenciaReporteSaldoItemStateChanged(evt);
            }
        });
        jPanelContingenciaArchivo.add(jRadioButtonContingenciaReporteSaldo);

        jPanelContingenciaReporte.add(jPanelContingenciaArchivo, java.awt.BorderLayout.PAGE_START);

        jPanelContingenciaInformacion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaInformacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 13), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaInformacion.setLayout(new javax.swing.BoxLayout(jPanelContingenciaInformacion, javax.swing.BoxLayout.LINE_AXIS));

        jRadioButtonContingenciaGeneracionDiario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaInformacion.add(jRadioButtonContingenciaGeneracionDiario);
        jRadioButtonContingenciaGeneracionDiario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jRadioButtonContingenciaGeneracionDiario.setText("Diario");
        jPanelContingenciaInformacion.add(jRadioButtonContingenciaGeneracionDiario);

        jRadioButtonContingenciaGeneracionHistorico.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaInformacion.add(jRadioButtonContingenciaGeneracionHistorico);
        jRadioButtonContingenciaGeneracionHistorico.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jRadioButtonContingenciaGeneracionHistorico.setText("Historico");
        jPanelContingenciaInformacion.add(jRadioButtonContingenciaGeneracionHistorico);

        jRadioButtonContingenciaGeneracionAmbos.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaInformacion.add(jRadioButtonContingenciaGeneracionAmbos);
        jRadioButtonContingenciaGeneracionAmbos.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jRadioButtonContingenciaGeneracionAmbos.setSelected(true);
        jRadioButtonContingenciaGeneracionAmbos.setText("Ambos");
        jPanelContingenciaInformacion.add(jRadioButtonContingenciaGeneracionAmbos);

        jPanelContingenciaReporte.add(jPanelContingenciaInformacion, java.awt.BorderLayout.LINE_START);

        jScrollPaneContingenciaServicio.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneContingenciaServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Servicios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jScrollPaneContingenciaServicio.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jTableContingenciaServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTableContingenciaServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Descripción", "¿Activo?", "Seleccionar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableContingenciaServicio.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableContingenciaServicio.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneContingenciaServicio.setViewportView(jTableContingenciaServicio);

        jPanelContingenciaEnvio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContingenciaEnvio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Envió", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelContingenciaEnvio.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jRadioButtonContingenciaEnvioEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaEnvio.add(jRadioButtonContingenciaEnvioEmpresa);
        jRadioButtonContingenciaEnvioEmpresa.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jRadioButtonContingenciaEnvioEmpresa.setText("Utilizar la lista de correos de la empresa Definir los correos en la siguiente lista");

        jRadioButtonContingenciaEnvioUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroupContingenciaEnvio.add(jRadioButtonContingenciaEnvioUsuario);
        jRadioButtonContingenciaEnvioUsuario.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jRadioButtonContingenciaEnvioUsuario.setSelected(true);
        jRadioButtonContingenciaEnvioUsuario.setText("Definir los correos en la siguiente lista");

        jButtonContingenciaGenerarEnviar.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jButtonContingenciaGenerarEnviar.setForeground(new java.awt.Color(51, 51, 51));
        jButtonContingenciaGenerarEnviar.setText("Generar & Enviar");
        jButtonContingenciaGenerarEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonContingenciaGenerarEnviarMouseClicked(evt);
            }
        });

        jTextAreaContingenciaCorreo.setColumns(20);
        jTextAreaContingenciaCorreo.setRows(2);
        jScrollPaneContingenciaCorreo.setViewportView(jTextAreaContingenciaCorreo);

        javax.swing.GroupLayout jPanelContingenciaEnvioLayout = new javax.swing.GroupLayout(jPanelContingenciaEnvio);
        jPanelContingenciaEnvio.setLayout(jPanelContingenciaEnvioLayout);
        jPanelContingenciaEnvioLayout.setHorizontalGroup(
            jPanelContingenciaEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContingenciaEnvioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelContingenciaEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneContingenciaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonContingenciaEnvioUsuario)
                    .addComponent(jRadioButtonContingenciaEnvioEmpresa)
                    .addComponent(jButtonContingenciaGenerarEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanelContingenciaEnvioLayout.setVerticalGroup(
            jPanelContingenciaEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContingenciaEnvioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonContingenciaEnvioEmpresa)
                .addGap(11, 11, 11)
                .addComponent(jRadioButtonContingenciaEnvioUsuario)
                .addGap(9, 9, 9)
                .addComponent(jScrollPaneContingenciaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonContingenciaGenerarEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelContingenciaLayout = new javax.swing.GroupLayout(jPanelContingencia);
        jPanelContingencia.setLayout(jPanelContingenciaLayout);
        jPanelContingenciaLayout.setHorizontalGroup(
            jPanelContingenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContingenciaLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanelContingenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPaneContingenciaServicio, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelContingenciaLayout.createSequentialGroup()
                        .addGroup(jPanelContingenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelContingenciaFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                            .addComponent(jPanelContingenciaEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanelContingenciaReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelContingenciaEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 113, Short.MAX_VALUE))
        );
        jPanelContingenciaLayout.setVerticalGroup(
            jPanelContingenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContingenciaLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelContingenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelContingenciaLayout.createSequentialGroup()
                        .addComponent(jPanelContingenciaFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanelContingenciaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelContingenciaReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPaneContingenciaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelContingenciaEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelCuerpo.add(jPanelContingencia, "Contingencia");

        jPanelEmpresa.setBackground(new java.awt.Color(255, 255, 255));

        jPanelEmpresaComboBox.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEmpresaComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelEmpresaComboBox.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jPanelEmpresaComboBox.setLayout(new java.awt.BorderLayout());

        jComboEmpresaBoxEmpresa.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jComboEmpresaBoxEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccionar -" }));
        jComboEmpresaBoxEmpresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboEmpresaBoxEmpresaItemStateChanged(evt);
            }
        });
        jPanelEmpresaComboBox.add(jComboEmpresaBoxEmpresa, java.awt.BorderLayout.CENTER);

        jLabelEmpresaIBS.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabelEmpresaIBS.setForeground(new java.awt.Color(51, 51, 51));
        jLabelEmpresaIBS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaIBS.setText("IBS: ");

        jScrollPaneEmpresaCorreo.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneEmpresaCorreo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Correos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jScrollPaneEmpresaCorreo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jTextPaneEmpresaCorreo.setEditable(false);
        jScrollPaneEmpresaCorreo.setViewportView(jTextPaneEmpresaCorreo);

        jScrollPaneEmpresaServicio.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneEmpresaServicio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Servicios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jScrollPaneEmpresaServicio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jTableEmpresaServicio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Descripción", "Correos", "Estado", "Ver Conceptos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmpresaServicio.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jTableEmpresaServicio.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTableEmpresaServicio.getTableHeader().setReorderingAllowed(false);
        jTableEmpresaServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEmpresaServicioMouseClicked(evt);
            }
        });
        jScrollPaneEmpresaServicio.setViewportView(jTableEmpresaServicio);
        if (jTableEmpresaServicio.getColumnModel().getColumnCount() > 0) {
            jTableEmpresaServicio.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTableEmpresaServicio.getColumnModel().getColumn(1).setPreferredWidth(90);
        }

        jPanelEmpresaInformacion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEmpresaInformacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 15), java.awt.Color.darkGray)); // NOI18N
        jPanelEmpresaInformacion.setLayout(new java.awt.GridLayout(5, 2));

        jLabelTextEmpresaVersionPago.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelTextEmpresaVersionPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEmpresaVersionPago.setText("Versión Pago: ");
        jPanelEmpresaInformacion.add(jLabelTextEmpresaVersionPago);

        jLabelEmpresaVersionPago.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelEmpresaVersionPago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaVersionPago.setText("VP");
        jPanelEmpresaInformacion.add(jLabelEmpresaVersionPago);

        jLabelTextEmpresaNivelPago.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelTextEmpresaNivelPago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEmpresaNivelPago.setText("Nivel Pago: ");
        jPanelEmpresaInformacion.add(jLabelTextEmpresaNivelPago);

        jLabelEmpresaNivelPago.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelEmpresaNivelPago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaNivelPago.setText("NP");
        jPanelEmpresaInformacion.add(jLabelEmpresaNivelPago);

        jLabelTextEmpresaVersionSaldo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelTextEmpresaVersionSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEmpresaVersionSaldo.setText("Versión Saldo: ");
        jPanelEmpresaInformacion.add(jLabelTextEmpresaVersionSaldo);

        jLabelEmpresaVersionSaldo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelEmpresaVersionSaldo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaVersionSaldo.setText("VS");
        jPanelEmpresaInformacion.add(jLabelEmpresaVersionSaldo);

        jLabelTextEmpresaNivelSaldo.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelTextEmpresaNivelSaldo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEmpresaNivelSaldo.setText("Nivel Saldo:  ");
        jPanelEmpresaInformacion.add(jLabelTextEmpresaNivelSaldo);

        jLabelEmpresaNivelSaldo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelEmpresaNivelSaldo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaNivelSaldo.setText("NS");
        jPanelEmpresaInformacion.add(jLabelEmpresaNivelSaldo);

        jLabelTextEmpresaConcepto.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabelTextEmpresaConcepto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextEmpresaConcepto.setText("Concepto: ");
        jPanelEmpresaInformacion.add(jLabelTextEmpresaConcepto);

        jLabelEmpresaConcepto.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabelEmpresaConcepto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelEmpresaConcepto.setText("CO");
        jPanelEmpresaInformacion.add(jLabelEmpresaConcepto);

        javax.swing.GroupLayout jPanelEmpresaLayout = new javax.swing.GroupLayout(jPanelEmpresa);
        jPanelEmpresa.setLayout(jPanelEmpresaLayout);
        jPanelEmpresaLayout.setHorizontalGroup(
            jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpresaLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmpresaLayout.createSequentialGroup()
                            .addComponent(jPanelEmpresaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEmpresaIBS, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(13, 13, 13))
                        .addComponent(jScrollPaneEmpresaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEmpresaLayout.createSequentialGroup()
                        .addComponent(jPanelEmpresaInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPaneEmpresaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanelEmpresaLayout.setVerticalGroup(
            jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEmpresaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelEmpresaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEmpresaIBS, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanelEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneEmpresaCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelEmpresaInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPaneEmpresaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanelCuerpo.add(jPanelEmpresa, "Empresa");

        jPanelAyuda.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelAyudaLayout = new javax.swing.GroupLayout(jPanelAyuda);
        jPanelAyuda.setLayout(jPanelAyudaLayout);
        jPanelAyudaLayout.setHorizontalGroup(
            jPanelAyudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 931, Short.MAX_VALUE)
        );
        jPanelAyudaLayout.setVerticalGroup(
            jPanelAyudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );

        jPanelCuerpo.add(jPanelAyuda, "Ayuda");

        jPanelConfiguracion.setBackground(new java.awt.Color(255, 255, 255));

        jPanelConfiguracionInfo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelConfiguracionInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Variables de configuración", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jLabelTextConfiguracionConexion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionConexion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionConexion.setText("Conexión: ");

        jLabelConfiguracionConexion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionConexion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionConexion.setText("Conexion");

        jLabelTextConfiguracionUsuario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionUsuario.setText("Usuario: ");

        jLabelConfiguracionUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionUsuario.setText("Usuario");

        jLabelTextConfiguracionDirectorioInterno.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionDirectorioInterno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionDirectorioInterno.setText("Directorio Interno: ");

        jLabelConfiguracionDirectorioInterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionDirectorioInterno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionDirectorioInterno.setText("Directorio Interno");

        jLabelTextConfiguracionTiempo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionTiempo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionTiempo.setText("Tiempo: ");

        jLabelConfiguracionTiempo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionTiempo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionTiempo.setText("Tiempo");

        jLabelTextConfiguracionProducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionProducto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionProducto.setText("Producto Notificación:  ");

        jLabelConfiguracionProducto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionProducto.setText("Producto");

        jLabelTextConfiguracionDirectorioExterno.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelTextConfiguracionDirectorioExterno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTextConfiguracionDirectorioExterno.setText("Directorio Externo: ");

        jLabelConfiguracionDirectorioExterno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelConfiguracionDirectorioExterno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelConfiguracionDirectorioExterno.setText("Directorio Externo");

        javax.swing.GroupLayout jPanelConfiguracionInfoLayout = new javax.swing.GroupLayout(jPanelConfiguracionInfo);
        jPanelConfiguracionInfo.setLayout(jPanelConfiguracionInfoLayout);
        jPanelConfiguracionInfoLayout.setHorizontalGroup(
            jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionInfoLayout.createSequentialGroup()
                        .addComponent(jLabelTextConfiguracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelConfiguracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionInfoLayout.createSequentialGroup()
                        .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionInfoLayout.createSequentialGroup()
                                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabelTextConfiguracionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelTextConfiguracionConexion, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelConfiguracionConexion, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelConfiguracionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionInfoLayout.createSequentialGroup()
                                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelTextConfiguracionDirectorioExterno)
                                    .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabelTextConfiguracionDirectorioInterno, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                        .addComponent(jLabelTextConfiguracionTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelConfiguracionDirectorioInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelConfiguracionTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelConfiguracionDirectorioExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(231, 231, 231))))
        );
        jPanelConfiguracionInfoLayout.setVerticalGroup(
            jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionInfoLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionConexion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionConexion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionDirectorioInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionDirectorioInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionDirectorioExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionDirectorioExterno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelConfiguracionInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTextConfiguracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelConfiguracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelConfiguracionDoc.setBackground(new java.awt.Color(255, 255, 255));
        jPanelConfiguracionDoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Documentación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), java.awt.Color.darkGray)); // NOI18N

        jLabel2.setText("Repositorio: https://github.com/em10029/ReporteAtlantidaManual.git");

        jLabel3.setText("Versión: V.1.1");

        jLabel4.setText("Javadoc: https://em10029.github.io/DocReporteAtlantida");

        jLabel5.setText("Manual de usuario: ");

        javax.swing.GroupLayout jPanelConfiguracionDocLayout = new javax.swing.GroupLayout(jPanelConfiguracionDoc);
        jPanelConfiguracionDoc.setLayout(jPanelConfiguracionDocLayout);
        jPanelConfiguracionDocLayout.setHorizontalGroup(
            jPanelConfiguracionDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionDocLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelConfiguracionDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelConfiguracionDocLayout.setVerticalGroup(
            jPanelConfiguracionDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionDocLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelConfiguracionLayout = new javax.swing.GroupLayout(jPanelConfiguracion);
        jPanelConfiguracion.setLayout(jPanelConfiguracionLayout);
        jPanelConfiguracionLayout.setHorizontalGroup(
            jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelConfiguracionLayout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addGroup(jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelConfiguracionDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelConfiguracionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );
        jPanelConfiguracionLayout.setVerticalGroup(
            jPanelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConfiguracionLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanelConfiguracionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelConfiguracionDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jPanelCuerpo.add(jPanelConfiguracion, "Configuracion");

        getContentPane().add(jPanelCuerpo, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Menu");

        reporteMenuItem.setMnemonic('o');
        reporteMenuItem.setText("Inicio");
        fileMenu.add(reporteMenuItem);

        contingenciaMenuItem.setMnemonic('s');
        contingenciaMenuItem.setText("Generar Reporte");
        fileMenu.add(contingenciaMenuItem);

        empresaAsMenuItem.setMnemonic('a');
        empresaAsMenuItem.setText("Visualizar Empresa");
        fileMenu.add(empresaAsMenuItem);

        salirMenuItem.setMnemonic('x');
        salirMenuItem.setText("Salir");
        salirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(salirMenuItem);

        menuBar.add(fileMenu);

        ayudaMenu.setMnemonic('e');
        ayudaMenu.setText("Ayuda");

        ayudaMenuItem.setMnemonic('t');
        ayudaMenuItem.setText("Documentación");
        ayudaMenu.add(ayudaMenuItem);

        soporteMenuItem.setMnemonic('y');
        soporteMenuItem.setText("Soporte");
        ayudaMenu.add(soporteMenuItem);

        menuBar.add(ayudaMenu);

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     ************************************************************************
     * MENU *
     * ***********************************************************************
     */

    private void salirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirMenuItemActionPerformed

    private void jButtonReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReporteMouseClicked
        this.cambiarPanel("Reporte");
    }//GEN-LAST:event_jButtonReporteMouseClicked

    private void jButtonContingenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonContingenciaMouseClicked
        this.cambiarPanel("Contingencia");
    }//GEN-LAST:event_jButtonContingenciaMouseClicked

    private void jButtonEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpresaMouseClicked
        this.cambiarPanel("Empresa");
    }//GEN-LAST:event_jButtonEmpresaMouseClicked

    private void jButtonConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConfiguracionMouseClicked
        this.cambiarPanel("Configuracion");
    }//GEN-LAST:event_jButtonConfiguracionMouseClicked

    private void jTableReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableReporteMouseClicked

        int fila = this.jTableReporte.rowAtPoint(evt.getPoint());
        int columna = this.jTableReporte.getColumnModel().getColumnIndexAtX(evt.getX());
        //System.out.println("Posicion: (" + fila + "," + columna + ")");

        String key = this.modeloReporte.getValueAt(fila, 1).toString();
        //System.out.println("Key:" + key);
        Reporte reporte = null;

        switch (columna) {
            case 9: //Ver
                reporte = this.reportes.get(key);
                this.verReporte(reporte);
                this.cambiarPanel("Ver");
                break;

            case 10: //Editar                
                reporte = this.reportes.get(key);
                this.reporteRef = reporte;
                this.editarReporte(reporte);
                this.cambiarPanel("Editar");
                break;

            default:
                break;
        }
    }//GEN-LAST:event_jTableReporteMouseClicked

    private void jButtonContingenciaGenerarEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonContingenciaGenerarEnviarMouseClicked
        if (this.validarContingencia()) {
            this.procesarContingencia();
        }
    }//GEN-LAST:event_jButtonContingenciaGenerarEnviarMouseClicked

    private void jRadioButtonContingenciaReporteSaldoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonContingenciaReporteSaldoItemStateChanged

        if (this.jRadioButtonContingenciaReporteSaldo.isSelected()) {
            this.jRadioButtonContingenciaGeneracionDiario.setEnabled(false);
            this.jRadioButtonContingenciaGeneracionHistorico.setEnabled(false);
            this.jRadioButtonContingenciaGeneracionAmbos.setEnabled(false);
        } else {
            this.jRadioButtonContingenciaGeneracionDiario.setEnabled(true);
            this.jRadioButtonContingenciaGeneracionHistorico.setEnabled(true);
            this.jRadioButtonContingenciaGeneracionAmbos.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButtonContingenciaReporteSaldoItemStateChanged

    private void jComboContingenciaBoxEmpresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboContingenciaBoxEmpresaItemStateChanged

        Util.limpiarTabla(this.modeloContingenciaServicio); //Limpia la tabla de servicios
        int seleccion = this.jComboContingenciaBoxEmpresa.getSelectedIndex(); //Idice de seleccion
        if (seleccion > 0) {
            Data.Empresa empresa = Data.empresas.get(seleccion - 1);
            this.visualizarContingencia(empresa);
        }
    }//GEN-LAST:event_jComboContingenciaBoxEmpresaItemStateChanged

    private void jTableEditarCorreoServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEditarCorreoServicioMouseClicked

        int fila = this.jTableEditarCorreoServicio.rowAtPoint(evt.getPoint());
        int columna = this.jTableEditarCorreoServicio.getColumnModel().getColumnIndexAtX(evt.getX());

        String correos = "";
        if (columna == 4) {

            Servicio servicio = reporteRef.getEmpresa().getServicios().get(fila);
            String mensaje = servicio.toString() + " - " + servicio.getDescripcion();

            //Para que solo pueda editar los correos de los servicios que no se han enviado
            if (servicio.getEstadoEnvio().equals("I")) {

                correos = JOptionPane.showInputDialog(this, mensaje, servicio.getCorreos());

                if (correos != null) { //Preciono aceptar al dialogo de edicion de correos
                    correos = correos.trim();
                    servicio.setCorreos(correos); //Edita los correos del servicio
                    this.editarReporteCorreoServicio(this.reporteRef); //Actualizando tabla
                }

            } else {
                String info = "La notificación del servicio " + mensaje + " ya fue envida.";
                JOptionPane.showMessageDialog(this, info, mensaje, JOptionPane.INFORMATION_MESSAGE);
            }

        }
        //System.out.println(correos);        
    }//GEN-LAST:event_jTableEditarCorreoServicioMouseClicked

    private void jTableEditarCorreoEmpresaServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEditarCorreoEmpresaServicioMouseClicked

        int fila = this.jTableEditarCorreoEmpresaServicio.rowAtPoint(evt.getPoint());
        int columna = this.jTableEditarCorreoEmpresaServicio.getColumnModel().getColumnIndexAtX(evt.getX());

        String correos = "";
        if (columna == 3) {
            Servicio servicio = reporteRef.getEmpresa().getServicios().get(fila);
            String mensaje = servicio.toString() + " - " + servicio.getDescripcion();

            correos = JOptionPane.showInputDialog(this, mensaje, servicio.getCorreos());

            if (correos != null) { //Preciono aceptar al dialogo de edicion de correos
                correos = correos.trim();
                servicio.setCorreos(correos); //Edita los correos del servicio
                this.editarReporteCorreoEmpresaServicio(this.reporteRef); //Actualizando tabla
            }

        }
        //System.out.println(correos);
    }//GEN-LAST:event_jTableEditarCorreoEmpresaServicioMouseClicked

    private void jButtonReintentarCorreoDefinidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReintentarCorreoDefinidoMouseClicked
        //Verificar estado
        if (this.reporteRef.getEstado().equals("F")) {
            this.reporteRef.setCorreos(this.jTextAreaEditarCuerpoCorreo.getText().trim());
            this.reenviar();
        }
    }//GEN-LAST:event_jButtonReintentarCorreoDefinidoMouseClicked

    private void jButtonReintentarCorreoServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReintentarCorreoServicioMouseClicked

        //Verificar estado
        if (this.reporteRef.getEstado().equals("F")) {
            ArrayList<Servicio> servicios = new ArrayList<>();
            ArrayList<Servicio> serviciosEnvio = new ArrayList<>(); //Lista de servicios que no han sido enviados

            for (Servicio servicio : this.reporteRef.getEmpresa().getServicios()) {
                servicios.add(servicio);
                if (servicio.getEstadoEnvio().equals("I")) {
                    serviciosEnvio.add(servicio);
                }
            }

            //Hay archivos para enviar
            if (serviciosEnvio.size() > 0) {
                this.reporteRef.getEmpresa().setServicios(serviciosEnvio);
                this.reenviar();
                this.reporteRef.getEmpresa().setServicios(servicios);
            }

            //Actualizacion de vista
            this.editarReporte(this.reporteRef);
        }

    }//GEN-LAST:event_jButtonReintentarCorreoServicioMouseClicked

    private void jButtonReintentarCorreoEmpresaServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReintentarCorreoEmpresaServicioMouseClicked
        //Verificar estado
        if (this.reporteRef.getEstado().equals("F")) {
            this.reporteRef.getEmpresa().setCorreos(this.jTextAreaEditarCorreoEmpresaServicio.getText());
            this.reenviar();
        }
    }//GEN-LAST:event_jButtonReintentarCorreoEmpresaServicioMouseClicked

    private void jButtonReintentarFTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReintentarFTPMouseClicked
        //Verificar estado
        if (this.reporteRef.getEstado().equals("F")) {
            this.reporteRef.getEmpresa().setUrl(this.jTextFieldFTPServidor.getText().trim());
            this.reporteRef.getEmpresa().setDirectorio(this.jTextFieldFTPDirectorio.getText().trim());
            this.reporteRef.getEmpresa().setUser(this.jTextFieldUsuario.getText().trim());
            this.reporteRef.getEmpresa().setPassword(this.jTextFieldClave.getText().trim());            
            this.reporteRef.getEmpresa().setCorreos(this.jTextAreaEditarCorreoEmpresaServicio.getText());
            this.reenviar();
        }
    }//GEN-LAST:event_jButtonReintentarFTPMouseClicked

    private void jComboEmpresaBoxEmpresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboEmpresaBoxEmpresaItemStateChanged

        Util.limpiarTabla(this.modeloEmpresaServicio); //Limpia la tabla de servicios

        this.jLabelEmpresaIBS.setText("");
        this.jLabelEmpresaVersionPago.setText("");
        this.jLabelEmpresaNivelPago.setText("");
        this.jLabelEmpresaVersionSaldo.setText("");
        this.jLabelEmpresaNivelSaldo.setText("");
        this.jLabelEmpresaConcepto.setText("");
        this.jTextPaneEmpresaCorreo.setText("");

        int seleccion = this.jComboEmpresaBoxEmpresa.getSelectedIndex(); //Idice de seleccion
        if (seleccion > 0) {
            Data.Empresa empresa = Data.empresas.get(seleccion - 1);
            this.visualizarEmpresa(empresa);
            this.empresaRef = empresa;
        }
    }//GEN-LAST:event_jComboEmpresaBoxEmpresaItemStateChanged

    private void jTableEmpresaServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEmpresaServicioMouseClicked

        int fila = this.jTableEmpresaServicio.rowAtPoint(evt.getPoint());
        int columna = this.jTableEmpresaServicio.getColumnModel().getColumnIndexAtX(evt.getX());

        if (columna == 4) {
            this.dialogo.visualizar(this.empresaRef, this.empresaRef.servicios.get(fila));
            this.dialogo.setVisible(true);
        }

    }//GEN-LAST:event_jTableEmpresaServicioMouseClicked

    private void jButtonAyudaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAyudaMouseClicked
        //this.cambiarPanel("Ayuda");
    }//GEN-LAST:event_jButtonAyudaMouseClicked

    /**
     ************************************************************************
     * REPORTE *
     * ***********************************************************************
     */
    //****************** REPORTES ******************//
    public void agregarReporte(Reporte reporte) {

        //Ver
        javax.swing.JLabel ver = new javax.swing.JLabel();
        ver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/ver.png")));

        //Editar
        javax.swing.JLabel editar = new javax.swing.JLabel();
        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/editar.png")));

        Object[] data = {
            Reporte.contador,
            reporte.toString(),
            reporte.getEmpresa().getNombre(),
            reporte.getEmpresa(),
            reporte.getInformacion(),
            reporte.getContenido(),
            reporte.getFechaInicial() + " - " + reporte.getFechaFinal(),
            reporte.getGeneracion(),
            reporte.getEstado(),
            ver,
            editar
        };
        this.modeloReporte.addRow(data); //Agregandolo el registro a la tabla        
    }

    public void actualizarReporte(Reporte reporte) {
        if (reporte.getEstado().equals("A")) {
            this.modeloReporte.setValueAt("A", reporte.getSecuencia() - 1, 8);
        }
    }

    //****************** REPORTES - VER ******************//
    private void verReporte(Reporte reporte) {

        Empresa empresa = reporte.getEmpresa();
        ArrayList<Servicio> servicios = empresa.getServicios();
        ArrayList<Archivo> archivos = reporte.getArchivos();

        //************* Encabezado *************//
        this.jLabelVerEmpresa.setText(empresa.getNombre());

        //************* Informacion *************//
        this.jLabelID.setText(reporte.toString());
        this.jLabelEstado.setText(reporte.getEstado());
        this.jLabelIBS.setText(empresa.toString());
        this.jLabelInformacion.setText(reporte.getInformacion());
        this.jLabelFechas.setText(reporte.getFechaInicial() + " - " + reporte.getFechaFinal());
        this.jLabelConcepto.setText(empresa.getConcepto());
        this.jLabelVersion.setText(empresa.getVersion());
        this.jLabelNivel.setText(empresa.getNivel());
        this.jLabelGeneracion.setText(reporte.getGeneracion());
        this.jLabelContenido.setText(reporte.getContenido());
        this.jLabelDestino.setText(reporte.getDestino());

        //************* Servicios *************//
        Util.limpiarTabla(this.modeloVerServicio);
        for (Servicio servicio : servicios) {

            Object[] data = {
                servicio.toString(),
                servicio.getDescripcion(),
                servicio.getTransacciones(),
                servicio.getEstadoEnvio().equals("A")
            };
            this.modeloVerServicio.addRow(data); //Agregandolo el registro a la tabla  
        }

        //************* Archivos *************//
        this.jLabelVerDirectorio.setText(reporte.getDirectorio().getUbicacion());
        Util.limpiarTabla(this.modeloVerArchivo);
        for (int i = 0; i < archivos.size(); i++) {
            Object[] data = {
                i + 1,
                archivos.get(i).getNombre(),
                archivos.get(i).isGenerado(),
                archivos.get(i).isEnviado()
            };
            this.modeloVerArchivo.addRow(data); //Agregandolo el registro a la tabla  
        }
    }

    //****************** REPORTES - EDITAR ******************//
    private void editarReporte(Reporte reporte) {

        Empresa empresa = reporte.getEmpresa();

        //Validacion de carta
        String destino = "";
        String envio = "";
        String infoEnvio = "";
        String card = "";

        //DESTINO Y ENVIO
        if (reporte.getDestino().equals("S")) { //S: Por definicion, puede ser COR o FTP
            if (reporte.getEmpresa().getTipoEnvio().equals("COR")) { //Correo electronico
                destino = "S - COR - Correo Electrónico";

                if (reporte.getEmpresa().getNivel().equals("NIVELEMP")) { //Empresa
                    envio = "Correos de la empresa y de los servicios";
                    infoEnvio += "Correos empresa: " + reporte.getEmpresa().getCorreos() + "\r\n";

                    card = "CorreoEmpresaServicio";
                } else { //Servicios
                    envio = "Correos de los servicios";

                    card = "CorreoServicio";
                }

                //Correos de servicios
                for (Servicio servicio : reporte.getEmpresa().getServicios()) {
                    infoEnvio += "Correos servicio " + servicio.toString() + " : " + servicio.getCorreos() + "\r\n";
                }

                //Copias ocultas
                infoEnvio += "Copias ocultas (CCO): " + reporte.getEmpresa().getCopiasOcultas() + "\r\n";

            } else { //Servidor de archivos
                destino = "S - FTP - Servidor de archivos";
                envio = "Server";
                infoEnvio += "URL: " + reporte.getEmpresa().getUrl() + "\r\n";
                infoEnvio += "Directorio: " + reporte.getEmpresa().getDirectorio() + "\r\n";
                infoEnvio += "Código de envío: " + reporte.getEmpresa().getCodigoEnvio() + "\r\n";

                card = "FTP";
            }
        } else { //N: Correo electronico, otros correos
            destino = "N - Correo Electrónico";
            envio = "Correos definidos";
            infoEnvio += "Correos: " + reporte.getCorreos() + "\r\n";
            //Copias ocultas
            infoEnvio += "Copias ocultas (CCO): " + reporte.getEmpresa().getCopiasOcultas() + "\r\n";

            card = "CorreoDefinido";
        }

        //****Encabezado***//
        this.jLabelEditarPeticion.setText(reporte.toString());
        this.jLabelEditarEmpresa.setText(empresa.getNombre());
        this.jLabelEditarDestino.setText(destino);
        this.jLabelEditarEnvio.setText(envio);

        //Preparando la vista
        switch (card) {
            case "CorreoDefinido": //N-Correos definidos por el usuario                
                this.editarReporteCorreoDefinido(reporte);
                break;
            case "CorreoServicio": //S-Correos de los servicios
                this.editarReporteCorreoServicio(reporte);
                break;
            case "CorreoEmpresaServicio": //S-Correos de la empresa y los servicios
                this.editarReporteCorreoEmpresaServicio(reporte);
                break;
            case "FTP": //S-Servidor FTP
                this.editarReporteFTP(reporte);
                break;
        }

        //Seleccion el panel del editor
        this.cotroladorEditar.show(this.jPanelEditarCuerpo, card);
    }

    private void editarReporteCorreoDefinido(Reporte reporte) {

        this.jTextAreaEditarCuerpoCorreo.setText(reporte.getCorreos());

        //Deshabilitacion de componentes
        if (reporte.getEstado().equals("A")) {
            this.jTextAreaEditarCuerpoCorreo.setEditable(false);
            this.jButtonReintentarCorreoDefinido.setEnabled(false);
        } else {
            this.jTextAreaEditarCuerpoCorreo.setEditable(true);
            this.jButtonReintentarCorreoDefinido.setEnabled(true);
        }
    }

    private void editarReporteCorreoServicio(Reporte reporte) {

        Util.limpiarTabla(this.modeloEditarCorreoServicio);

        for (Servicio servicio : reporte.getEmpresa().getServicios()) {

            //Editar
            javax.swing.JLabel editar = new javax.swing.JLabel();
            editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/editar.png")));

            Object[] data = {
                servicio.toString(),
                servicio.getDescripcion(),
                servicio.getCorreos(),
                servicio.getEstadoEnvio().equals("A"),
                editar
            };

            this.modeloEditarCorreoServicio.addRow(data);
        }

        this.reporteRef = reporte;

        //Deshabilitacion de componentes
        if (reporte.getEstado().equals("A")) {
            this.jTableEditarCorreoServicio.setEnabled(false);
            this.jButtonReintentarCorreoServicio.setEnabled(false);
        } else {
            this.jTableEditarCorreoServicio.setEnabled(true);
            this.jButtonReintentarCorreoServicio.setEnabled(true);
        }
    }

    private void editarReporteCorreoEmpresaServicio(Reporte reporte) {

        this.jTextAreaEditarCorreoEmpresaServicio.setText(reporte.getEmpresa().getCorreos());

        Util.limpiarTabla(this.modeloEditarCorreoEmpresaServicio);

        for (Servicio servicio : reporte.getEmpresa().getServicios()) {

            //Editar
            javax.swing.JLabel editar = new javax.swing.JLabel();
            editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/editar.png")));

            Object[] data = {
                servicio.toString(),
                servicio.getDescripcion(),
                servicio.getCorreos(),
                editar
            };

            this.modeloEditarCorreoEmpresaServicio.addRow(data);
        }

        this.reporteRef = reporte;

        //Deshabilitacion de componentes
        if (reporte.getEstado().equals("A")) {
            this.jTextAreaEditarCorreoEmpresaServicio.setEditable(false); //Correos empresa
            this.jTableEditarCorreoEmpresaServicio.setEnabled(false); //Correos servicios            
            this.jButtonReintentarCorreoEmpresaServicio.setEnabled(false); //Boton reenvio
        } else {
            this.jTextAreaEditarCorreoEmpresaServicio.setEditable(true); //Correos empresa
            this.jTableEditarCorreoEmpresaServicio.setEnabled(true); //Correos servicios            
            this.jButtonReintentarCorreoEmpresaServicio.setEnabled(true); //Boton reenvio
        }
    }

    private void editarReporteFTP(Reporte reporte) {        
        
        Empresa empresa = reporte.getEmpresa();
        
        this.jTextFieldFTPServidor.setText(reporte.getEmpresa().getUrl());
        this.jTextFieldFTPDirectorio.setText(reporte.getEmpresa().getDirectorio());
        this.jTextFieldUsuario.setText(reporte.getEmpresa().getUser());
        this.jTextFieldClave.setText(reporte.getEmpresa().getPassword());
        
        try {
            String ipFormato = FTP.getIPFormato(empresa.getUrl());            
            String ip = Arrays.toString(FTP.getIP(ipFormato));
            String ayuda = "IP formato: " + ipFormato + " - IP Byte: " + ip;
            this.jLabelFTPAyuda.setText(ayuda);
        } catch (ReporteAtlantidaExcepcion ex) {
            Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(reporte.getEstado().equals("F")){
            this.jButtonReintentarFTP.setEnabled(true);
        }else{
            this.jButtonReintentarFTP.setEnabled(false);
        }
        
    }

    /**
     ************************************************************************
     * CONTINGENCIA *
     * ***********************************************************************
     */
    /**
     * Muestra los servicios correspondientes de la empresa.
     *
     * @param empresa
     */
    private void visualizarContingencia(Data.Empresa empresa) {
        for (Data.Servicio servicio : empresa.servicios) {
            Object[] data = {
                servicio.identificador,
                servicio.nombre,
                servicio.estado,
                servicio.seleccionado,};
            this.modeloContingenciaServicio.addRow(data);
        }
    }

    /**
     * Realiza la validación de los datos de la petición.
     *
     * @return boolean
     */
    private boolean validarContingencia() {

        //*********** FECHAS ***********//
        //Validacion de fechas
        Calendar desde = this.jDateChooserContingenciaDesde.getCalendar();
        Calendar hasta = this.jDateChooserContingenciaHasta.getCalendar();

        //Fecha inicial mayor que la fecha final
        if (desde.compareTo(hasta) > 0) {
            JOptionPane.showConfirmDialog(this, "La fecha inical no puede ser mayor que la fecha final.", "Error de datos", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

        //*********** EMPRESA ***********//
        //Empresa seleccionada
        int seleccion = this.jComboContingenciaBoxEmpresa.getSelectedIndex(); //Idice de seleccion 
        if (seleccion == 0) {
            JOptionPane.showConfirmDialog(this, "Seleccione una empresa.", "Error de datos", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

        //*********** SERVICIOS ***********//
        //Obtiene la empresa seleccionada        
        Data.Empresa empresa = Data.empresas.get(seleccion - 1);
        //Se asigna la seleccion en los servicios
        for (int i = 0; i < empresa.servicios.size(); i++) {
            empresa.servicios.get(i).seleccionado = (boolean) this.modeloContingenciaServicio.getValueAt(i, 3);
        }
        //Comprovando que haya selecionado por menos un servicio
        boolean seleccionoServicio = false;
        for (Data.Servicio servicio : empresa.servicios) {
            if (servicio.seleccionado) {
                seleccionoServicio = true;
            }
        }

        //No ha selecionado ningun servicio
        if (!seleccionoServicio) {
            JOptionPane.showConfirmDialog(this, "No ha seleccionado ningun servicio.", "Error de datos", JOptionPane.PLAIN_MESSAGE);
            return false;
        }

        //*********** ENVIO ***********//
        if (this.jRadioButtonContingenciaEnvioUsuario.isSelected()) {
            if (jTextAreaContingenciaCorreo.getText().trim().isEmpty()) {
                JOptionPane.showConfirmDialog(this, "Ingrese los correos detinatarios.", "Error de datos", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        }

        return true;
    }

    /**
     * Escribe en la CAECEA y CAECEAS la petición.
     */
    private void procesarContingencia() {

        String fecha = reporte.atlantida.utilitario.Util.getFechaHoraActual("yyyyMMdd");
        String hora = reporte.atlantida.utilitario.Util.getFechaHoraActual("hh:mm:ss");
        //String canal = "\'F\'";
        //String correlativo = "9999";

        //Obtiene el rango de fechas
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaInicial = formato.format(this.jDateChooserContingenciaDesde.getCalendar().getTime());
        String fechaFinal = formato.format(this.jDateChooserContingenciaHasta.getCalendar().getTime());

        //Obtiene el tipo de archivo y el contenido
        String informacion = "SALDOS";
        String contenido = "T";
        String generacion = "M";

        if (this.jRadioButtonContingenciaReportePago.isSelected()) {
            informacion = "PAGOS";
            if (jRadioButtonContingenciaGeneracionDiario.isSelected()) {
                contenido = "D";
                generacion = "A";
            }
            if (jRadioButtonContingenciaGeneracionHistorico.isSelected()) {
                contenido = "H";
                generacion = "C";
            }
        }

        //Obtiene la empresa seleccionada
        int seleccion = this.jComboContingenciaBoxEmpresa.getSelectedIndex(); //Idice de seleccion 
        Data.Empresa empresa = Data.empresas.get(seleccion - 1);

        //Obtiene los servicio selecciones         
        for (int i = 0; i < empresa.servicios.size(); i++) {
            empresa.servicios.get(i).seleccionado = (boolean) this.modeloContingenciaServicio.getValueAt(i, 3);
        }

        //Envio
        String destino = "S";
        String correos = "";
        if (this.jRadioButtonContingenciaEnvioUsuario.isSelected()) {
            destino = "N";
            correos = this.jTextAreaContingenciaCorreo.getText();
        }

        //********* REALIZA LA PETICION *********//
        if (DB2.abrir()) {

            PreparedStatement ps = null;

            try {
                ps = DB2.getConexion().prepareStatement(QUERY_INSERT_CAECEA);
                ps.setString(1, fecha);
                ps.setString(2, hora);
                //ps.setString(3, canal);
                //ps.setString(4, correlativo);
                ps.setString(3, contenido);
                ps.setString(4, "F");
                ps.setString(5, informacion);
                ps.setString(6, fechaInicial);
                ps.setString(7, fechaFinal);
                ps.setString(8, empresa.identificador);
                ps.setString(9, destino);
                ps.setString(10, correos);
                ps.setString(11, generacion);

                int exito = ps.executeUpdate(); //Escribio en CAECEA

                if (exito == 1) {

                    PreparedStatement ps2 = null;

                    for (Data.Servicio servicio : empresa.servicios) {
                        if (servicio.seleccionado) {
                            try {
                                ps2 = DB2.getConexion().prepareStatement(QUERY_INSERT_CAECEAS);
                                ps2.setString(1, fecha);
                                ps2.setString(2, hora);
                                //ps2.setString(3, canal);
                                //ps2.setString(4, correlativo);
                                ps2.setString(3, servicio.identificador);
                                ps2.setString(4, "I");
                                ps2.executeUpdate(); //Escribio en CAECEAS
                            } catch (SQLException ex) {
                                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                Query.cerrar(ps2);
                            }
                        }
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Query.cerrar(ps);
            }

            DB2.cerrar();
        }

        //Muestra mensaje exito
        String peticion = fecha + "-" + hora + "-F-9999";
        JOptionPane.showConfirmDialog(this, "Petición realizada con éxito. (" + peticion + ")", "Información", JOptionPane.PLAIN_MESSAGE);

        this.limpiarContingencia(); //Limpia componentes

    }

    /**
     * Limpia los componentes.
     */
    private void limpiarContingencia() {
        //Fechas default
        Calendar calendar = Calendar.getInstance();
        this.jDateChooserContingenciaDesde.setCalendar(calendar);
        this.jDateChooserContingenciaHasta.setCalendar(calendar);

        //Archivos
        this.jRadioButtonContingenciaReportePago.setSelected(true);
        this.jRadioButtonContingenciaGeneracionAmbos.setSelected(true);

        //Empresa
        this.jComboContingenciaBoxEmpresa.setSelectedIndex(0);

        //Servicios
        Util.limpiarTabla(this.modeloContingenciaServicio);

        //Envio
        this.jRadioButtonContingenciaEnvioUsuario.setSelected(true);
        this.jTextAreaContingenciaCorreo.setText("");
    }

    /**
     ************************************************************************
     * REENVIAR *
     * **********************************************************************
     */
    private void reenviar() {

        //Reintento de envio
        Proceso.enviar(this.reporteRef);

        //Actualicion de data CAECEA y CAECEAS
        if (Controlador.DB2.abrir()) {
            Proceso.actualizar(Controlador.DB2, this.reporteRef);
            Controlador.DB2.cerrar();
        }

        //Actulaizacion de estructura
        this.actualizarReporte(this.reporteRef); //Tabla Reporte

        //Actualizacion de vista
        this.editarReporte(this.reporteRef);

        //Mensaje de reenvio
        String info = this.reporteRef.toString() + " : " + this.reporteRef.getEmpresa().getNombre();
        String mensaje = "";

        //Verifica nivel
        if (this.reporteRef.getDestino().equals("S")) { //S: Por definicion, puede ser COR o FTP
            if (this.reporteRef.getEmpresa().getTipoEnvio().equals("COR")) { //Correo electronico

                if (this.reporteRef.getEmpresa().getNivel().equals("NIVELEMP")) { //Empresa
                    //Una notificacion
                    if (this.reporteRef.getEstado().equals("A")) {
                        mensaje = "La notificación fue envida con exito.";
                    } else {
                        mensaje = "La notificación no fue envida.";
                    }
                } else {
                    //Varias notificaciones
                    for (Servicio servicio : this.reporteRef.getEmpresa().getServicios()) {
                        if (servicio.getEstadoEnvio().equals("A")) {
                            mensaje += "\r\nLa notificación del servicio " + servicio.toString() + " - " + servicio.getDescripcion() + " fue enviada.";
                        } else {
                            mensaje += "\r\nLa notificación del servicio " + servicio.toString() + " - " + servicio.getDescripcion() + " no fue enviada.";
                        }
                    }
                }

            } else { //Servidor de archivos
                //Servidor FTP
            }
        } else //N: Correo electronico, otros correos
        //Una notificacion       
         if (this.reporteRef.getEstado().equals("A")) {
                mensaje = "La notificación fue envida con exito.";
            } else {
                mensaje = "La notificación no fue envida.";
            }

        JOptionPane.showMessageDialog(this, mensaje, info, JOptionPane.INFORMATION_MESSAGE);

        Controlador.registrarProceso(this.reporteRef); //Registra el proceso en el log
    }

    /**
     ************************************************************************
     * EMPRESA *
     * **********************************************************************
     */
    /**
     * Muestra los servicios correspondientes de la empresa.
     *
     * @param empresa
     */
    private void visualizarEmpresa(Data.Empresa empresa) {

        this.jLabelEmpresaIBS.setText("IBS: " + empresa.identificador);
        this.jLabelEmpresaVersionPago.setText(empresa.versionPago);
        this.jLabelEmpresaNivelPago.setText(empresa.nivelPago);
        this.jLabelEmpresaVersionSaldo.setText(empresa.versionSaldo);
        this.jLabelEmpresaNivelSaldo.setText(empresa.nivelSaldo);
        this.jLabelEmpresaConcepto.setText(empresa.concepto);
        this.jTextPaneEmpresaCorreo.setText(empresa.correos);

        for (Data.Servicio servicio : empresa.servicios) {

            //Ver
            javax.swing.JLabel ver = new javax.swing.JLabel();
            ver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reporte/atlantida/manual/utilitario/imagen/ver.png")));

            Object[] data = {
                servicio.identificador,
                servicio.nombre,                
                servicio.correos,
                servicio.estado,
                ver
            };

            this.modeloEmpresaServicio.addRow(data);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JMenuItem ayudaMenuItem;
    private javax.swing.ButtonGroup buttonGroupContingenciaArchivo;
    private javax.swing.ButtonGroup buttonGroupContingenciaEnvio;
    private javax.swing.ButtonGroup buttonGroupContingenciaInformacion;
    private javax.swing.JMenuItem contingenciaMenuItem;
    private javax.swing.JMenuItem empresaAsMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton jButtonAyuda;
    private javax.swing.JButton jButtonConfiguracion;
    private javax.swing.JButton jButtonContingencia;
    private javax.swing.JButton jButtonContingenciaGenerarEnviar;
    private javax.swing.JButton jButtonEmpresa;
    private javax.swing.JButton jButtonReintentarCorreoDefinido;
    private javax.swing.JButton jButtonReintentarCorreoEmpresaServicio;
    private javax.swing.JButton jButtonReintentarCorreoServicio;
    private javax.swing.JButton jButtonReintentarFTP;
    private javax.swing.JButton jButtonReporte;
    private javax.swing.JComboBox<String> jComboContingenciaBoxEmpresa;
    private javax.swing.JComboBox<String> jComboEmpresaBoxEmpresa;
    private com.toedter.calendar.JDateChooser jDateChooserContingenciaDesde;
    private com.toedter.calendar.JDateChooser jDateChooserContingenciaHasta;
    private javax.swing.JLabel jLabeTextInformacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelConcepto;
    private javax.swing.JLabel jLabelConfiguracionConexion;
    private javax.swing.JLabel jLabelConfiguracionDirectorioExterno;
    private javax.swing.JLabel jLabelConfiguracionDirectorioInterno;
    private javax.swing.JLabel jLabelConfiguracionProducto;
    private javax.swing.JLabel jLabelConfiguracionTiempo;
    private javax.swing.JLabel jLabelConfiguracionUsuario;
    private javax.swing.JLabel jLabelContenido;
    private javax.swing.JLabel jLabelCorreoDefinido;
    private javax.swing.JLabel jLabelDestino;
    private javax.swing.JLabel jLabelEditarCorreoEmpresaServicio;
    private javax.swing.JLabel jLabelEditarCorreoServicio;
    private javax.swing.JLabel jLabelEditarDestino;
    private javax.swing.JLabel jLabelEditarEmpresa;
    private javax.swing.JLabel jLabelEditarEnvio;
    private javax.swing.JLabel jLabelEditarPeticion;
    private javax.swing.JLabel jLabelEmpresaConcepto;
    private javax.swing.JLabel jLabelEmpresaIBS;
    private javax.swing.JLabel jLabelEmpresaNivelPago;
    private javax.swing.JLabel jLabelEmpresaNivelSaldo;
    private javax.swing.JLabel jLabelEmpresaVersionPago;
    private javax.swing.JLabel jLabelEmpresaVersionSaldo;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelFTPAyuda;
    private javax.swing.JLabel jLabelFTPClave;
    private javax.swing.JLabel jLabelFTPDirectorio;
    private javax.swing.JLabel jLabelFTPServidor;
    private javax.swing.JLabel jLabelFTPUsuario;
    private javax.swing.JLabel jLabelFechas;
    private javax.swing.JLabel jLabelGeneracion;
    private javax.swing.JLabel jLabelIBS;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelInformacion;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelNivel;
    private javax.swing.JLabel jLabelTextConcepto;
    private javax.swing.JLabel jLabelTextConfiguracionConexion;
    private javax.swing.JLabel jLabelTextConfiguracionDirectorioExterno;
    private javax.swing.JLabel jLabelTextConfiguracionDirectorioInterno;
    private javax.swing.JLabel jLabelTextConfiguracionProducto;
    private javax.swing.JLabel jLabelTextConfiguracionTiempo;
    private javax.swing.JLabel jLabelTextConfiguracionUsuario;
    private javax.swing.JLabel jLabelTextContenido;
    private javax.swing.JLabel jLabelTextDestino;
    private javax.swing.JLabel jLabelTextEditarDestino;
    private javax.swing.JLabel jLabelTextEditarEnvio;
    private javax.swing.JLabel jLabelTextEditarPeticion;
    private javax.swing.JLabel jLabelTextEmpresaConcepto;
    private javax.swing.JLabel jLabelTextEmpresaNivelPago;
    private javax.swing.JLabel jLabelTextEmpresaNivelSaldo;
    private javax.swing.JLabel jLabelTextEmpresaVersionPago;
    private javax.swing.JLabel jLabelTextEmpresaVersionSaldo;
    private javax.swing.JLabel jLabelTextEstado;
    private javax.swing.JLabel jLabelTextFechas;
    private javax.swing.JLabel jLabelTextGeneracion;
    private javax.swing.JLabel jLabelTextIBS;
    private javax.swing.JLabel jLabelTextID;
    private javax.swing.JLabel jLabelTextNivel;
    private javax.swing.JLabel jLabelTextVersion;
    private javax.swing.JLabel jLabelVerDirectorio;
    private javax.swing.JLabel jLabelVerEmpresa;
    private javax.swing.JLabel jLabelVerTextDirectorio;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanelAyuda;
    private javax.swing.JPanel jPanelConfiguracion;
    private javax.swing.JPanel jPanelConfiguracionDoc;
    private javax.swing.JPanel jPanelConfiguracionInfo;
    private javax.swing.JPanel jPanelContingencia;
    private javax.swing.JPanel jPanelContingenciaArchivo;
    private javax.swing.JPanel jPanelContingenciaEmpresa;
    private javax.swing.JPanel jPanelContingenciaEnvio;
    private javax.swing.JPanel jPanelContingenciaFecha;
    private javax.swing.JPanel jPanelContingenciaInformacion;
    private javax.swing.JPanel jPanelContingenciaReporte;
    private javax.swing.JPanel jPanelCorreoDefinidoEnvio;
    private javax.swing.JPanel jPanelCorreoDefinidoLista;
    private javax.swing.JPanel jPanelCorreoEmpresaServicioEnvio;
    private javax.swing.JPanel jPanelCorreoServicioEnvio;
    private javax.swing.JPanel jPanelCuerpo;
    private javax.swing.JPanel jPanelDescripsion;
    private javax.swing.JPanel jPanelEditar;
    private javax.swing.JPanel jPanelEditarCorreoEmpresaServicio;
    private javax.swing.JPanel jPanelEditarCuerpo;
    private javax.swing.JPanel jPanelEditarCuerpoCorreoDefinido;
    private javax.swing.JPanel jPanelEditarCuerpoCorreoEmpresaServicio;
    private javax.swing.JPanel jPanelEditarCuerpoCorreoServicio;
    private javax.swing.JPanel jPanelEditarCuerpoFTP;
    private javax.swing.JPanel jPanelEditarEncabezado;
    private javax.swing.JPanel jPanelEditarInfo;
    private javax.swing.JPanel jPanelEmpresa;
    private javax.swing.JPanel jPanelEmpresaComboBox;
    private javax.swing.JPanel jPanelEmpresaInformacion;
    private javax.swing.JPanel jPanelEncabezado;
    private javax.swing.JPanel jPanelFTPEnvio;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelReporte;
    private javax.swing.JPanel jPanelVer;
    private javax.swing.JPanel jPanelVerArchivo;
    private javax.swing.JPanel jPanelVerCuerpo;
    private javax.swing.JPanel jPanelVerEncabezado;
    private javax.swing.JPanel jPanelVerInfo;
    private javax.swing.JRadioButton jRadioButtonContingenciaEnvioEmpresa;
    private javax.swing.JRadioButton jRadioButtonContingenciaEnvioUsuario;
    private javax.swing.JRadioButton jRadioButtonContingenciaGeneracionAmbos;
    private javax.swing.JRadioButton jRadioButtonContingenciaGeneracionDiario;
    private javax.swing.JRadioButton jRadioButtonContingenciaGeneracionHistorico;
    private javax.swing.JRadioButton jRadioButtonContingenciaReportePago;
    private javax.swing.JRadioButton jRadioButtonContingenciaReporteSaldo;
    private javax.swing.JScrollPane jScrollPaneContingenciaCorreo;
    private javax.swing.JScrollPane jScrollPaneContingenciaServicio;
    private javax.swing.JScrollPane jScrollPaneEditarCorreoEmpresaServicio;
    private javax.swing.JScrollPane jScrollPaneEditarCorreoServicio;
    private javax.swing.JScrollPane jScrollPaneEditarCuerpoCorreo;
    private javax.swing.JScrollPane jScrollPaneEditarCuerpoCorreoEmpresaServicio;
    private javax.swing.JScrollPane jScrollPaneEmpresaCorreo;
    private javax.swing.JScrollPane jScrollPaneEmpresaServicio;
    private javax.swing.JScrollPane jScrollPaneReporte;
    private javax.swing.JScrollPane jScrollPaneVerArchivo;
    private javax.swing.JScrollPane jScrollPaneVerServicio;
    private javax.swing.JSeparator jSeparatorEditar;
    private javax.swing.JSeparator jSeparatorVer;
    private javax.swing.JTable jTableContingenciaServicio;
    private javax.swing.JTable jTableEditarCorreoEmpresaServicio;
    private javax.swing.JTable jTableEditarCorreoServicio;
    private javax.swing.JTable jTableEmpresaServicio;
    private javax.swing.JTable jTableReporte;
    private javax.swing.JTable jTableVerArchivo;
    private javax.swing.JTable jTableVerServicio;
    private javax.swing.JTextArea jTextAreaContingenciaCorreo;
    private javax.swing.JTextArea jTextAreaEditarCorreoEmpresaServicio;
    private javax.swing.JTextArea jTextAreaEditarCuerpoCorreo;
    private javax.swing.JTextField jTextFieldClave;
    private javax.swing.JTextField jTextFieldFTPDirectorio;
    private javax.swing.JTextField jTextFieldFTPServidor;
    private javax.swing.JTextField jTextFieldUsuario;
    private javax.swing.JTextPane jTextPaneEmpresaCorreo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem reporteMenuItem;
    private javax.swing.JMenuItem salirMenuItem;
    private javax.swing.JMenuItem soporteMenuItem;
    // End of variables declaration//GEN-END:variables

}
