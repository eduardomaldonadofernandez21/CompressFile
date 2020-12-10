import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.zip.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.util.Locale.filter;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interfaz extends javax.swing.JFrame {
    
    private JFileChooser fc = new JFileChooser();
    private JOptionPane dialog = new JOptionPane();
    private DefaultListModel tablaFiles = new DefaultListModel();
    private FileNameExtensionFilter filter = null;
    private List<File> listAllFiles = new ArrayList<File>();
    private List<File> listClickFiles = new ArrayList<File>();
    private Tarea tarea;
    private int nFilesCompress;
    
    private class Tarea extends SwingWorker<Void, Integer>{
        private String path;
        
        private Tarea(String ruta){
            this.path = ruta;
        }
        
        @Override
        protected Void doInBackground() throws Exception {
            cancelarButton.setVisible(true);
            int nFilesTotal = listClickFiles.size();
            try{
                // Objeto para referenciar a los archivos que queremos comprimir
                BufferedInputStream origin = null;
                // Objeto para referenciar el archivo zip de salida
                FileOutputStream dest = new FileOutputStream(path);
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                int BUFFER_SIZE = 4096;
                // Buffer de transferencia para almacenar datos a comprimir
                byte[] data = new byte[BUFFER_SIZE];
                Iterator i = listClickFiles.iterator();
                while(i.hasNext()){
                    if(isCancelled()){
                        out.close();
                        new File(path).delete();
                        return null;
                    }
                    File file = (File)i.next();
                    FileInputStream fi = new FileInputStream(file.getPath());
                    origin = new BufferedInputStream(fi, BUFFER_SIZE);
                    ZipEntry entry = new ZipEntry( file.getName() );
                    out.putNextEntry( entry );
                    // Leemos datos desde el archivo origen y se envían al archivo destino
                    int count;
                    while((count = origin.read(data, 0, BUFFER_SIZE)) != -1){
                        out.write(data, 0, count);
                    }
                    nFilesCompress++;
                    progresoBarra.setValue((int) (((double)nFilesCompress/nFilesTotal)*100));
                    // Cerramos el archivo origen, ya enviado a comprimir
                    origin.close();
                    
                }
                // Cerramos el archivo zip
                progresoBarra.setValue(100);
                out.close();
            }catch( Exception e ){
                e.printStackTrace();
            } 
            listClickFiles.clear();
            return null;
        } 
        
        @Override
        public void done(){
            System.out.println("Tarea terminada");
            cancelarButton.setVisible(false);
        }
        
    }

    public Interfaz() {
        initComponents();
        panelProgreso.setVisible(false);
        progresoBarra.setStringPainted(true);
        initFiltros();
        listaFileZip.setModel(tablaFiles);
        guardarButton.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void initFiltros(){
        fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
        filter = new FileNameExtensionFilter("Carpeta","Folder");
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaFileZip = new javax.swing.JList<>();
        labelLista = new javax.swing.JLabel();
        guardarButton = new javax.swing.JButton();
        panelProgreso = new javax.swing.JPanel();
        labelProgreso = new javax.swing.JLabel();
        progresoBarra = new javax.swing.JProgressBar();
        cancelarButton = new javax.swing.JButton();
        labelFolder = new javax.swing.JLabel();
        crearZipButton = new javax.swing.JButton();
        labelFer = new javax.swing.JLabel();
        labelEdu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listaFileZip.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaFileZipValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaFileZip);

        labelLista.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelLista.setText("Archivos en la carpeta: ");

        guardarButton.setText("Comprimir archivos");
        guardarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarButtonActionPerformed(evt);
            }
        });

        labelProgreso.setText("% Progreso");

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProgresoLayout = new javax.swing.GroupLayout(panelProgreso);
        panelProgreso.setLayout(panelProgresoLayout);
        panelProgresoLayout.setHorizontalGroup(
            panelProgresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProgresoLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(panelProgresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProgresoLayout.createSequentialGroup()
                        .addComponent(labelProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProgresoLayout.createSequentialGroup()
                        .addComponent(progresoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProgresoLayout.createSequentialGroup()
                        .addComponent(cancelarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        panelProgresoLayout.setVerticalGroup(
            panelProgresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProgresoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelProgreso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelarButton))
        );

        labelFolder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelFolder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        crearZipButton.setText("Seleccionar carpeta");
        crearZipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearZipButtonActionPerformed(evt);
            }
        });

        labelFer.setText("© Fernando Marcelo Alonso");

        labelEdu.setText(" Eduardo Maldonado Fernández");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(300, Short.MAX_VALUE)
                        .addComponent(guardarButton)
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(panelProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(crearZipButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelFer, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(labelEdu, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelLista)
                        .addGap(63, 63, 63))))
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(labelFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLista)
                    .addComponent(labelEdu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(guardarButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(crearZipButton)))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     
    private void crearZipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearZipButtonActionPerformed
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            labelFolder.setText(fc.getName(fc.getSelectedFile()));
            addLista(fc.getSelectedFile());
        }
    }//GEN-LAST:event_crearZipButtonActionPerformed

    private void guardarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarButtonActionPerformed
        if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            String name = dialog.showInputDialog(null,"Introduzca el nombre del archivo comprimido *.zip","Nombre del archivo comprimido", JOptionPane.QUESTION_MESSAGE);
            if(name == null || name.isEmpty()){
                dialog.showMessageDialog(null,"No se ha introducido ningun nombre para el archivo a comprimir","Nombre en blanco", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int [] index = listaFileZip.getSelectedIndices();
            for(int i = 0; i<index.length; i++){
                listClickFiles.add(listAllFiles.get(index[i])); 
            }
            nFilesCompress = 0;
            generateTarea(fc.getSelectedFile().getPath()+"/"+name+".zip");
        }

    }//GEN-LAST:event_guardarButtonActionPerformed
    
    private void generateTarea(String path){
        progresoBarra.setValue(0);
        panelProgreso.setVisible(true);
        tarea = new Tarea(path);
        tarea.execute();
    }
    
    private void listaFileZipValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaFileZipValueChanged
        int [] index = listaFileZip.getSelectedIndices();
        guardarButton.setEnabled(index.length != 0);
    }//GEN-LAST:event_listaFileZipValueChanged

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        guardarButton.setEnabled(false);
        tarea.cancel(true);
        listaFileZip.clearSelection();
        dialog.showMessageDialog(null,"Se ha cancelado el archivo a comprimir","Zip cancelado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void addLista(File folder){ 
        listAllFiles.clear();
        tablaFiles.clear();
        for(File file : folder.listFiles()){
            if(!file.isDirectory()){
                listAllFiles.add(file);
                tablaFiles.addElement(file.getName());
            }
        }
        
    }
   
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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton crearZipButton;
    private javax.swing.JButton guardarButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEdu;
    private javax.swing.JLabel labelFer;
    private javax.swing.JLabel labelFolder;
    private javax.swing.JLabel labelLista;
    private javax.swing.JLabel labelProgreso;
    private javax.swing.JList<String> listaFileZip;
    private javax.swing.JPanel panelProgreso;
    private javax.swing.JProgressBar progresoBarra;
    // End of variables declaration//GEN-END:variables
}
