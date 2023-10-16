package forloopstatements;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForLoopStatements {

    static userInterface frame = new userInterface();

    public static void main(String[] args) {
        frame.inputStartUp();
        frame.outputStartUp();
    }
}

class userInterface extends WindowAdapter {

    private JFrame inputScreen, outputScreen;

    private JPanel inputScreenP1, inputScreenP2, inputScreenP3, inputScreenP4,
            outputScreenP1, outputScreenP2, outputScreenP3, outputScreenP4;

    private JButton checkB, backB, exitB;

    private JTextArea inputTA, resultTA;

    private JLabel forLoopL, forLoopL2, enterL, resultL;

    private String inputString, result = "";

    private boolean flag = false;

    public userInterface() {
        inputScreen = new JFrame("Input Screen");
        outputScreen = new JFrame("Output Screen");

        //inputScreen components
        inputScreenP1 = new JPanel();
        inputScreenP2 = new JPanel();
        inputScreenP3 = new JPanel();
        inputScreenP4 = new JPanel();
        forLoopL = new JLabel("For Loop Statements");
        enterL = new JLabel("Enter your For Loop Statement in the text area:");
        inputTA = new JTextArea("", 10, 35);
        checkB = new JButton("    Check!    ");

        //outputScreen components
        outputScreenP1 = new JPanel();
        outputScreenP2 = new JPanel();
        outputScreenP3 = new JPanel();
        outputScreenP4 = new JPanel();
        forLoopL2 = new JLabel("For Loop Statements");
        resultL = new JLabel("Result of checking your For Loop Statement");
        resultTA = new JTextArea("", 10, 35);
        backB = new JButton("    Go Back    ");
        exitB = new JButton("    Exit    ");
    }

    public void inputStartUp() {
        //adding a vertical scroll bar on the TextArea
        JScrollPane scroll = new JScrollPane(inputTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        forLoopL.setFont(forLoopL.getFont().deriveFont(Font.BOLD, 20));

        //add components to panel
        inputScreenP1.add(forLoopL);
        inputScreenP2.add(enterL);
        inputScreenP3.add(scroll);
        inputScreenP4.add(checkB);

        //set layout
        inputScreen.setLayout(new FlowLayout());

        //add panels to frame
        inputScreen.add(inputScreenP1);
        inputScreen.add(inputScreenP2);
        inputScreen.add(inputScreenP3);
        inputScreen.add(inputScreenP4);

        //setting size, set location relative to null to center the window, set visibility to true, set resizable to false
        inputScreen.setSize(450, 325);
        inputScreen.setLocationRelativeTo(null);
        inputScreen.setVisible(true);
        inputScreen.setResizable(false);

        //adding an anonymouse to buttons
        inputScreen.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, "Exiting Program...", "Exit Screen", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        });

        checkB.addActionListener(
                e -> {
                    inputString = inputTA.getText();

                    String[] tokensArray = checkTokens(inputString);
                    System.out.println(Arrays.toString(tokensArray));

                    if (hasCorrectSeparators(tokensArray)) {
                        if (isCorrectSequence(tokensArray)) {
                            int startIndex = 0;
                            int endIndex = tokensArray.length - 1;
                            for (int i = 0; i < tokensArray.length; i++) {
                                if (tokensArray[i].equals("openParen")) {
                                    startIndex = i;
                                } else if (tokensArray[i].equals("closeParen")) {
                                    endIndex = i;
                                    break;
                                }
                            }

                            flag = false;
                            int initIndex = isCorrectInitialization(tokensArray, startIndex, endIndex);
                            int conditionIndex = isCorrectCondition(tokensArray, startIndex, endIndex);
                            int incDecIndex = isCorrectIncDec(tokensArray, startIndex, endIndex, flag);

                            if (initIndex > -1 && conditionIndex > -1 && incDecIndex > -1) {
                                if (initIndex < conditionIndex && conditionIndex < incDecIndex) {
                                    result += "Correct Order of Initialization, Condition, and Step Size\n\n";
                                    result += "Checking Statements Inside the Body\n";
                                    result += "______________________________________\n";
                                    startIndex = 0;
                                    endIndex = tokensArray.length - 1;
                                    for (int i = 0; i < tokensArray.length; i++) {
                                        if (tokensArray[i].equals("openCurly")) {
                                            startIndex = i;
                                        } else if (tokensArray[i].equals("closeCurly")) {
                                            endIndex = i;
                                            break;
                                        }
                                    }

                                    if ((endIndex - startIndex) > 1) {
                                        checkBody(tokensArray, startIndex, endIndex);
                                    }
                                } else {
                                    result += "Incorrect Order of Initialization, Condition, and Step Size\n";
                                }
                            }
                        }
                    }
                    resultTA.setText(result);
                    inputScreen.setVisible(false);
                    outputScreen.setVisible(true);
                }
        );
    }

    public void outputStartUp() {
        //set Text Area to not editable
        resultTA.setEditable(false);

        //adding a vertical scroll bar on the TextArea
        JScrollPane scroll = new JScrollPane(resultTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        forLoopL2.setFont(forLoopL2.getFont().deriveFont(Font.BOLD, 20));

        //add components to panel
        outputScreenP1.add(forLoopL2);
        outputScreenP2.add(resultL);
        outputScreenP3.add(scroll);
        outputScreenP4.add(backB);
        outputScreenP4.add(exitB);

        //set layout
        outputScreen.setLayout(new FlowLayout());

        //add panels to frame
        outputScreen.add(outputScreenP1);
        outputScreen.add(outputScreenP2);
        outputScreen.add(outputScreenP3);
        outputScreen.add(outputScreenP4);

        //setting size, set location relative to null to center the window, set resizable to false
        outputScreen.setSize(450, 325);
        outputScreen.setLocationRelativeTo(null);
        outputScreen.setResizable(false);

        //adding an anonymouse windowListerner for the close button
        outputScreen.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f, "Exiting Program...", "Exit Screen", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        });

        backB.addActionListener(
                e -> {
                    outputScreen.setVisible(false);
                    inputScreen.setVisible(true);
                    result = "";
                }
        );

        exitB.addActionListener(
                e -> {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "Exiting Program...", "Exit Screen", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
        );
    }

    public String[] checkTokens(String input) {
        String[] inputArrayFirst = input.trim().split("\\s+");
        java.util.List<String> inputArraySecond = Arrays.asList(inputArrayFirst);
        java.util.List<String> inputArray = new ArrayList<>(inputArraySecond);
        
        Map<String, String> keywordMappings = new HashMap<>();
        keywordMappings.put("for", "for");
        keywordMappings.put("(", "openParen");
        keywordMappings.put(")", "closeParen");
        keywordMappings.put("{", "openCurly");
        keywordMappings.put("}", "closeCurly");
        keywordMappings.put("[", "openBracket");
        keywordMappings.put("]", "closeBracket");
        keywordMappings.put(";", "semiColon");
        keywordMappings.put("int", "intType");
        keywordMappings.put("==", "equalEqual");
        keywordMappings.put("!=", "notEqual");
        keywordMappings.put("<", "lessThan");
        keywordMappings.put(">", "greaterThan");
        keywordMappings.put("<=", "lessOrEqual");
        keywordMappings.put(">=", "greaterOrEqual");
        keywordMappings.put("=", "assignmentEqual");
        keywordMappings.put("System.out.println", "sysOutLn");
        keywordMappings.put("System.out.print", "sysOut");
        
        String part1 = "";
        int j = 0;
        while(j < inputArray.size())
        {
            boolean retry = true;
            String token = inputArray.get(j);
            if(token.equals("(") || token.equals(")") || token.equals(";") || token.equals("=") || token.equals("<=") || token.equals(">=") || token.equals("<") || token.equals(">"))
            { 
                retry = false;
            }
            else if (token.endsWith("(")) {
                part1 = token.substring(0, token.length() - 1); // Remove the "(" from the end
                System.out.println("Part 1:" + part1);
                inputArray.set(j,part1);
                inputArray.add(j+1, "(");
            }
            else if (token.endsWith(")")) {
                part1 = token.substring(0, token.length() - 1); // Remove the "(" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, ")");
            }
            else if (token.endsWith(";")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, ";");
            }
            else if (token.endsWith("=")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, "=");
            }else if (token.endsWith("<=")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, "<=");
            }
            else if (token.endsWith(">=")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, ">=");
            }
            else if (token.endsWith("<")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, "<");
            }
            else if (token.endsWith(">")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, ">");
            }
            else
            {
                String[] parts = token.split("\\(", 2);
                String[] parts2 = token.split("\\)", 2);
                String[] parts3 = token.split("\\=", 2);
                String[] parts4 = token.split("\\;", 2);
                String[] parts5 = token.split("\\<=", 2);
                String[] parts6 = token.split("\\>=", 2);
                String[] parts7 = token.split("\\<", 2);
                String[] parts8 = token.split("\\>", 2);
                if(parts.length > 1){
                    inputArray.set(j,parts[0]);
                    inputArray.add(j+1,"(");
                    inputArray.add(j+2,parts[1]);
                }
                else if(parts2.length > 1){
                    inputArray.set(j,parts2[0]);
                    inputArray.add(j+1,")");
                    inputArray.add(j+2,parts2[1]);
                }
                else if(parts4.length > 1){
                    inputArray.set(j,parts4[0]);
                    inputArray.add(j+1,";");
                    inputArray.add(j+2,parts4[1]);
                }
                else if(parts5.length > 1){
                    inputArray.set(j,parts5[0]);
                    inputArray.add(j+1,"<=");
                    inputArray.add(j+2,parts5[1]);
                }
                else if(parts6.length > 1){
                    inputArray.set(j,parts6[0]);
                    inputArray.add(j+1,">=");
                    inputArray.add(j+2,parts6[1]);
                }
                else if(parts3.length > 1){
                    inputArray.set(j,parts3[0]);
                    inputArray.add(j+1,"=");
                    inputArray.add(j+2,parts3[1]);
                }
                else if(parts7.length > 1){
                    inputArray.set(j,parts7[0]);
                    inputArray.add(j+1,"<");
                    inputArray.add(j+2,parts7[1]);
                }
                else if(parts8.length > 1){
                    inputArray.set(j,parts8[0]);
                    inputArray.add(j+1,">");
                    inputArray.add(j+2,parts8[1]);
                }
                else
                    retry = false;
            }
            if(!retry)
                j++;
        }
        
        
        for (int i = 0; i < inputArray.size(); i++) {
            String element = inputArray.get(i);
            System.out.println(element);
        }
        String[] tokensArray = new String[inputArray.size()];
        for (int i = 0; i < inputArray.size(); i++) {
            String token = inputArray.get(i);
            if (keywordMappings.containsKey(token)) {
                tokensArray[i] = keywordMappings.get(token);
            } else if (token.matches("-?\\d+")) {
                tokensArray[i] = "integer";
            } else if (token.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
                tokensArray[i] = "variableName";
            } else if (token.matches("^\"[^\"][^\']*\"$")) {
                tokensArray[i] = "stringValue";
            } else if (token.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*\\+\\+$")
                    || token.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*\\-\\-$")
                    || token.matches("^\\+\\+[a-zA-Z_$][a-zAZ_$0-9]*$")
                    || token.matches("^\\-\\-[a-zA-Z_$][a-zA-Z_$0-9]*$")) {
                tokensArray[i] = "incDec";
            } else {
                tokensArray[i] = "unknown";
            }
        }

        return tokensArray;
    } // REMADE

    public boolean hasCorrectSeparators(String[] tokensArray) {
        int openParenCount = 0;
        int openCurlyCount = 0;
        int openBracketCount = 0;

        for (String token : tokensArray) {
            switch (token) {
                case "openParen":
                    openParenCount++;
                    break;
                case "closeParen":
                    if (openParenCount == 0) {
                        return handleError("parentheses");
                    }
                    openParenCount--;
                    break;
                case "openCurly":
                    openCurlyCount++;
                    break;
                case "closeCurly":
                    if (openCurlyCount == 0) {
                        return handleError("curly braces");
                    }
                    openCurlyCount--;
                    break;
                case "openBracket":
                    openBracketCount++;
                    break;
                case "closeBracket":
                    if (openBracketCount == 0) {
                        return handleError("square brackets");
                    }
                    openBracketCount--;
                    break;
            }
        }

        return checkBalanced(openParenCount, openCurlyCount, openBracketCount);
    }

    private boolean handleError(String separatorType) {
        result += "Incorrect Sequence of Separators (for " + separatorType + ")\n";
        return false;
    }

    private boolean checkBalanced(int openParenCount, int openCurlyCount, int openBracketCount) {
        if (openParenCount == 0 && openCurlyCount == 0 && openBracketCount == 0) {
            result += "Balanced and Correct Sequence of Separators\n";
            return true;
        } else {
            result += "Unbalanced Separators\n";
            return false;
        }
    }

    public boolean isCorrectSequence(String[] tokensArray) {
        boolean foundFor = false;
        boolean foundOpenParen = false;
        boolean foundCloseParen = false;
        boolean foundOpenCurly = false;
        boolean foundCloseCurly = false;
        boolean closedParen = false;

        for (int i = 0; i < tokensArray.length; i++) {
            String token = tokensArray[i];
            if (i == 0) {
                if (!token.equals("for")) {
                    break;
                }
                foundFor = true;
            } else if (i == 1 && foundFor) {
                if (!token.equals("openParen")) {
                    break;
                }
                foundOpenParen = true;
            } else if (token.equals("oppenParen")) {
                foundOpenParen = true;
                foundCloseParen = false;
                closedParen = false;
            } else if (token.equals("closeParen")) {
                if (foundOpenParen) {
                    foundCloseParen = true;
                    closedParen = true;
                }
            } else if (token.equals("openCurly")) {
                if (foundOpenParen && foundCloseParen) {
                    foundOpenCurly = true;
                } else {
                    break;
                }
            } else if (token.equals("closeCurly")) {
                if (foundOpenCurly) {
                    foundCloseCurly = true;
                }
            }
        }

        boolean isCorrect = foundFor && foundOpenParen && closedParen && foundCloseParen && foundOpenCurly && foundCloseCurly;
        result += isCorrect ? "Correct for, (), and {} sequence\n" : "Incorrect for, (), and {} sequence\n";
        return isCorrect;
    }

    public int isCorrectIncDec(String[] tokensArray, int startIndex, int endIndex, boolean flag) {
        if (flag) {
            for (int i = startIndex; i <= endIndex - 1; i++) {
                if (tokensArray[i].equals("incDec") && tokensArray[i + 1].equals("semiColon")) {
                    result += "Found Step Size\n";
                    return i;
                }
            }
            result += "Missing Step Size\n";
            return -1;
        } else {
            for (int i = startIndex; i <= endIndex; i++) {
                if (tokensArray[i].equals("incDec")) {
                    result += "Found Step Size\n";
                    return i;
                }
            }
            result += "Missing Step Size\n";
            return -1;
        }
    }

    public int isCorrectInitialization(String[] tokens, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex - 4; i++) {
            if ((tokens[i].equals("intType") && tokens[i + 1].equals("variableName") && tokens[i + 2].equals("assignmentEqual")
                    && (tokens[i + 3].equals("integer") || tokens[i + 3].equals("variableName")) && tokens[i + 4].equals("semiColon"))) {
                result += "Found Correct Initialization\n";
                return i;
            }

        }
        for (int i = startIndex; i <= endIndex - 3; i++) {
            if ((tokens[i].equals("variableName") && tokens[i + 1].equals("assignmentEqual")
                    && (tokens[i + 2].equals("integer") || tokens[i + 2].equals("variableName"))
                    && tokens[i + 3].equals("semiColon"))) {
                result += "Found Correct Initialization\n";
                return i;
            }

        }
        result += "Incorrect Initialization\n";
        return -1;
    }

    public int isCorrectCondition(String[] tokensArray, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex - 3; i++) {
            if ((tokensArray[i].equals("variableName")
                    && (tokensArray[i + 1].equals("lessThan") || tokensArray[i + 1].equals("greaterThan")
                    || tokensArray[i + 1].equals("lessOrEqual") || tokensArray[i + 1].equals("greaterOrEqual")
                    || tokensArray[i + 1].equals("equalEqual") || tokensArray[i + 1].equals("notEqual"))
                    && (tokensArray[i + 2].equals("integer") || tokensArray[i + 2].equals("variableName"))
                    && tokensArray[i + 3].equals("semiColon"))) {
                result += "Found Correct Condition\n";
                return i;
            }
        }
        result += "Incorrect Condition\n";
        return -1;
    }

    public int isCorrectSysOut(String[] tokensArray, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex - 4; i++) {
            if ((tokensArray[i].equals("sysOut") || tokensArray[i].equals("sysOutLn"))
                    && tokensArray[i + 1].equals("openParen")
                    && (tokensArray[i + 2].equals("stringValue") || tokensArray[i + 2].equals("variableName"))
                    && tokensArray[i + 3].equals("closeParen")
                    && tokensArray[i + 4].equals("semiColon")) {
                result += "Found Correct Sysout\n";
                return i;
            }
        }
        result += "Incorrect Sysout\n";
        return -1;
    }

    public boolean checkBody(String[] tokensArray, int startIndex, int endIndex) {
        boolean flag2 = true;
        int initCounter = 0, incDecCounter = 0, sysOutCounter = 0, indexTracker = startIndex;

        for (int i = startIndex + 1; i < endIndex; i++) {
            switch (tokensArray[i]) {
                case "intType":
                    initCounter++;
                    break;
                case "variableName":
                    initCounter++;
                    sysOutCounter++;
                    break;
                case "assignmentEqual":
                    initCounter++;
                    break;
                case "integer":
                    initCounter++;
                    break;
                case "incDec":
                    incDecCounter += 10;
                    break;
                case "sysOut":
                    sysOutCounter++;
                    break;
                case "sysOutLn":
                    sysOutCounter++;
                    break;
                case "openParen":
                    sysOutCounter++;
                    break;
                case "stringValue":
                    sysOutCounter++;
                    break;
                case "closeParen":
                    sysOutCounter++;
                    break;
                case "semiColon":
                    if (initCounter > incDecCounter && initCounter > sysOutCounter) {
                        isCorrectInitialization(tokensArray, indexTracker, i + 1);
                        indexTracker = i + 1;
                        initCounter = 0;
                        incDecCounter = 0;
                        sysOutCounter = 0;
                        flag2 = false;
                    } else if (incDecCounter > initCounter && incDecCounter > sysOutCounter) {
                        isCorrectIncDec(tokensArray, indexTracker, i + 1, true);
                        indexTracker = i + 1;
                        initCounter = 0;
                        incDecCounter = 0;
                        sysOutCounter = 0;
                        flag2 = false;
                    } else if (sysOutCounter > initCounter && sysOutCounter > incDecCounter) {
                        isCorrectSysOut(tokensArray, indexTracker, i + 1);
                        indexTracker = i + 1;
                        initCounter = 0;
                        incDecCounter = 0;
                        sysOutCounter = 0;
                        flag2 = false;

                    } else if (tokensArray[i + 1].equals("closeCurly")) {
                        flag2 = false;
                        return true;
                    }
                    break;
            }
        }
        if (flag2 == true) {
            result += "Error\n";
        }
        return false;
    }
}
