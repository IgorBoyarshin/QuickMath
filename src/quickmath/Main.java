package quickmath;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by Igor on 08-Aug-15.
 */
public class Main extends JFrame {

    private final int width = 300;
    private final int height = 360;
    private final int textInputLength = 50;
    private final char belongs = '=';
    private Font mainFont = new Font(null, Font.BOLD, 12);
    private Color backgroundColor = new Color(0.45882353f, 0.34117648f, 0.9019608f);
    private Color textInputBackgroundColor = new Color(0.6392157f, 0.654902f, 1.0f);

    private JTextField addTextField;
    private JTextField subTextField;
    private JTextField mulTextField;
    private JTextField divTextField;
    private JTextField divkTextField;
    private JTextField sqrTextField;
    private JTextField singleTextField;
    private JLabel generatingStatus;
    private JTextField amountTextField;
    private JTextField columnsTextField;
    private JCheckBox skipLineCheckbox;

    private Mode currentMode;
    private Random random;
    private final int operationsAmount = 6;

    public Main() {
        random = new Random();
        random.setSeed(System.currentTimeMillis());

        generatingStatus = new JLabel("");
        generatingStatus.setBackground(backgroundColor);

        currentMode = new Mode();
        init();
        setValues(currentMode);
    }

    private void setValues(Mode mode) {
        addTextField.setText(mode.getMaxAdd() + "");
        subTextField.setText(mode.getMaxSub() + "");
        mulTextField.setText(mode.getMaxMul() + "");
        divTextField.setText(mode.getMaxDiv() + "");
        divkTextField.setText(mode.getMaxDivK() + "");
        sqrTextField.setText(mode.getMaxSqr() + "");
        singleTextField.setText(mode.getMaxSingle() + "");
    }

    private void load() {
        JFileChooser fdia = new JFileChooser(System.getProperty("user.dir") + "//res");
        FileFilter filter = new FileNameExtensionFilter("Mode files",
                "mode");
        fdia.addChoosableFileFilter(filter);
        fdia.setFileFilter(filter);

        int ret = fdia.showDialog(getContentPane(), "Load mode");

        if (ret == JFileChooser.APPROVE_OPTION) {
            currentMode = new Mode(fdia.getSelectedFile().getPath());
            setValues(currentMode);
        }
    }

    private void generate() {
        JFileChooser fdia = new JFileChooser(System.getProperty("user.dir"));

        int ret = fdia.showDialog(getContentPane(), "Generate");

        if (ret == JFileChooser.APPROVE_OPTION) {
            generatingStatus.setText("Generating..");
            File file = fdia.getSelectedFile();

            final int amount = Integer.parseInt(amountTextField.getText());
            final int columns = Integer.parseInt(columnsTextField.getText());
            final boolean skipLine = skipLineCheckbox.isSelected();

            try {
                PrintWriter out = new PrintWriter(new FileWriter(file));

                for (int i = 0; i < amount; i++) {
                    if (i > 0 && i % columns == 0) {
                        out.println();
                        if (skipLine) {
                            out.println();
                        }
                    }

                    out.print(generateExample(Math.abs(random.nextInt() % operationsAmount)));
                }

                out.close();

                generatingStatus.setText("Done!");
            } catch (IOException e) {
                e.printStackTrace();
                generatingStatus.setText("Failed!");
            }
        }
    }

    private String generateExample(int operationCode) {
        int argSpaces = currentMode.getMaxSize();
        int ansSpaces = currentMode.getMaxAnswerSize();
        int firstArg = 1;
        int secondArg = 1;
        char operator = '#';

        switch (operationCode) {
            case 0: // +
                operator = '+';
                firstArg = Math.abs(random.nextInt()) % currentMode.getMaxAdd() + 1;
                secondArg = Math.abs(random.nextInt()) % currentMode.getMaxAdd() + 1;
                break;
            case 1: // -
                operator = '-';
                firstArg = Math.abs(random.nextInt()) % currentMode.getMaxAdd() + 1;
                secondArg = Math.abs(random.nextInt()) % firstArg + 1;
                break;
            case 2: // *
                operator = '*';
                firstArg = Math.abs(random.nextInt()) % currentMode.getMaxMul() + 1;
                secondArg = Math.abs(random.nextInt()) % currentMode.getMaxMul() + 1;
                break;
            case 3: // :
                operator = ':';
                secondArg = Math.abs(random.nextInt()) % currentMode.getMaxDiv() + 1;
                firstArg = secondArg * (Math.abs(random.nextInt()) % currentMode.getMaxDivK() + 1);
                break;
            case 4: // ^2
                operator = '*';
                firstArg = Math.abs(random.nextInt()) % currentMode.getMaxSqr() + 1;
                secondArg = firstArg;
                break;
            case 5: // *[1..9]
                operator = '*';
                firstArg = Math.abs(random.nextInt()) % currentMode.getMaxSingle() + 1;
                secondArg = Math.abs(random.nextInt()) % 8 + 2;
                break;
        }

        return getSpaces(argSpaces - Mode.getDigits(firstArg)) + firstArg + " " + operator + " "
                + getSpaces(argSpaces - Mode.getDigits(secondArg)) + secondArg + "=" + getSpaces(ansSpaces);
    }

    private String getSpaces(int amount) {
        String spaces = "";

        for (int i = 0; i < amount; i++) {
            spaces += " ";
        }

        return spaces;
    }

    private void init() {
        // Main vertical layout
        JPanel mainVerticalLayout = new JPanel();
        mainVerticalLayout.setLayout(new BoxLayout(mainVerticalLayout, BoxLayout.Y_AXIS));
        mainVerticalLayout.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        mainVerticalLayout.setBackground(backgroundColor);
        add(mainVerticalLayout);

        mainVerticalLayout.add(new JLabel("Made by Igor Boiarshin"));
        mainVerticalLayout.add(Box.createRigidArea(new Dimension(0, 5)));

        // -- Operations grid --
        {
            JPanel operationsGridLayout = new JPanel();
            operationsGridLayout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            operationsGridLayout.setLayout(new GridLayout(3, 2, 5, 5));
            operationsGridLayout.setBackground(backgroundColor);

            // Add
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a + b = c"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("a " + belongs + " [1 .. "));

                        addTextField = new JTextField();
                        addTextField.setFont(mainFont);
                        addTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        addTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        addTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(addTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }

                    layout.add(new JLabel("b " + belongs + " [1 .. max(a)]"));
                }

                operationsGridLayout.add(layout);
            }

            // Sub
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a - b = c"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("a " + belongs + " [1 .. "));

                        subTextField = new JTextField();
                        subTextField.setFont(mainFont);
                        subTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        subTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        subTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(subTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }

                    layout.add(new JLabel("b " + belongs + " [1 .. a]"));
                }

                operationsGridLayout.add(layout);
            }

            // Mul
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a * b = c"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("a " + belongs + " [1 .. "));

                        mulTextField = new JTextField();
                        mulTextField.setFont(mainFont);
                        mulTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        mulTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        mulTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(mulTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }

                    layout.add(new JLabel("b " + belongs + " [1 .. max(a)]"));
                }

                operationsGridLayout.add(layout);
            }

            // Div
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a / b = c"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("b " + belongs + " [1 .. "));

                        divTextField = new JTextField();
                        divTextField.setFont(mainFont);
                        divTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        divTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        divTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(divTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("c " + belongs + " [1 .. "));

                        divkTextField = new JTextField();
                        divkTextField.setFont(mainFont);
                        divkTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        divkTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        divkTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(divkTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }
                }

                operationsGridLayout.add(layout);
            }

            // Sqr
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a^2 = b"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("a " + belongs + " [1 .. "));

                        sqrTextField = new JTextField();
                        sqrTextField.setFont(mainFont);
                        sqrTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        sqrTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        sqrTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(sqrTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }
                }

                operationsGridLayout.add(layout);
            }

            // Single
            {
                JPanel layout = new JPanel();
                layout.setLayout(new BoxLayout(layout, BoxLayout.Y_AXIS));
                layout.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));

                {
                    layout.add(new JLabel("a * b = c"));

                    {
                        JPanel aDescription = new JPanel();
                        aDescription.setLayout(new BoxLayout(aDescription, BoxLayout.X_AXIS));
                        aDescription.setAlignmentX(0.0f);

                        aDescription.add(new JLabel("a " + belongs + " [1 .. "));

                        singleTextField = new JTextField();
                        singleTextField.setFont(mainFont);
                        singleTextField.setMaximumSize(new Dimension(textInputLength, 18));
                        singleTextField.setMinimumSize(new Dimension(textInputLength, 18));
                        singleTextField.setBackground(textInputBackgroundColor);
                        aDescription.add(singleTextField);

                        aDescription.add(new JLabel("]"));

                        layout.add(aDescription);
                    }

                    layout.add(new JLabel("b " + belongs + " [1 .. 9]"));
                }

                operationsGridLayout.add(layout);
            }


            mainVerticalLayout.add(operationsGridLayout);
        }
        // -- Operations grid --
        mainVerticalLayout.add(Box.createRigidArea(new Dimension(0, 10)));


        // -- Load layout --
        JPanel loadLayout = new JPanel();
        loadLayout.setLayout(new BoxLayout(loadLayout, BoxLayout.X_AXIS));
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        loadLayout.add(loadButton);
        mainVerticalLayout.add(loadLayout);
        // -- Load layout --
        mainVerticalLayout.add(Box.createRigidArea(new Dimension(0, 10)));


        // -- Generate layout --
        JPanel generateLayout = new JPanel();
        generateLayout.setLayout(new BoxLayout(generateLayout, BoxLayout.X_AXIS));
        generateLayout.setBackground(backgroundColor);

        // Amount
        generateLayout.add(new JLabel("Amount"));
        generateLayout.add(Box.createRigidArea(new Dimension(5, 0)));

        amountTextField = new JTextField();
        amountTextField.setFont(mainFont);
        amountTextField.setMaximumSize(new Dimension(textInputLength, 18));
        amountTextField.setMinimumSize(new Dimension(textInputLength, 18));
        amountTextField.setBackground(textInputBackgroundColor);
        amountTextField.setText(2000 + "");
        generateLayout.add(amountTextField);

        generateLayout.add(Box.createRigidArea(new Dimension(10, 0)));

        // Columns
        generateLayout.add(new JLabel("Columns"));
        generateLayout.add(Box.createRigidArea(new Dimension(5, 0)));

        columnsTextField = new JTextField();
        columnsTextField.setFont(mainFont);
        columnsTextField.setMaximumSize(new Dimension(textInputLength, 18));
        columnsTextField.setMinimumSize(new Dimension(textInputLength, 18));
        columnsTextField.setBackground(textInputBackgroundColor);
        columnsTextField.setText(5 + "");
        generateLayout.add(columnsTextField);

        generateLayout.add(Box.createRigidArea(new Dimension(10, 0)));

        skipLineCheckbox = new JCheckBox("Skip line", true);
        skipLineCheckbox.setBackground(backgroundColor);
        generateLayout.add(skipLineCheckbox);

        mainVerticalLayout.add(generateLayout);
        // -- Generate layout --
        mainVerticalLayout.add(Box.createRigidArea(new Dimension(0, 10)));

        // ---
        JPanel generateLayoutButton = new JPanel();
        generateLayoutButton.setLayout(new BoxLayout(generateLayoutButton, BoxLayout.X_AXIS));
        generateLayoutButton.setBackground(backgroundColor);

        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generate();
            }
        });
        generateLayoutButton.add(generateButton);

        generateLayoutButton.add(Box.createRigidArea(new Dimension(10, 0)));

        generateLayoutButton.add(generatingStatus);

        mainVerticalLayout.add(generateLayoutButton);
        // ---


        setTitle("Quick Math");
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main app = new Main();
                app.setVisible(true);
            }
        });
    }
}
