package frontMon;

import Materia.Asignatura;
import Materia.registrarAsig;
import Materia.vistaPrevia;
import back.Session;
import back.Usuario;
import back.Actualizable;
import front.login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import main.DBConnection;

public class docente extends javax.swing.JFrame implements Actualizable {

    // Estos deben estar declarados en el diseñador con estos nombres exactos holiiiiiiiiiiiiii
    private final List<vistaPrevia> vistas = new ArrayList<>();

    public docente() {
        initComponents();
        cargarUsuario();
        cargarAsignaturasDesdeBD();
        // Activar Scroll en contenedorVistas
        JScrollPane scroll = new JScrollPane(contenedorVistas);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

// Reemplaza el añadido original
        menuTab.add(scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 440));

    }

    private void cargarUsuario() {
        Usuario u = Session.getUsuario();
        if (u != null) {
            userName.setText(u.getNombre() + " " + u.getApellido());
        } else {
            userName.setText("Usuario");
        }
    }

    private void agregarAsignatura(Asignatura nueva) {
        vistaPrevia vp = new vistaPrevia(nueva);
        vp.setOnAcceder(() -> {
            int idx = encontrarIndiceTabPorNombre(nueva.getNombre());
            if (idx >= 0) {
                tabbed.setSelectedIndex(idx);
            }
        });

        vistas.add(vp);
        contenedorVistas.add(vp);
        contenedorVistas.revalidate();
        contenedorVistas.repaint();

        JPanel panel = crearPanelAsignatura(nueva);
        tabbed.addTab(nueva.getNombre(), panel);
    }

    private JPanel crearPanelAsignatura(Asignatura a) {
        JPanel p = new JPanel(new BorderLayout());

        // --- 1. Panel Superior (Header) para contener Título y Botón ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Título de la Asignatura
        JLabel titulo = new JLabel("Asignatura: " + a.getNombre());
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));

        // Añadir el título al lado izquierdo del header
        headerPanel.add(titulo, BorderLayout.WEST);

        // --- 2. Botón "VOLVER AL MENU" con funcionalidad ---
        JLabel volverMenu = new JLabel("VOLVER AL MENU");
        volverMenu.setForeground(new Color(4, 174, 178)); // Color que usas
        volverMenu.setFont(new Font("Questrial", Font.BOLD, 12));

        // Añadir la funcionalidad de clic
        volverMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // ✅ ACCIÓN CLAVE: Selecciona la pestaña "Menú" (índice 0)
                tabbed.setSelectedIndex(0);
            }
        });

        // Contenedor para el botón de menú y alineación a la derecha
        JPanel menuBtnContainer = new JPanel(new BorderLayout());
        menuBtnContainer.setBackground(Color.WHITE);
        menuBtnContainer.add(volverMenu, BorderLayout.EAST);

        // Añadir el contenedor de menú al lado derecho del header
        headerPanel.add(menuBtnContainer, BorderLayout.EAST);

        // Añadir el header al panel principal (en la posición NORTH)
        p.add(headerPanel, BorderLayout.NORTH);

        // --- 3. Área de Descripción (Tus componentes existentes) ---
        JTextArea descripcion = new JTextArea(a.getDescripcion());
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // Añadir el área de descripción al panel principal (en la posición CENTER)
        p.add(new JScrollPane(descripcion), BorderLayout.CENTER);

        return p;
    }

    private int encontrarIndiceTabPorNombre(String nombre) {
        for (int i = 0; i < tabbed.getTabCount(); i++) {
            if (tabbed.getTitleAt(i).equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    private void cargarAsignaturasDesdeBD() {
        Usuario u = Session.getUsuario();
        String idDocente = u.getId();

        // 1. Obtener la lista de asignaturas COMPLETA (incluyendo duplicados de la DB)
        List<Asignatura> listaAsignaturasConDuplicados = DBConnection.obtenerAsignaturasDocente(idDocente);

        // ----------------------------------------------------
        // ✅ PASO CLAVE: Filtrar duplicados usando un HashSet
        // ----------------------------------------------------
        // Crear un conjunto (Set) para almacenar solo las asignaturas únicas.
        // Asumo que tu clase Asignatura tiene implementados correctamente los métodos 
        // hashCode() y equals() basados en el ID o Nombre de la asignatura.
        java.util.Set<Asignatura> asignaturasUnicas = new java.util.HashSet<>(listaAsignaturasConDuplicados);

        // Convertir de nuevo a una lista para el procesamiento secuencial
        List<Asignatura> listaAsignaturas = new java.util.ArrayList<>(asignaturasUnicas);
        // ----------------------------------------------------

        // 2. Limpieza Absoluta de Contenedores (Mantenemos esta parte)
        contenedorVistas.removeAll();
        vistas.clear();
        for (int i = tabbed.getTabCount() - 1; i > 0; i--) {
            tabbed.removeTabAt(i);
        }

        // 3. Recorrer la lista ÚNICA y agregar cada asignatura
        for (Asignatura asig : listaAsignaturas) { // Usamos la lista filtrada
            agregarAsignatura(asig);
        }

        // 4. Forzar la actualización visual
        contenedorVistas.revalidate();
        contenedorVistas.repaint();
        this.revalidate();
        this.repaint();

    }

    @Override
    public void actualizarNombreEnUI() {
        Usuario u = Session.getUsuario();
        if (u != null) {
            userName.setText(u.getNombre() + " " + u.getApellido());
        } else {
            userName.setText("Usuario");
        }
        this.revalidate();
        this.repaint();
        System.out.println("✅ Dashboard: Nombre de usuario recargado.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sectionName = new javax.swing.JPanel();
        actsTitle = new javax.swing.JLabel();
        menuBar = new javax.swing.JPanel();
        appName = new javax.swing.JLabel();
        dashBtn = new javax.swing.JPanel();
        dashTxt = new javax.swing.JLabel();
        dashIcon = new javax.swing.JLabel();
        coursesBtn = new javax.swing.JPanel();
        coursesTxt = new javax.swing.JLabel();
        coursesIcon = new javax.swing.JLabel();
        logoutBtn = new javax.swing.JPanel();
        logoutTxt = new javax.swing.JLabel();
        logoutIcon = new javax.swing.JLabel();
        docBtn = new javax.swing.JPanel();
        docTxt = new javax.swing.JLabel();
        docIcon = new javax.swing.JLabel();
        actsBtn = new javax.swing.JPanel();
        actsTxt = new javax.swing.JLabel();
        actsIcon = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        searchIcon = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        profilePic = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        configArrow = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        mainCont = new javax.swing.JPanel();
        tabbed = new javax.swing.JTabbedPane();
        menuTab = new javax.swing.JPanel();
        addAsigBtn = new javax.swing.JPanel();
        addAsigTxt = new javax.swing.JLabel();
        deleteAsigBtn = new javax.swing.JPanel();
        deleteAsigTxt = new javax.swing.JLabel();
        contenedorVistas = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sectionName.setBackground(new java.awt.Color(153, 153, 153));
        sectionName.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        actsTitle.setFont(new java.awt.Font("Questrial", 0, 35)); // NOI18N
        actsTitle.setForeground(new java.awt.Color(0, 0, 0));
        actsTitle.setText("Docente");
        sectionName.add(actsTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        getContentPane().add(sectionName, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 1010, 80));

        menuBar.setBackground(new java.awt.Color(255, 255, 255));
        menuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        appName.setFont(new java.awt.Font("Questrial", 0, 30)); // NOI18N
        appName.setForeground(new java.awt.Color(0, 0, 0));
        appName.setText("Skillbridge");
        menuBar.add(appName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        dashBtn.setBackground(new java.awt.Color(255, 255, 255));
        dashBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashBtnMouseClicked(evt);
            }
        });
        dashBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashTxt.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        dashTxt.setForeground(new java.awt.Color(0, 0, 0));
        dashTxt.setText("Dashboard");
        dashBtn.add(dashTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 13, 80, -1));

        dashIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/houseBicon.png"))); // NOI18N
        dashBtn.add(dashIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 40));

        menuBar.add(dashBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 260, 40));

        coursesBtn.setBackground(new java.awt.Color(255, 255, 255));
        coursesBtn.setForeground(new java.awt.Color(0, 0, 0));
        coursesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                coursesBtnMouseClicked(evt);
            }
        });
        coursesBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        coursesTxt.setBackground(new java.awt.Color(255, 255, 255));
        coursesTxt.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        coursesTxt.setForeground(new java.awt.Color(0, 0, 0));
        coursesTxt.setText("Cursos");
        coursesBtn.add(coursesTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 13, 50, -1));

        coursesIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hatBicon.png"))); // NOI18N
        coursesBtn.add(coursesIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 40));

        menuBar.add(coursesBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 260, 40));

        logoutBtn.setBackground(new java.awt.Color(255, 255, 255));
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
            }
        });
        logoutBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutTxt.setFont(new java.awt.Font("Open Sans", 0, 11)); // NOI18N
        logoutTxt.setForeground(new java.awt.Color(0, 0, 0));
        logoutTxt.setText("Log out");
        logoutBtn.add(logoutTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 13, -1, -1));

        logoutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logoutIcon.png"))); // NOI18N
        logoutBtn.add(logoutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 40));

        menuBar.add(logoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 260, 40));

        docBtn.setBackground(new java.awt.Color(255, 255, 255));
        docBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                docBtnMouseClicked(evt);
            }
        });
        docBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        docTxt.setFont(new java.awt.Font("Open Sans", 1, 12)); // NOI18N
        docTxt.setForeground(new java.awt.Color(4, 174, 178));
        docTxt.setText("Docente");
        docBtn.add(docTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 13, -1, -1));

        docIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caseGreen.png"))); // NOI18N
        docBtn.add(docIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 40));

        menuBar.add(docBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 260, 40));

        actsBtn.setBackground(new java.awt.Color(255, 255, 255));
        actsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actsBtnMouseClicked(evt);
            }
        });
        actsBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        actsTxt.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        actsTxt.setForeground(new java.awt.Color(0, 0, 0));
        actsTxt.setText("Actividades");
        actsBtn.add(actsTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 13, -1, -1));

        actsIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/appleBicon.png"))); // NOI18N
        actsBtn.add(actsIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 40, 40));

        menuBar.add(actsBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 260, 40));

        getContentPane().add(menuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 710));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchIcon.setBackground(new java.awt.Color(145, 145, 145));
        searchIcon.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        searchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/searchIcon.png"))); // NOI18N
        searchIcon.setBorder(null);
        searchIcon.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        searchIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchIconActionPerformed(evt);
            }
        });
        header.add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 50, 26));

        searchBar.setBackground(new java.awt.Color(145, 145, 145));
        searchBar.setFont(new java.awt.Font("Open Sans", 0, 11)); // NOI18N
        searchBar.setForeground(new java.awt.Color(229, 229, 229));
        searchBar.setText("    Search");
        searchBar.setBorder(null);
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        header.add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 240, 26));

        profilePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/men.png"))); // NOI18N
        header.add(profilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, -1, -1));

        userName.setFont(new java.awt.Font("Questrial", 0, 12)); // NOI18N
        userName.setForeground(new java.awt.Color(0, 0, 0));
        userName.setText("Martin Berrio");
        header.add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(883, 36, 70, -1));

        configArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrowProfile.png"))); // NOI18N
        header.add(configArrow, new org.netbeans.lib.awtextra.AbsoluteConstraints(946, 34, 40, 20));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        header.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, 150, 40));

        getContentPane().add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1010, 80));

        mainCont.setBackground(new java.awt.Color(102, 102, 102));
        mainCont.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuTab.setBackground(new java.awt.Color(255, 255, 255));
        menuTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addAsigBtn.setBackground(new java.awt.Color(64, 174, 178));

        addAsigTxt.setFont(new java.awt.Font("Questrial", 0, 18)); // NOI18N
        addAsigTxt.setForeground(new java.awt.Color(255, 255, 255));
        addAsigTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addAsigTxt.setText("Agregar");
        addAsigTxt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                addAsigTxtMouseMoved(evt);
            }
        });
        addAsigTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addAsigTxtMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addAsigTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout addAsigBtnLayout = new javax.swing.GroupLayout(addAsigBtn);
        addAsigBtn.setLayout(addAsigBtnLayout);
        addAsigBtnLayout.setHorizontalGroup(
            addAsigBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addAsigTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addAsigBtnLayout.setVerticalGroup(
            addAsigBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addAsigTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menuTab.add(addAsigBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 440, 140, 40));

        deleteAsigBtn.setBackground(new java.awt.Color(223, 91, 91));

        deleteAsigTxt.setFont(new java.awt.Font("Questrial", 0, 18)); // NOI18N
        deleteAsigTxt.setForeground(new java.awt.Color(255, 255, 255));
        deleteAsigTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteAsigTxt.setText("Eliminar");
        deleteAsigTxt.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                deleteAsigTxtMouseMoved(evt);
            }
        });
        deleteAsigTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteAsigTxtMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteAsigTxtMouseExited(evt);
            }
        });

        javax.swing.GroupLayout deleteAsigBtnLayout = new javax.swing.GroupLayout(deleteAsigBtn);
        deleteAsigBtn.setLayout(deleteAsigBtnLayout);
        deleteAsigBtnLayout.setHorizontalGroup(
            deleteAsigBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteAsigTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );
        deleteAsigBtnLayout.setVerticalGroup(
            deleteAsigBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteAsigTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        menuTab.add(deleteAsigBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 440, 140, 40));

        contenedorVistas.setBackground(new java.awt.Color(255, 255, 255));
        contenedorVistas.setLayout(new javax.swing.BoxLayout(contenedorVistas, javax.swing.BoxLayout.Y_AXIS));
        menuTab.add(contenedorVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 440));

        tabbed.addTab("tab1", menuTab);

        mainCont.add(tabbed, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 550));

        getContentPane().add(mainCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 1010, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBarActionPerformed

    private void searchIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchIconActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchIconActionPerformed

    private void coursesBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_coursesBtnMouseClicked
        //Cierra la ventana actual (login)
        this.dispose();
        //Abre la ventana nueva 
        cursosDashMon nuevaventana = new cursosDashMon();
        nuevaventana.setVisible(true);
    }//GEN-LAST:event_coursesBtnMouseClicked

    private void dashBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashBtnMouseClicked
        //Cierra la ventana actual (login)
        this.dispose();
        //Abre la ventana nueva 
        dashboardMon nuevaventana = new dashboardMon();
        nuevaventana.setVisible(true);    }//GEN-LAST:event_dashBtnMouseClicked

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
        //Cierra la ventana actual (login)
        this.dispose();
        //Abre la ventana nueva 
        login nuevaventana = new login();
        nuevaventana.setVisible(true);
    }//GEN-LAST:event_logoutBtnMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // Abrir el perfil pasando la referencia de esta ventana (dashboard)
        profileMon nuevaventana = new profileMon(this);
        nuevaventana.setVisible(true);

        // Ocultar dashboard (no cerrarlo) para poder volver cuando el perfil cierre
        this.setVisible(false);
    }//GEN-LAST:event_jPanel1MouseClicked

    private void docBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_docBtnMouseClicked
        //Cierra la ventana actual (login)
        this.dispose();
        //Abre la ventana nueva
        docente nuevaventana = new docente();
        nuevaventana.setVisible(true);
    }//GEN-LAST:event_docBtnMouseClicked

    private void actsBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actsBtnMouseClicked
        //Cierra la ventana actual (login)
        this.dispose();
        //Abre la ventana nueva 
        actDashMon nuevaventana = new actDashMon();
        nuevaventana.setVisible(true);
    }//GEN-LAST:event_actsBtnMouseClicked

    private void addAsigTxtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAsigTxtMouseMoved
        addAsigBtn.setBackground(new Color(38, 114, 116));
    }//GEN-LAST:event_addAsigTxtMouseMoved

    private void addAsigTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAsigTxtMouseExited
        addAsigBtn.setBackground(new Color(4, 174, 178));
    }//GEN-LAST:event_addAsigTxtMouseExited

    private void deleteAsigTxtMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAsigTxtMouseMoved

        deleteAsigBtn.setBackground(new Color(191, 40, 40));
    }//GEN-LAST:event_deleteAsigTxtMouseMoved

    private void deleteAsigTxtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAsigTxtMouseExited
        deleteAsigBtn.setBackground(new Color(223, 91, 91));
    }//GEN-LAST:event_deleteAsigTxtMouseExited

    private void addAsigTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAsigTxtMouseClicked

        // 1. Crear y mostrar la ventana de registro
        registrarAsig regAsig = new registrarAsig();
        regAsig.setLocationRelativeTo(null); // Centra la ventana
        regAsig.setVisible(true);

        // 2. Esperar a que la ventana de registro se cierre
        regAsig.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // 3. Una vez cerrada, actualiza la lista en la pantalla principal
                cargarAsignaturasDesdeBD();
                JOptionPane.showMessageDialog(null, "Lista de asignaturas actualizada.");
            }
        });


    }//GEN-LAST:event_addAsigTxtMouseClicked

    private void deleteAsigTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteAsigTxtMouseClicked
        // 1. Obtener las asignaturas seleccionadas
        java.util.List<vistaPrevia> seleccionadas = new java.util.ArrayList<>();
        for (vistaPrevia vp : vistas) {
            if (vp.isSeleccionada()) {
                seleccionadas.add(vp);
            }
        }

        if (seleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una asignatura para eliminar.");
            return;
        }

        // Obtener ID del docente logueado
        String idDocente = Session.getUsuario() != null ? Session.getUsuario().getId() : null;
        if (idDocente == null) {
            JOptionPane.showMessageDialog(this, "Error: Sesión de docente no encontrada.", "Error de Sesión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Recorrer y eliminar cada asignatura seleccionada
            for (vistaPrevia vp : seleccionadas) {
                Asignatura a = vp.getAsignatura();

                // ✅ CORRECCIÓN CLAVE: Llamar al método que borra SOLO de la tabla 'docente'
                DBConnection.eliminarAsignaturaDocente(idDocente, a.getNombre(), a.getDescripcion());

                // 2. Eliminar de la interfaz de usuario (Vista Previa)
                contenedorVistas.remove(vp);
                vistas.remove(vp);

                // 3. Eliminar la pestaña de detalle
                int idx = encontrarIndiceTabPorNombre(a.getNombre());
                if (idx >= 0) {
                    tabbed.removeTabAt(idx);
                }
            }

            // Forzar la actualización visual
            contenedorVistas.revalidate();
            contenedorVistas.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la asignación de asignatura: " + e.getMessage());
        }

        // Fin del método
    }//GEN-LAST:event_deleteAsigTxtMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(docente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(docente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(docente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(docente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new docente().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actsBtn;
    private javax.swing.JLabel actsIcon;
    private javax.swing.JLabel actsTitle;
    private javax.swing.JLabel actsTxt;
    private javax.swing.JPanel addAsigBtn;
    private javax.swing.JLabel addAsigTxt;
    private javax.swing.JLabel appName;
    private javax.swing.JLabel configArrow;
    private javax.swing.JPanel contenedorVistas;
    private javax.swing.JPanel coursesBtn;
    private javax.swing.JLabel coursesIcon;
    private javax.swing.JLabel coursesTxt;
    private javax.swing.JPanel dashBtn;
    private javax.swing.JLabel dashIcon;
    private javax.swing.JLabel dashTxt;
    private javax.swing.JPanel deleteAsigBtn;
    private javax.swing.JLabel deleteAsigTxt;
    private javax.swing.JPanel docBtn;
    private javax.swing.JLabel docIcon;
    private javax.swing.JLabel docTxt;
    private javax.swing.JPanel header;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel logoutBtn;
    private javax.swing.JLabel logoutIcon;
    private javax.swing.JLabel logoutTxt;
    private javax.swing.JPanel mainCont;
    private javax.swing.JPanel menuBar;
    private javax.swing.JPanel menuTab;
    private javax.swing.JLabel profilePic;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchIcon;
    private javax.swing.JPanel sectionName;
    private javax.swing.JTabbedPane tabbed;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
