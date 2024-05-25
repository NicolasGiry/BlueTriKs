package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import logger.ExpeLogger;

public class Main extends JFrame {
	private static final int LONGUEUR = 450;
	private static final int HAUTEUR = 200;
	private static final int SPINNERWIDTH = 50;
	private static final int SPINNERHEIGHT = 40;

    JLabel modeLabel, clavLabel, groupLabel, partLabel;
    JRadioButton bTrain, bExp, bClavierPred, bClavierNoPred, bG1, bG2;
    JButton bvalider;
    ButtonGroup groupExpTrain, groupClavier, groupGroup;
    JSpinner partSpinner;

    public Main() {
        super("Initialisation BlueTriKs");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(LONGUEUR, HAUTEUR));

        modeLabel = new JLabel("Choisir un mode : ");
        clavLabel = new JLabel("Choisir un clavier : ");
        groupLabel = new JLabel("Indiquez votre groupe : ");
        partLabel = new JLabel("Indiquez votre numéro de participant : ");
        bTrain = new JRadioButton("entrainement");
        bExp = new JRadioButton("experience");
        bClavierPred = new JRadioButton("avec prédiction");
        bClavierNoPred = new JRadioButton("sans prédiction");
        bG1 = new JRadioButton("groupe 1");
        bG2 = new JRadioButton("groupe 2");
        groupExpTrain = new ButtonGroup();
        groupClavier = new ButtonGroup();
        groupGroup = new ButtonGroup();
        bvalider = new JButton("Valider");
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 12, 1);
        partSpinner = new JSpinner(model);

        JPanel radioModePanel = new JPanel(new BorderLayout());
        JPanel radioClavPanel = new JPanel(new BorderLayout());
        JPanel radioGroupPanel = new JPanel(new BorderLayout());
        JPanel modelPanel = new JPanel(new BorderLayout());
        JPanel clavierPanel = new JPanel(new BorderLayout());
        JPanel groupPanel = new JPanel(new BorderLayout());
        JPanel partPanel = new JPanel(new BorderLayout());
        JPanel intermediatePanel = new JPanel(new BorderLayout());
        JPanel finalPanel = new JPanel(new BorderLayout());
        
        partPanel.setPreferredSize(new Dimension(SPINNERWIDTH, SPINNERHEIGHT));
        radioModePanel.add(bTrain, BorderLayout.WEST);
        radioModePanel.add(bExp, BorderLayout.CENTER);
        radioClavPanel.add(bClavierPred, BorderLayout.WEST);
        radioClavPanel.add(bClavierNoPred, BorderLayout.CENTER);
        radioGroupPanel.add(bG1, BorderLayout.WEST);
        radioGroupPanel.add(bG2, BorderLayout.CENTER);

        modelPanel.add(modeLabel, BorderLayout.WEST);
        modelPanel.add(radioModePanel, BorderLayout.CENTER);
        clavierPanel.add(clavLabel, BorderLayout.WEST);
        clavierPanel.add(radioClavPanel, BorderLayout.CENTER);
        groupPanel.add(groupLabel, BorderLayout.WEST);
        groupPanel.add(radioGroupPanel, BorderLayout.CENTER);

        partPanel.add(partLabel, BorderLayout.WEST);
        partPanel.add(partSpinner, BorderLayout.CENTER);

        intermediatePanel.add(modelPanel, BorderLayout.NORTH);
        intermediatePanel.add(clavierPanel, BorderLayout.CENTER);
        intermediatePanel.add(groupPanel, BorderLayout.SOUTH);
        
        finalPanel.add(intermediatePanel, BorderLayout.NORTH);
        finalPanel.add(partPanel, BorderLayout.CENTER);
        finalPanel.add(bvalider, BorderLayout.SOUTH);
        
        groupExpTrain.add(bTrain);
        groupExpTrain.add(bExp);
        groupClavier.add(bClavierPred);
        groupClavier.add(bClavierNoPred);
        groupGroup.add(bG1);
        groupGroup.add(bG2);

        this.setLayout(new BorderLayout());
        this.add(finalPanel, BorderLayout.CENTER);
        //this.add(bvalider, BorderLayout.SOUTH);
        pack();
		setLocationRelativeTo(null);
		setVisible(true);

        bvalider.addActionListener(new ActionListener() {      
            @Override
            public void actionPerformed(ActionEvent e) 
            { 
                Mode mode = Mode.TRAIN;
                ResultsWordPrediction wp = ResultsWordPrediction.PRED;
                Ordre ordre;
                int nbPart = (int) partSpinner.getValue();
                if (bTrain.isSelected()) { 
                    mode = Mode.TRAIN; 
                } else if (bExp.isSelected()) { 
                    mode = Mode.EXP; 
                } else {
                    return;
                }
                if (bClavierPred.isSelected()) { 
                    wp = ResultsWordPrediction.PRED; 
                } else if (bClavierNoPred.isSelected()) { 
                    wp = ResultsWordPrediction.NO_PRED; 
                } else {
                    return;
                }
                
                if (bG1.isSelected()) {
                	ordre = Ordre.PRED_NOPRED;
                } else if (bG2.isSelected()) {
                	ordre = Ordre.NOPRED_PRED;
                } else {
                	return;
                }

                start(mode, wp, nbPart, ordre);
            } 
        }); 
    }

    private void start(Mode mode, ResultsWordPrediction wp, int nbPart, Ordre ordre) {
        ExpeLogger.debutSimulation(wp, nbPart, mode, ordre);
        new Clavier2Frame(mode, wp, nbPart);
        this.dispose();
    }

    public static void main(String[] args) {
        new Main();
    }
}