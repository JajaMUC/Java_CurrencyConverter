import Interfaces.ICurrencyConversion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionFrame extends JFrame{

    private JTextField currencyAmount;
    private JComboBox<String> sourceCurrency;
    private JLabel resultLabel;
    private JSpinner dateSpinner;

    private final ICurrencyConversion iCurrencyConversion;
    public ConversionFrame(ICurrencyConversion iCurrencyConversion) {
        this.iCurrencyConversion = iCurrencyConversion;
        setTitle("Währungsrechner");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Panel A
        JPanel panelA = createPanelA();
        //Panel B
        JPanel panelB = createPanelB();
        //Panel C
        JPanel panelC = createPanelC();
        add(panelA, BorderLayout.NORTH);
        add(panelB, BorderLayout.CENTER);
        add(panelC, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private JPanel createPanelA() {
        final JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.cyan, Color.magenta), "Ausgangswert", TitledBorder.TRAILING, TitledBorder.CENTER));

        currencyAmount = new JTextField(15);

        sourceCurrency = new JComboBox<>();
        sourceCurrency.setModel(new DefaultComboBoxModel<>(iCurrencyConversion.getCurrency()));

        jPanel.add(currencyAmount);
        jPanel.add(sourceCurrency);

        return jPanel;

    }

    private JPanel createPanelB () {
        final JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.cyan, Color.magenta), "Zielwährung", TitledBorder.TRAILING, TitledBorder.BOTTOM));

        final JComboBox<String> targetCurrencyBox = new JComboBox<>();
        targetCurrencyBox.setModel(new DefaultComboBoxModel<>(iCurrencyConversion.getCurrency()));

        final JComboBox<String> converters = new JComboBox<>();
        converters.setModel(new DefaultComboBoxModel<>(iCurrencyConversion.getModus()));

        final JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        final JButton convertButton = new JButton("umrechnen");

        convertButton.addActionListener(e -> {
            iCurrencyConversion.setDate((Date)dateSpinner.getValue());
            final double result = iCurrencyConversion.performConversion(Integer.parseInt(currencyAmount.getText()), (String)sourceCurrency.getSelectedItem(), (String)targetCurrencyBox.getSelectedItem(),(String) converters.getSelectedItem());
            resultLabel.setText("Ergebnis " + result + " " + targetCurrencyBox.getSelectedItem());
        });

        jPanel.add(targetCurrencyBox);
        jPanel.add(converters);
        jPanel.add(dateSpinner);
        jPanel.add(convertButton);
        pack();
        return jPanel;
    }

    private JPanel createPanelC () {
        final JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.cyan, Color.magenta), "Ergebnis", TitledBorder.TRAILING, TitledBorder.BOTTOM));

        resultLabel = new JLabel("Ergebnis:");
        jPanel.add(resultLabel);

        return jPanel;
    }
}
