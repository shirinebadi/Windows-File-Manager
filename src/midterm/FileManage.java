package midterm;

import com.sun.deploy.xml.XMLNodeBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.*;
import java.util.regex.Pattern;

public class FileManage  implements DropTargetListener {
    int[] array;
    JPanel detailVeiw;
    protected File currentFile;
    DefaultTreeModel treeModel;
    JMenuBar menuBar;
    JMenu menu, submenu;
    private DropTarget dropTarget;
    private boolean cutOrCopy;
    int sX = -1, sY = -1;
    static Label stat;
    Image bImage;
    File[] delFile;
    boolean dragging = false;
    int curX = -1, curY = -1;
    TreeSelectionListener treeSelectionListener;
    JMenuItem menuItem;
    Desktop desktop;
    JFrame jFrame = new JFrame();
    JToolBar toolBar;
    JPanel gui;
    JTree tree;
    MouseHandler mouseHandler ;



    File[] fileO;
    //    KeyHandler keyHandler;
    JTextField findTextField;
    private JTextField findTextField1;
    JButton btBack;
    JButton btFrw;
    private JPopupMenu popupMenu;
    private JTable table;
    private JProgressBar progressBar;
    private JLabel fileName;
    private JTextField path;
    ArrayList<File> result ;
    private JLabel date;
    private JLabel size;
    private JCheckBox readable;
    private JCheckBox writable;
    private JCheckBox executable;
    JButton findLabel;
    private JRadioButton isDirectory;
    private JRadioButton isFile;
    private JTable theTable;
    private FileTableModel fileTableModel;
    private File[] files;
    private FileSystemView fileSystemView;
    private ListSelectionListener listSelectionListener;
    DefaultMutableTreeNode node;
    protected File finalCurrentFile;
    protected JTextField setTex1, setTex11,setTex111 ;
    private boolean cellSizesSet = false;
    private int rowIconPadding = 6;

    public FileManage() {
        ButtonHandler handler = new ButtonHandler();
//      KeyHandler keyHandler = new KeyHandler();
        path = new JTextField();
        date = new JLabel();
        size = new JLabel();
        setTex11 = new JTextField();
        setTex111=new JTextField();
        setTex1 = new JTextField();
        theTable = new JTable();
        desktop = Desktop.getDesktop();
        dropTarget = new DropTarget(table,this);
        JFileChooser chooser = new JFileChooser(fileSystemView);
        chooser.setMultiSelectionEnabled(true);
        File[] files2 = chooser.getSelectedFiles();
        mouseHandler = new MouseHandler();
        result = new ArrayList<>();
        isFile = new JRadioButton();
        isDirectory = new JRadioButton();
        fileSystemView = FileSystemView.getFileSystemView();
        gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        popupMenu = new JPopupMenu();

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int row = table.getSelectionModel().getLeadSelectionIndex();
                setFileDetails(((FileTableModel) table.getModel()).getFile(row));
            }
        };

        table.getSelectionModel().addListSelectionListener(listSelectionListener);
        String s = " ";
        table.setDragEnabled(true);
        table.setDropTarget(dropTarget);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setTransferHandler(new FileTransferHandler());
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.addMouseListener(mouseHandler);
        table.addMouseMotionListener(mouseHandler);
        table.addKeyListener(new KeyListener() {
                                 @Override
                                 public void keyTyped(KeyEvent e) {

                                 }

                                 @Override
                                 public void keyPressed(KeyEvent e) {

                                 }

                                 @Override
                                 public void keyReleased(KeyEvent e) {
                                     if (e.getKeyCode() == KeyEvent.VK_C && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                                         System.out.println("re");
                                         array = table.getSelectedRows();
                                         fileO = new File[array.length];
                                         for (int targetRow = 0; targetRow < array.length; targetRow++) {
                                             int row = array[targetRow];
                                             fileO[targetRow] = fileTableModel.getFile(row);
                                             System.out.println(fileO[targetRow].getName());
                                         }
                                         cutOrCopy = true;
                                     }
                                     if (e.getKeyCode() == KeyEvent.VK_X && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                                         array = table.getSelectedRows();
                                         fileO = new File[array.length];
                                         for (int targetRow = 0; targetRow < array.length; targetRow++) {
                                             int row = array[targetRow];
                                             fileO[targetRow] = fileTableModel.getFile(row);
                                             System.out.println(fileO[targetRow].getName() + " " + fileO.length);
                                         }
                                         cutOrCopy = false;
                                     }
                                     if ((e.getKeyCode() == KeyEvent.VK_V) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {


                                         TreePath parentPath = findTreePath(currentFile.getParentFile());
                                         DefaultMutableTreeNode parentNode =
                                                 (DefaultMutableTreeNode) parentPath.getLastPathComponent();
//                        System.out.println( ((File)node.getUserObject()).getParentFile().toPath());
//                    TreePath parentPath1 = findTreePath(currentFile.getParentFile());
                                         System.out.println("parentPath: " + parentPath);
//                    DefaultMutableTreeNode parentNode1 =
//                            (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                                         for (int i = 0; i < fileO.length; i++) {

                                             try {
                                                 copyFileUsingStream(fileO[i]);
                                             } catch (IOException ex) {
                                                 ex.printStackTrace();
                                             }

                                         }


                                         if (!cutOrCopy){
                                             for (int i = 0; i < fileO.length; i++) {
                                                 try {
//                            System.out.println("parentNode: " + parentNode);

                                                     boolean directory = fileO[i].isDirectory();
                                                     boolean deleted = fileO[i].delete();
                                                     if (deleted) {
                                                         if (directory) {
                                                             // delete the node..
                                                             System.out.println("direc");

                                                             TreePath currentPath = findTreePath(fileO[i]);
                                                             System.out.println(currentPath);
                                                             DefaultMutableTreeNode currentNode =
                                                                     (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                                                             treeModel.removeNodeFromParent(currentNode);
                                                         }

                                                     } else {
                                                         String msg = " ";

                                                     }
                                                 } catch (Throwable t) {
                                                     System.out.println("couldnt delete");
                                                 }
                                             }
                                         }
                                         showChildren(parentNode);

                                     }

                                     if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                                         array = table.getSelectedRows();
                                         delFile = new File[array.length];
                                         for (int targetRow = 0; targetRow < array.length; targetRow++) {
                                             int row = array[targetRow];
                                             delFile[targetRow] = fileTableModel.getFile(row);
                                         }
                                         System.out.println(delFile.length);
                                         deletFile();
                                         for (int i = 0; i < delFile.length; i++) {
                                             delFile[i].delete();
                                         }

                                     }

                                     if ((e.getKeyCode() == KeyEvent.VK_N) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
                                         String result = JOptionPane.showInputDialog("Enter the name");
                                         String appa = JOptionPane.showInputDialog("Enter suffix");
                                         File file = new File(currentFile.getParentFile() +"\\" + result+"."+appa);
                                         try {
                                             if(file.createNewFile())
                                                 showChildren(node);
                                         } catch (IOException ex) {
                                             ex.printStackTrace();
                                         }
                                     }
                                     if ((e.getKeyCode() == KeyEvent.VK_F) && (e.getModifiers() & KeyEvent.CTRL_MASK) !=0){
                                         String result = JOptionPane.showInputDialog("Enter the name");
                                         File file = new File(currentFile.getParentFile() +"\\" + result);
                                         file.mkdir();
                                         showChildren(node);
                                     }

                                     if (e.getKeyCode() == KeyEvent.VK_F2){
                                         if (currentFile.isFile()) {
                                             String result = JOptionPane.showInputDialog("Enter the sub name");
                                             String[] spl ={ };
                                             spl = currentFile.getName().split("\\.");
//                        System.out.println(spl.length);
                                             File file = new File(currentFile.getParentFile() + "\\" + result+"."+spl[spl.length-1]);
                                             currentFile.renameTo(file);
//                        TreePath t = findTreePath(currentFile);
//                        DefaultMutableTreeNode n = (DefaultMutableTreeNode) t.getLastPathComponent();

//                        treeModel.removeNodeFromParent(n);

                                             showChildren(node);


                                         }
                                         if (currentFile.isDirectory()){
                                             String result = JOptionPane.showInputDialog("Enter the sub name");
                                             File file = new File(currentFile.getParentFile() +"\\" + result);
                                             currentFile.renameTo(file);
                                             showChildren(node);
                                         }
                                     }
                                 }

                             }

        );
        JScrollPane tableScroll = new JScrollPane(table);

        gui.add(tableScroll, BorderLayout.CENTER);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(root);

        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent tse) {
                node =
                        (DefaultMutableTreeNode)  tse.getPath().getLastPathComponent();

                showChildren(node);
                setFileDetails((File) node.getUserObject());
                System.out.println( currentFile);
            }
        };

        // show the file system roots.
        File[] roots = fileSystemView.getRoots();
        System.out.println(roots.length);
        for (File fileSystemRoot : roots) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);

            File[] files = fileSystemView.getFiles(fileSystemRoot, true);
            for (File file : files) {
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                    root.add(node);
                }
            }
        }

        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.addTreeSelectionListener(treeSelectionListener);
//        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.expandRow(0);
        JScrollPane treeScroll = new JScrollPane(tree);


        Dimension preferredSize = treeScroll.getPreferredSize();
        Dimension widePreferred = new Dimension(
                200,
                (int) preferredSize.getHeight());
        treeScroll.setPreferredSize(widePreferred);

        gui.add(treeScroll, BorderLayout.WEST);

        menuBar = new JMenuBar();

        menu = new JMenu("File");
        menuBar.add(menu);
        // needs to complete

        menuItem = new JMenuItem("Create new File");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog("Enter the name");
                File file = new File(currentFile.getParentFile() +"\\" + result+".txt");
                try {
                    if(file.createNewFile())
                        showChildren(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Create new Folder");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String result = JOptionPane.showInputDialog("Enter the name");
                File file = new File(currentFile.getParentFile() +"\\" + result);
                file.mkdir();
                showChildren(node);
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                delFile = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    delFile[targetRow] = fileTableModel.getFile(row);
                }
                System.out.println(delFile.length);
                deletFile();
                for (int i = 0; i < delFile.length; i++) {
                    delFile[i].delete();
                }
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Delete");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                delFile = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    delFile[targetRow] = fileTableModel.getFile(row);
                }
                System.out.println(delFile.length);
                deletFile();
                for (int i = 0; i < delFile.length; i++) {
                    delFile[i].delete();
                }
            }
        });

        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Select / find the address");



        menu.add(menuItem);

        menu = new JMenu("Edit");

        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                fileO = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    fileO[targetRow] = fileTableModel.getFile(row);
                    System.out.println(fileO[targetRow].getName() + " " + fileO.length);
                }
                cutOrCopy = true;
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                fileO = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    fileO[targetRow] = fileTableModel.getFile(row);
                    System.out.println(fileO[targetRow].getName() + " " + fileO.length);
                }
                cutOrCopy = true;
            }
        });

        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                fileO = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    fileO[targetRow] = fileTableModel.getFile(row);
                    System.out.println(fileO[targetRow].getName() + " " + fileO.length);
                }
                cutOrCopy = false;
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Cut");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                array = table.getSelectedRows();
                fileO = new File[array.length];
                for (int targetRow = 0; targetRow < array.length; targetRow++) {
                    int row = array[targetRow];
                    fileO[targetRow] = fileTableModel.getFile(row);
                    System.out.println(fileO[targetRow].getName() + " " + fileO.length);
                }
                cutOrCopy = false;
            }
        });

        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath parentPath = findTreePath(currentFile.getParentFile());
                DefaultMutableTreeNode parentNode =
                        (DefaultMutableTreeNode) parentPath.getLastPathComponent();
//                        System.out.println( ((File)node.getUserObject()).getParentFile().toPath());
//                    TreePath parentPath1 = findTreePath(currentFile.getParentFile());
                System.out.println("parentPath: " + parentPath);
//                    DefaultMutableTreeNode parentNode1 =
//                            (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                for (int i = 0; i < fileO.length; i++) {

                    try {
                        copyFileUsingStream(fileO[i]);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }


                if (!cutOrCopy){
                    for (int i = 0; i < fileO.length; i++) {
                        try {
//                            System.out.println("parentNode: " + parentNode);

                            boolean directory = fileO[i].isDirectory();
                            boolean deleted = fileO[i].delete();
                            if (deleted) {
                                if (directory) {
                                    // delete the node..
                                    System.out.println("direc");

                                    TreePath currentPath = findTreePath(fileO[i]);
                                    System.out.println(currentPath);
                                    DefaultMutableTreeNode currentNode =
                                            (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                                    treeModel.removeNodeFromParent(currentNode);
                                }

                            } else {
                                String msg = " ";

                            }
                        } catch (Throwable t) {
                            System.out.println("couldnt delete");
                        }
                    }
                }
                showChildren(parentNode);

            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath parentPath = findTreePath(currentFile.getParentFile());
                DefaultMutableTreeNode parentNode =
                        (DefaultMutableTreeNode) parentPath.getLastPathComponent();
//                        System.out.println( ((File)node.getUserObject()).getParentFile().toPath());
//                    TreePath parentPath1 = findTreePath(currentFile.getParentFile());
                System.out.println("parentPath: " + parentPath);
//                    DefaultMutableTreeNode parentNode1 =
//                            (DefaultMutableTreeNode) parentPath.getLastPathComponent();

                for (int i = 0; i < fileO.length; i++) {

                    try {
                        copyFileUsingStream(fileO[i]);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }


                if (!cutOrCopy){
                    for (int i = 0; i < fileO.length; i++) {
                        try {
//                            System.out.println("parentNode: " + parentNode);

                            boolean directory = fileO[i].isDirectory();
                            boolean deleted = fileO[i].delete();
                            if (deleted) {
                                if (directory) {
                                    // delete the node..
                                    System.out.println("direc");

                                    TreePath currentPath = findTreePath(fileO[i]);
                                    System.out.println(currentPath);
                                    DefaultMutableTreeNode currentNode =
                                            (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                                    treeModel.removeNodeFromParent(currentNode);
                                }

                            } else {
                                String msg = " ";

                            }
                        } catch (Throwable t) {
                            System.out.println("couldnt delete");
                        }
                    }
                }
                showChildren(parentNode);

            }
        });


        popupMenu.add(menuItem);

        menuItem = new JMenuItem("properties");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                if (currentFile.isFile()) {
                    String[] spl = currentFile.getName().split("\\.");
                    JOptionPane.showMessageDialog(jFrame,"Type" + "       " + spl[spl.length - 1] + "\n" + "Location" + "        " + currentFile.getParent() + "\n" + "Size" + "        " + currentFile.getTotalSpace() + "Byte" + "\n"+ "contains" +"          " + 0 + "\n" + "Last Modified" + "           " + new Date(currentFile.lastModified()).toString());
                }
                else {
                    File[] files = currentFile.listFiles();
                    int counter =0;
                    for (int i = 0; i <files.length ; i++) {
                        if (files[i].isDirectory())
                            counter ++;
                    }
                    JOptionPane.showMessageDialog(jFrame, "Type" + "       " + "Folder File" + "\n" + "Location" + "        " + currentFile.getParent() + "\n" + "Size" + "        " + currentFile.getTotalSpace() + "Byte" + "\n" + "containes "+ "          "+ "File" +( files.length-counter) + "  Folder" + counter+ "\n" + "Last Modified" + "           " + new Date(currentFile.lastModified()).toString());
                }
            }
        });

        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Sync");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileManage fg = new FileManage();
                try {
                    System.out.println(currentFile.getName());
                    finalCurrentFile = currentFile;
                    fg.run();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem);

        menuBar.add(menu);


        menu = new JMenu("Help");

        menuItem = new JMenuItem("about me");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setContentPane(gui);
                JOptionPane.showMessageDialog(jFrame, "Hi!\n this is Shirin Ebadi \n From Aut", "Result", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Help");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setContentPane(gui);
                JOptionPane.showMessageDialog(jFrame, "This a test platform of a windows file manager\n use MenueBar to use feauters", "Result", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Settings");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panello = new JPanel(new GridLayout(8,2));
                JFrame newjFrame = new JFrame();
                panello.setBorder(new EmptyBorder(5,5,5,5));

                setTex1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showChildrenFilePath(setTex1.getText());
                        File file = new File(setTex1.getText());
                        setFileDetails(file);
                    }
                });
                setTex1.setBorder(new LineBorder(Color.black));
                panello.add(setTex1);

                JLabel jLabel = new JLabel("Address of files in this pc");
                panello.add(jLabel);
//                setTex11 = new JTextField();
                setTex11.setBorder(new LineBorder(Color.black));
                panello.add(setTex11);
                JLabel jLabel1 = new JLabel("Adress of target pc");
                panello.add(jLabel1);

//                setTex111 = new JTextField();
                setTex111.setBorder(new LineBorder(Color.black));
                panello.add(setTex111);
                JLabel jLabel2 = new JLabel("port of target pc");
                panello.add(jLabel2);

                JTextField setTex1111 = new JTextField();
                setTex1111.setBorder(new LineBorder(Color.black));
                panello.add(setTex1111);
                JLabel jLabel3 = new JLabel("Adress of synced files");
                panello.add(jLabel3);


                JComboBox<String> comboBox = new JComboBox();
                comboBox.addItem("Nothing");
                comboBox.addItem("Nimbus");
                comboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent en) {

                        try {
                            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (UnsupportedLookAndFeelException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });
                comboBox.addItem("System Look and feel");
                comboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent en) {
                        try {
                            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                        }
                         catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (UnsupportedLookAndFeelException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });

                panello.add(comboBox);

                JComboBox<Integer> jComboBox = new JComboBox<>();
                jComboBox.addItem(1);
                jComboBox.addItem(5);
                jComboBox.addItem(10);
                jComboBox.addItem(30);
                jComboBox.addItem(60);
                jComboBox.addItem(0);

                panello.add(jComboBox);

                JCheckBox jCheckBox = new JCheckBox("Grid");
                panello.add(jCheckBox);
                JCheckBox jCheckBox1 = new JCheckBox("List");


                panello.add(jCheckBox1);
                newjFrame.add(panello);
                newjFrame.setSize(300,300);
                newjFrame.setLocation(jFrame.getX() + 300, jFrame.getY()+ 300);
                newjFrame.setVisible(true);
            }
        });


        menu.add(menuItem);

        menuBar.add(menu);

        toolBar = new JToolBar();
        toolBar.setLayout(new BorderLayout(2, 1));


        toolBar.add(menuBar, BorderLayout.NORTH);
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(4, 3, 4, 3));

//      JButton findLabel = new JButton("Search:");
//      panel.add(findLabel);

//      panel.add(Box.createRigidArea(new Dimension(2, 0)));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        findLabel = new JButton("⬆");
        findLabel.addActionListener(handler);
        panel1.add(findLabel);
//      panel1.add(Box.createRigidArea(new Dimension(2, 0)));
        findTextField1 = new JTextField(25);
        findTextField1.addActionListener(handler);
//      panel1.add(Box.createRigidArea(new Dimension(4, 0)));
        panel1.add(findTextField1, BorderLayout.CENTER);
        panel1.setBorder(BorderFactory.createEmptyBorder(4, 3, 4, 3));
//      panel1.add(Box.createRigidArea(new Dimension(0, 0)));

        totalPanel.add(panel1);

        findTextField = new JTextField(7);
        findTextField.addActionListener(handler);
        panel.add(findTextField);


        totalPanel.add(panel, BorderLayout.EAST);

        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(new FlowLayout());
        btBack = new JButton("⬅");
        btBack.addActionListener(handler);
        btFrw = new JButton("➡");
        btFrw.addActionListener(handler);
        btnpanel.add(btBack);
        btnpanel.add(btFrw);
        totalPanel.add(btnpanel, BorderLayout.WEST);
        toolBar.add(totalPanel, BorderLayout.SOUTH);
        gui.add(toolBar, BorderLayout.NORTH);
    }



    public void dragEnter(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dragOver(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dropActionChanged(DropTargetDragEvent arg0) {
        // nothing
    }

    public void dragExit(DropTargetEvent arg0) {
        // nothing
    }

    private void run() throws IOException {
        Server.main(null);
//        Client.main(null);
    }
    public void drop(DropTargetDropEvent evt) {
        int action = evt.getDropAction();
        evt.acceptDrop(action);
        try {
            Transferable data = evt.getTransferable();
            DataFlavor flavors[] = data.getTransferDataFlavors();
            if (data.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> files = (List<File>) data.getTransferData(
                        DataFlavor.javaFileListFlavor);
                for (File file : files) {
                    System.out.println(file.getName());
                    copyFileUsingStream(file);
                    showChildren(node);
                }
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            evt.dropComplete(true);
        }
    }

    private void processFiles(List<File> files) throws IOException {
        // . . .
    }


    private class FileTransferHandler extends TransferHandler {

        @Override
        protected Transferable createTransferable(JComponent c) {

            List<File> files = new ArrayList<File>();
            for (Object obj: table.getSelectedRows()) {
                files.add((File)obj);
            }
            return new FileTransferable(files);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
    }

    private class FileTransferable implements Transferable {

        private List<File> files;

        public FileTransferable(List<File> files) {
            this.files = files;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.javaFileListFlavor);
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return files;
        }
    }

    private void deletFile() {
        int result = JOptionPane.showConfirmDialog(
                gui,
                "Are you sure you want to delete this file?",
                "Delete File",
                JOptionPane.ERROR_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {

            TreePath parentPath = findTreePath(delFile[0].getParentFile());
            System.out.println("parentPath: " + parentPath);
            DefaultMutableTreeNode parentNode =
                    (DefaultMutableTreeNode) parentPath.getLastPathComponent();

            for (int i = 0; i < delFile.length; i++) {
                try {
                    System.out.println("parentNode: " + parentNode);

                    boolean directory = delFile[i].isDirectory();
                    boolean deleted = delFile[i].delete();
                    if (deleted) {
                        if (directory) {
                            // delete the node..
                            System.out.println("direc");

                            TreePath currentPath = findTreePath(delFile[i]);
                            System.out.println(currentPath);
                            DefaultMutableTreeNode currentNode =
                                    (DefaultMutableTreeNode) currentPath.getLastPathComponent();

                            treeModel.removeNodeFromParent(currentNode);
                        }

                    } else {
                        String msg = "The file '" +
                                currentFile +
                                "' could not be deleted.";

                    }
                } catch (Throwable t) {
                    System.out.println("sth wrong");
                }
            }
            showChildren(parentNode);
            gui.repaint();


        }
    };


    private  void copyFileUsingStream(File file) throws IOException {
        InputStream is;
        File f2;
        System.out.println("hiiii");
        OutputStream os;
        byte[] buffer = new byte[1024];
        int length;


        try {
            if (file.isDirectory()) {
                f2 = new File(currentFile.getParentFile() + "\\" + file.getName());
                f2.mkdir();
            }
            f2 = new File(currentFile.getParentFile() + "\\" + file.getName());
            is = new FileInputStream(file);
            os = new FileOutputStream(f2);
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();

        } catch (IOException io) {
            System.out.println("went wrong!");
        }


    }


    //        private  class KeyHandler extends KeyAdapter{
//            public void keyReleased(KeyEvent e){
//               switch (e.getKeyCode()){
//                   case KeyEvent.VK_C:
//                       System.out.println(table.getSelectedRows());
//               }
//            }
//        }
    private class MouseHandler extends Component implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount()== 2){

                try {
                    desktop.open(currentFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent event) {
            Point point = event.getPoint();

            System.out.println("mousePressed at " + point);

            sX = point.x;

            sY = point.y;

            dragging = true;
        }
        void showPopup(MouseEvent me) {
            if(me.isPopupTrigger())
                popupMenu.show(me.getComponent(), me.getX(), me.getY());
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            showPopup(event);
            if(event.getButton() == MouseEvent.BUTTON3){

            }
            dragging = false;

            System.out.println("Drawn rectangle area IS " + sX + "," + sY + " to "

                    + curX + "," + curY);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent event) {

            Point p = event.getPoint();

            // System.err.println("mouse drag to " + p);

            curX = p.x;

            curY = p.y;

            if (!dragging) {

                repaint();

            }
        }

        public void paint(Graphics graphic) {

            int w = curX - sX, h = curY - sY;

            Dimension dims = getSize();

            graphic.drawImage(bImage, 0, 0, dims.width, dims.height, this);

            if (sX < 0 || sY < 0) {

                return;

            }

            System.out.println("Rect[" + sX + "," + sY

                    + "] size " + w + "x" + h);

            graphic.setColor(Color.blue);

            graphic.fillRect(sX, sY, w, h);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }


    }

    class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(findTextField1)) {
                String separator = "\\";
                String[] str_arr = findTextField1.getText().replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
                System.out.println(str_arr.length);
                File file1 = new File(str_arr[0]);
                DefaultMutableTreeNode root = new DefaultMutableTreeNode();
                DefaultMutableTreeNode node = root;
                for (int i = 1; i < str_arr.length; i++) {
                    file1 = new File(file1.getPath() + "\\" + str_arr[i]);
                    if (file1.isDirectory()) {
                        if (i != str_arr.length - 1) {
                            continue;
                        } else {
                            showChildrenFilePath(findTextField1.getText());
                            File file = new File(findTextField1.getText());
                            setFileDetails(file);
                        }
                    } else {
                        JFrame jFrame = new JFrame();
                        jFrame.setContentPane(gui);
                        JOptionPane.showMessageDialog(jFrame, "No similar path found!", "Result", JOptionPane.ERROR_MESSAGE);
                    }


                }
            }
            if (e.getSource().equals(findTextField)) {
                int c = 0;
                search(findTextField.getText(), currentFile);
                System.out.println(currentFile.getName());
                if (result != null) {
                    System.out.println(result.size());
                    for (int i = 0; i < result.size(); i++) {
                        showChildrenFilePath(result.get(i).getParentFile().getPath());
                        setFileDetails(result.get(i).getParentFile());
                    }
                } else {
                    JFrame jFrame = new JFrame();
                    jFrame.setContentPane(gui);
                    JOptionPane.showMessageDialog(jFrame, "No similar path found!", "Result", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource().equals(findLabel)){
                System.out.println("hi");
                TreePath currentPath = findTreePath(currentFile.getParentFile());
                System.out.println(currentPath);
                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)currentPath.getLastPathComponent();
                showChildren(node);
                setFileDetails((File) node.getUserObject());


            }

            if (e.getSource().equals(btBack)){
                System.out.println("hi");
                TreePath currentPath = findTreePath(currentFile.getParentFile());
                System.out.println(currentPath);
                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)currentPath.getLastPathComponent();
                showChildren(node);
                setFileDetails((File) node.getUserObject());


            }
            if(e.getSource().equals(menuItem.getText().equals("about me"))){
                JFrame jFrame = new JFrame();
                jFrame.setContentPane(gui);
                JOptionPane.showMessageDialog(jFrame, "Hi!\n this is Shirin Ebadi \n From Aut", "Result", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public  void search(final String pattern, final File folder) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f);
            }

            if (f.isFile()) {
                if (f.getName().contains(pattern)) {
                    result.add(f);
                }
            }

        }
    }

    private void showChildrenFilePath(String filePath1) {
        progressBar = new JProgressBar();
        tree.setEnabled(false);
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {

            public Void doInBackground() {
                File file = new File(filePath1);
                if (file.isDirectory()) {
                    File[] files1 = fileSystemView.getFiles(file, true); //!!
//                    if (filePath1.isLeaf()) {
//                        for (File child : files1) {
//                            if (child.isDirectory()) {
//                                publish(child);
//                            }
//                        }
//                    }
                    setTableData(files1);
                    files = files1;
                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
//                for (File child : chunks) {
//                    node.add(new DefaultMutableTreeNode(child));
//                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void showChildren(final DefaultMutableTreeNode node) {
        progressBar = new JProgressBar();
        tree.setEnabled(false);
//            progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        SwingWorker<Void, File> swingWorker = new SwingWorker<Void, File>() {

            public Void doInBackground() {
                File file = (File) node.getUserObject();
                if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    if (node.isLeaf()) {
                        for (File child : files) {
                            if (child.isDirectory()) {
                                publish(child);
                            }
                        }
                    }
                    setTableData(files);

                }
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                tree.setEnabled(true);
            }
        };
        swingWorker.execute();
    }

    private void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {


            public void run() {
                if (fileTableModel == null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                if (!cellSizesSet) {
                    Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight(icon.getIconHeight() + rowIconPadding);

//                        setColumnWidth(0, -1);
//                        setColumnWidth(3, 60);
                    table.getColumnModel().getColumn(3).setMaxWidth(120);
//                        setColumnWidth(4, -1);
//                        setColumnWidth(5, -1);
//                        setColumnWidth(6, -1);
//                        setColumnWidth(7, -1);
//                        setColumnWidth(8, -1);
//                        setColumnWidth(9, -1);

                    cellSizesSet = true;
                }
            }
        });
    }

    private void setFileDetails(File file) {
        fileName = new JLabel();
        currentFile = file;
//            fileName.setIcon(fileSystemView.getSystemIcon(file));
//            fileName.setToolTipText(file.getName() + "\n " + file.getTotalSpace() + "\n" + file.getFreeSpace());
//            fileName.setText(fileSystemView.getSystemDisplayName(file));
        path.setText(file.getPath());
        date.setText(new Date(file.lastModified()).toString());
        size.setText(file.length() + " bytes");
//        readable.setSelected(file.canRead());
//        writable.setSelected(file.canWrite());
//        executable.setSelected(file.canExecute());
//        isDirectory.setSelected(file.isDirectory());

        isFile.setSelected(file.isFile());

        JFrame jFrame = (JFrame) gui.getTopLevelAncestor();
        if (findTextField1 != null && setTex1 !=null && setTex11 != null && setTex111 != null) {
            findTextField1.setText(
                    currentFile.getAbsolutePath());
            setTex1.setText(
                    setTex1.getToolTipText()
            );
            setTex11.setText(
                    setTex11.getToolTipText()
            );
            setTex111.setText(
                    setTex111.getToolTipText()
            );


        }
//        if (setTex1.getText().equals("") && setTex11.getText().equals("")&& setTex111. getText().equals("")){
//
//            setTex1.setText("Address of main files");
//            setTex111.setText("Port of target comp.");
//            setTex11.setText("adress of target comp");
//        }

        gui.repaint();
    }


    private TreePath findTreePath(File find) {
        for (int ii = 0; ii < tree.getRowCount(); ii++) {
            TreePath treePath = tree.getPathForRow(ii);
            Object object = treePath.getLastPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
            File nodeFile = (File) node.getUserObject();

            if (nodeFile == find) {
                return treePath;
            }
        }
        return null;
    }

}

class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    private FileSystemView fileSystemView;

    private JLabel label;

    FileTreeCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }


    public Component getTreeCellRendererComponent(
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        File file = (File) node.getUserObject();
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath() + "\n " + file.getName() + " \n " + file.getTotalSpace());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}

class FileTableModel extends AbstractTableModel {

    private File[] files;
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private String[] columns = {
            "",
            "name",
            "Size",
            "Last Modified",
    };

    FileTableModel() {
        this(new File[0]);
    }

    FileTableModel(File[] files) {
        this.files = files;
    }

    public Object getValueAt(int row, int column) {
        File file = files[row];
        JLabel label = new JLabel();
        switch (column) {
            case 0:
                return fileSystemView.getSystemIcon(file) ;
            case 1:
                return fileSystemView.getSystemDisplayName(file);
            case 2:
                return file.length();
            case 3:
                return file.lastModified();


            default:
                System.err.println("Logic Error");
        }

        ;

        return "";
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return ImageIcon.class;
            case 2:
                return Long.class;
            case 3:
                return Date.class;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return Boolean.class;
        }
        return String.class;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return files.length;
    }

    public File getFile(int row) {
        return files[row];
    }

    public void setFiles(File[] files) {
        File[] files2 = files;
        this.files = files;
        fireTableDataChanged();
    }
}


