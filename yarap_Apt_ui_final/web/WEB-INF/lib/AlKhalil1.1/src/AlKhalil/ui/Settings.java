/*
 *
 */
/* ALKHALIL MORPHO SYS -- An open source programm.
 *
 * Copyright (C) 2010.
 *
 * This program is free software, distributed under the terms of
 * the GNU General Public License Version 3. For more informations see web site at :
 * http://www.gnu.org/licenses/gpl.txt
 */
package AlKhalil.ui;

import AlKhalil.result.*;
import java.util.*;
import java.util.prefs.Preferences;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * This is the JFrame used to adapt analysis outputs to the user choices.
 */
public class Settings extends javax.swing.JFrame {

    // ------------------------------------------------------  Class Variables
    /** used to choose which database the user wants (the extended or the small one) */
    public static boolean dbchoice;
    /** true if the user chooses to display the vowelization of the word */
    public static boolean vowchoice;
    /** true if the user chooses to display the prefix of the word */
    public static boolean prefchoice;
    /** true if the user chooses to display the stem of the word */
    public static boolean stemchoice;
    /** true if the user chooses to display the type of the word */
    public static boolean typechoice;
    /** true if the user chooses to display the pattern of the word */
    public static boolean patternchoice;
    /** true if the user chooses to display the root of the word */
    public static boolean rootchoice;
    /** true if the user chooses to display the part of speech of the word */
    public static boolean poschoice;
    /** true if the user chooses to display the suffix of the word */
    public static boolean suffixchoice;
    /** indicates how many cloumns are used to display the results*/
    public static int ncoloumns;

    // --------------------------------------------------------- Constuctor
    /** Creates new form Settings */
    public Settings() {
        super("���� ������");
        initComponents();
        setResizable(false);
        setLayout(null);

        setSize(jPanel1.getSize().width + jPanel1.getSize().width + 100, 330);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Gui.setting.setEnabled(true);
            }
        });
        readSettings();
        if (dbchoice) {

            jRadioButton1.setSelected(true);
        } else {
            jRadioButton2.setSelected(true);
        }





    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBoxStem = new javax.swing.JCheckBox();
        jCheckBoxPrefix = new javax.swing.JCheckBox();
        jCheckBoxPattern = new javax.swing.JCheckBox();
        jCheckBoxSuffix = new javax.swing.JCheckBox();
        jCheckBoxType = new javax.swing.JCheckBox();
        jCheckBoxRoot = new javax.swing.JCheckBox();
        jCheckBoxVow = new javax.swing.JCheckBox();
        jCheckBoxPos = new javax.swing.JCheckBox();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToggleButton1.setFont(new java.awt.Font("Arabic Typesetting", 0, 24)); // NOI18N
        jToggleButton1.setText("�����");
        jToggleButton1.setMaximumSize(new java.awt.Dimension(200, 41));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jToggleButton2.setText("�����");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "����� ��������", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Simplified Arabic", 1, 18))); // NOI18N

        jRadioButton1.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jRadioButton1.setText("����� �������� ��������");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jRadioButton2.setText("����� �������� �������");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "��������", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Simplified Arabic", 1, 18))); // NOI18N

        jCheckBoxStem.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxStem.setSelected(true);
        jCheckBoxStem.setText("�����");
        jCheckBoxStem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxStemActionPerformed(evt);
            }
        });

        jCheckBoxPrefix.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxPrefix.setSelected(true);
        jCheckBoxPrefix.setText("������");
        jCheckBoxPrefix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPrefixActionPerformed(evt);
            }
        });

        jCheckBoxPattern.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxPattern.setSelected(true);
        jCheckBoxPattern.setText("�����");
        jCheckBoxPattern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPatternActionPerformed(evt);
            }
        });

        jCheckBoxSuffix.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxSuffix.setSelected(true);
        jCheckBoxSuffix.setText("������");
        jCheckBoxSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSuffixActionPerformed(evt);
            }
        });

        jCheckBoxType.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxType.setSelected(true);
        jCheckBoxType.setText("��� ������");
        jCheckBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTypeActionPerformed(evt);
            }
        });

        jCheckBoxRoot.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxRoot.setSelected(true);
        jCheckBoxRoot.setText("�����");
        jCheckBoxRoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRootActionPerformed(evt);
            }
        });

        jCheckBoxVow.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxVow.setSelected(true);
        jCheckBoxVow.setText("����� ������");
        jCheckBoxVow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxVowActionPerformed(evt);
            }
        });

        jCheckBoxPos.setFont(new java.awt.Font("Arabic Typesetting", 0, 24));
        jCheckBoxPos.setSelected(true);
        jCheckBoxPos.setText("������ ���������");
        jCheckBoxPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBoxPos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jCheckBoxVow)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxSuffix)
                            .addComponent(jCheckBoxRoot)
                            .addComponent(jCheckBoxPrefix))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxType)
                            .addComponent(jCheckBoxPattern)
                            .addComponent(jCheckBoxStem))
                        .addGap(28, 28, 28))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxStem)
                    .addComponent(jCheckBoxPrefix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxPattern)
                    .addComponent(jCheckBoxSuffix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxType)
                    .addComponent(jCheckBoxRoot))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxVow)
                    .addComponent(jCheckBoxPos)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        Gui.setting.setEnabled(true);
        dispose();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jCheckBoxVowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxVowActionPerformed
        // TODO add your handling code here:
        // jCheckBoxAllResults.setSelected(! jCheckBoxSomeResults.isSelected());
        vowchoice = jCheckBoxVow.isSelected();
    }//GEN-LAST:event_jCheckBoxVowActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        //dbchoice =  jRadioButton1.isSelected();

        Gui.analyzer.NSolutions = new java.util.HashMap();
        Gui.analyzer.NPatterns = new java.util.HashMap();
        Gui.analyzer.VPatterns = new java.util.HashMap();
        upDateSettings();
        upDateResults();
        Gui.setting.setEnabled(true);
        dispose();

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jCheckBoxPrefixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPrefixActionPerformed
        // TODO add your handling code here:
        prefchoice = jCheckBoxPrefix.isSelected();
    }//GEN-LAST:event_jCheckBoxPrefixActionPerformed

    private void jCheckBoxStemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxStemActionPerformed
        // TODO add your handling code here:
        stemchoice = jCheckBoxStem.isSelected();
    }//GEN-LAST:event_jCheckBoxStemActionPerformed

    private void jCheckBoxSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSuffixActionPerformed
        // TODO add your handling code here:
        suffixchoice = jCheckBoxSuffix.isSelected();
    }//GEN-LAST:event_jCheckBoxSuffixActionPerformed

    private void jCheckBoxPatternActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPatternActionPerformed
        // TODO add your handling code here:
        patternchoice = jCheckBoxPattern.isSelected();
    }//GEN-LAST:event_jCheckBoxPatternActionPerformed

    private void jCheckBoxRootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRootActionPerformed
        // TODO add your handling code here:
        rootchoice = jCheckBoxRoot.isSelected();
    }//GEN-LAST:event_jCheckBoxRootActionPerformed

    private void jCheckBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTypeActionPerformed
        // TODO add your handling code here:
        typechoice = jCheckBoxType.isSelected();

    }//GEN-LAST:event_jCheckBoxTypeActionPerformed

    private void jCheckBoxPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPosActionPerformed
        // TODO add your handling code here:
        poschoice = jCheckBoxPos.isSelected();

    }//GEN-LAST:event_jCheckBoxPosActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        jRadioButton2.setSelected(!jRadioButton1.isSelected());
        dbchoice = jRadioButton1.isSelected();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jRadioButton1.setSelected(!jRadioButton2.isSelected());
        dbchoice = jRadioButton1.isSelected();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //Gui.setting.setEnabled(true);
        Gui.setting.setEnabled(true);
        dispose();
//        jToggleButton2ActionPerformed(ev);
    }//GEN-LAST:event_formWindowClosing

    private void readSettings() {

        File settings = new File("db/settings");

        String line;
        StringBuffer buf = new StringBuffer();
        ncoloumns = 0;
        try {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    new FileInputStream(settings)));


            while ((line = in.readLine()) != null) {

                line = line.trim();
                String t[] = line.split("=");

                //int index=Integer.parseInt(t[1]);
                if (t[0].equals("dbchoice")) {
                    if (t[1].equals("false")) {
                        dbchoice = false;
                        jRadioButton2.setSelected(true);
                    } else {
                        dbchoice = true;
                        jRadioButton1.setSelected(true);
                    }
                }

                if (t[0].equals("vowchoice")) {
                    if (t[1].equals("false")) {
                        vowchoice = false;
                        jCheckBoxVow.setSelected(false);
                    } else {
                        vowchoice = true;
                        jCheckBoxVow.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("prefchoice")) {
                    if (t[1].equals("false")) {
                        prefchoice = false;
                        jCheckBoxPrefix.setSelected(false);
                    } else {
                        prefchoice = true;
                        jCheckBoxPrefix.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("stemchoice")) {
                    if (t[1].equals("false")) {
                        stemchoice = false;
                        jCheckBoxStem.setSelected(false);

                    } else {
                        stemchoice = true;
                        jCheckBoxStem.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("typechoice")) {
                    if (t[1].equals("false")) {
                        typechoice = false;
                        jCheckBoxType.setSelected(false);
                    } else {
                        typechoice = true;
                        jCheckBoxType.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("patternchoice")) {
                    if (t[1].equals("false")) {
                        patternchoice = false;
                        jCheckBoxPattern.setSelected(false);
                    } else {
                        patternchoice = true;
                        jCheckBoxPattern.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("rootchoice")) {
                    if (t[1].equals("false")) {
                        rootchoice = false;
                        jCheckBoxRoot.setSelected(false);
                    } else {
                        rootchoice = true;
                        jCheckBoxRoot.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("poschoice")) {
                    if (t[1].equals("false")) {
                        poschoice = false;
                        jCheckBoxPos.setSelected(false);
                    } else {
                        poschoice = true;
                        jCheckBoxPos.setSelected(true);
                        ncoloumns++;
                    }
                }

                if (t[0].equals("suffixchoice")) {
                    if (t[1].equals("false")) {
                        suffixchoice = false;
                        jCheckBoxSuffix.setSelected(false);

                    } else {
                        suffixchoice = true;
                        jCheckBoxSuffix.setSelected(true);
                        ncoloumns++;
                    }
                }



            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void upDateResults() {


        for (int i = 0; i < Gui.analyzer.allResults.size(); i++) {

            HashMap sol = (HashMap) Gui.analyzer.allResults.get(i);

            HashMap solbis = new HashMap();



            Collection normalizedWords = sol.keySet();
            Collection normalizedWordsbis;
            Iterator itn = normalizedWords.iterator();

            while (itn.hasNext()) {
                String normalizedWord = (String) itn.next();
                java.util.List result = (java.util.List) sol.get(normalizedWord);
                java.util.List resultbis = new LinkedList();
                HashMap Lines = new HashMap();

                if (result.isEmpty()) {


                    solbis.put(normalizedWord, result);


                } else {

                    for (int n = 0; n < result.size(); n++) {
                        Result resulta = (Result) result.get(n);
                        String line = "";

                        if (Settings.suffixchoice) {
                            line = line + " " + resulta.getSuffix();

                        }
                        if (Settings.poschoice) {
                            line = line + " " + resulta.getPos();

                        }
                        if (Settings.rootchoice) {
                            line = line + " " + resulta.getWordroot();

                        }
                        if (Settings.patternchoice) {
                            line = line + " " + resulta.getWordpattern();

                        }
                        if (Settings.typechoice) {
                            line = line + " " + resulta.getWordtype();

                        }
                        if (Settings.stemchoice) {
                            line = line + " " + resulta.getStem();

                        }
                        if (Settings.prefchoice) {
                            line = line + " " + resulta.getPrefix();

                        }
                        if (Settings.vowchoice) {
                            line = line + " " + resulta.getVoweledword();

                        }

                        if (!Lines.containsKey(line)) {
                            resultbis.add(resulta);
                            Lines.put(line, "");
                        }

                    }




                    solbis.put(normalizedWord, resultbis);
                }



            }

            Gui.analyzer.allResultsBis.put(i, solbis);

        }

        //System.out.println("results updated!!!");

    }

    public void upDateSettings() {

        File settings = new File("db/settings");

        ncoloumns = 0;
        try {

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(settings)));

            if (dbchoice) {
                out.write("dbchoice=true\n");
            } else {
                out.write("dbchoice=false\n");

            }
            if (vowchoice) {
                out.write("vowchoice=true\n");
                ncoloumns++;
            } else {
                out.write("vowchoice=false\n");

            }
            if (prefchoice) {
                out.write("prefchoice=true\n");
                ncoloumns++;
            } else {
                out.write("prefchoice=false\n");

            }
            if (stemchoice) {
                out.write("stemchoice=true\n");
                ncoloumns++;
            } else {
                out.write("stemchoice=false\n");

            }
            if (typechoice) {
                out.write("typechoice=true\n");
                ncoloumns++;
            } else {
                out.write("typechoice=false\n");

            }
            if (patternchoice) {
                out.write("patternchoice=true\n");
                ncoloumns++;
            } else {
                out.write("patternchoice=false\n");

            }
            if (rootchoice) {
                out.write("rootchoice=true\n");
                ncoloumns++;
            } else {
                out.write("rootchoice=false\n");

            }
            if (poschoice) {
                out.write("poschoice=true\n");
                ncoloumns++;
            } else {
                out.write("poschoice=false\n");

            }
            if (suffixchoice) {
                out.write("suffixchoice=true");
                ncoloumns++;
            } else {
                out.write("suffixchoice=false");

            }



            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxPattern;
    private javax.swing.JCheckBox jCheckBoxPos;
    private javax.swing.JCheckBox jCheckBoxPrefix;
    private javax.swing.JCheckBox jCheckBoxRoot;
    private javax.swing.JCheckBox jCheckBoxStem;
    private javax.swing.JCheckBox jCheckBoxSuffix;
    private javax.swing.JCheckBox jCheckBoxType;
    private javax.swing.JCheckBox jCheckBoxVow;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables

    class WindowCloser extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            Window win = e.getWindow();
            win.setVisible(false);
            Gui.setting.setEnabled(true);
            dispose();
        }
    }
}
