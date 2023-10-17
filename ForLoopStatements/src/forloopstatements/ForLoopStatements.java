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

                    String[] tokensArray = tokenChecker(inputString);
                    System.out.println(Arrays.toString(tokensArray));

                    if (separatorChecker(tokensArray)) {
                        if (sequenceChecker(tokensArray)) {
                            int startIndex = 0;
                            int endIndex = tokensArray.length - 1;
                            for (int i = 0; i < tokensArray.length; i++) {
                                if (tokensArray[i].equals("parenthesis1")) {
                                    startIndex = i;
                                } else if (tokensArray[i].equals("parenthesis2")) {
                                    endIndex = i;
                                    break;
                                }
                            }

                            flag = false;
                            int initIndex = initializationChecker(tokensArray, startIndex, endIndex);
                            int conditionIndex = conditionChecker(tokensArray, startIndex, endIndex);
                            int incDecIndex = stepSizeChecker(tokensArray, startIndex, endIndex, flag);

                            if (initIndex > -1 && conditionIndex > -1 && incDecIndex > -1) {
                                if (initIndex < conditionIndex && conditionIndex < incDecIndex) {
                                    result += "✓ Initialization\n";
                                    result += "✓ Condition \n";
                                    result += "✓ Step Size \n\n";
                                    result += "Checking Body...\n";
                                    result += "▒▒▒▒▒▒▒▒▒▒ 100% ᴄᴏᴍᴘʟᴇᴛᴇ!\n";
                                    startIndex = 0;
                                    endIndex = tokensArray.length - 1;
                                    for (int i = 0; i < tokensArray.length; i++) {
                                        if (tokensArray[i].equals("curlyBracket1")) {
                                            startIndex = i;
                                        } else if (tokensArray[i].equals("curlyBracket2")) {
                                            endIndex = i;
                                            break;
                                        }
                                    }

                                    if ((endIndex - startIndex) > 1) {
                                        bodyChecker(tokensArray, startIndex, endIndex);
                                    }
                                } else {
                                    result += "✖ Initialization, Condition, and Step Size are incorrect!\n";
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

    public String[] tokenChecker(String input) {
        String[] inputArrayFirst = input.trim().split("\\s+");
        java.util.List<String> inputArraySecond = Arrays.asList(inputArrayFirst);
        java.util.List<String> inputArray = new ArrayList<>(inputArraySecond);
        
        Map<String, String> keywordMappings = new HashMap<>();
        keywordMappings.put("for", "for");
        keywordMappings.put("(", "parenthesis1");
        keywordMappings.put(")", "parenthesis2");
        keywordMappings.put("{", "curlyBracket1");
        keywordMappings.put("}", "curlyBracket2");
        keywordMappings.put("[", "squareBracket1");
        keywordMappings.put("]", "squareBracket2");
        keywordMappings.put(";", "semiColon");
        keywordMappings.put("int", "typeInt");
        keywordMappings.put("==", "equalEqual");
        keywordMappings.put("!=", "notEqual");
        keywordMappings.put("<", "lessThan");
        keywordMappings.put(">", "greaterThan");
        keywordMappings.put("<=", "lessOrEqual");
        keywordMappings.put(">=", "greaterOrEqual");
        keywordMappings.put("=", "assignmentEqual");
        keywordMappings.put("System.out.println", "printNewLine");
        keywordMappings.put("System.out.print", "printMethod");
        
        String part1 = "";
        int j = 0;
        while(j < inputArray.size())
        {
            boolean retry = true;
            String token = inputArray.get(j);
            if(token.equals("(") || token.equals(")") || token.equals(";") || token.equals("=") || token.equals("<=") || token.equals(">=") || token.equals("<") || token.equals(">") || token.equals("{") || token.equals("}"))
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
            else if (token.endsWith("{")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, "{");
            }
            else if (token.endsWith("}")) {
                part1 = token.substring(0, token.length() - 1); // Remove the ";" from the end
                inputArray.set(j,part1);
                inputArray.add(j+1, "}");
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
                String[] parts9 = token.split("\\<", 2);
                String[] parts10 = token.split("\\>", 2);
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
                else if(parts9.length > 1){
                    inputArray.set(j,parts9[0]);
                    inputArray.add(j+1,"{");
                    inputArray.add(j+2,parts9[1]);
                }
                else if(parts10.length > 1){
                    inputArray.set(j,parts10[0]);
                    inputArray.add(j+1,"}");
                    inputArray.add(j+2,parts10[1]);
                }
                else
                    retry = false;
            }
            if(!retry)
                j++;
        }
        
        
        for (int i = 0; i < inputArray.size(); i++) {
            String element = inputArray.get(i);
            if(element.isEmpty())
                inputArray.remove(i);
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

    public boolean separatorChecker(String[] tokensArray) {
        int parenthesis1Count = 0;
        int curlyBracket1Count = 0;
        int squareBracket1Count = 0;

        for (String token : tokensArray) {
            switch (token) {
                case "parenthesis1":
                    parenthesis1Count++;
                    break;
                case "parenthesis2":
                    if (parenthesis1Count == 0) {
                        return handleError("parentheses");
                    }
                    parenthesis1Count--;
                    break;
                case "curlyBracket1":
                    curlyBracket1Count++;
                    break;
                case "curlyBracket2":
                    if (curlyBracket1Count == 0) {
                        return handleError("curly braces");
                    }
                    curlyBracket1Count--;
                    break;
                case "squareBracket1":
                    squareBracket1Count++;
                    break;
                case "squareBracket2":
                    if (squareBracket1Count == 0) {
                        return handleError("square brackets");
                    }
                    squareBracket1Count--;
                    break;
            }
        }

        return checkBalanced(parenthesis1Count, curlyBracket1Count, squareBracket1Count);
    }

    private boolean handleError(String separatorType) {
        result += "✖ Sequence of Separators is incorrect (for " + separatorType + ")\n";
        return false;
    }

    private boolean checkBalanced(int parenthesis1Count, int curlyBracket1Count, int squareBracket1Count) {
        if (parenthesis1Count == 0 && curlyBracket1Count == 0 && squareBracket1Count == 0) {
            result += "✓ Balanced and Correct Sequence of Separators\n";
            return true;
        } else {
            result += "✖ Separators are unbalanced!\n";
            return false;
        }
    }

    public boolean sequenceChecker(String[] tokensArray) {
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
                if (!token.equals("parenthesis1")) {
                    break;
                }
                foundOpenParen = true;
            } else if (token.equals("oppenParen")) {
                foundOpenParen = true;
                foundCloseParen = false;
                closedParen = false;
            } else if (token.equals("parenthesis2")) {
                if (foundOpenParen) {
                    foundCloseParen = true;
                    closedParen = true;
                }
            } else if (token.equals("curlyBracket1")) {
                if (foundOpenParen && foundCloseParen) {
                    foundOpenCurly = true;
                } else {
                    break;
                }
            } else if (token.equals("curlyBracket2")) {
                if (foundOpenCurly) {
                    foundCloseCurly = true;
                }
            }
        }

        boolean isCorrect = foundFor && foundOpenParen && closedParen && foundCloseParen && foundOpenCurly && foundCloseCurly;
        result += isCorrect ? "✓ Correct Sequence of: for, (), and {} \n" : "✖ Wrong Sequence of: for, (), and {} \n";
        return isCorrect;
    }

    public int stepSizeChecker(String[] tokensArray, int startIndex, int endIndex, boolean flag) {
        boolean stepSizeFound = false;

        for (int i = startIndex; i <= endIndex; i++) {
            if (tokensArray[i].equals("incDec")) {
                if (flag && i < endIndex && tokensArray[i + 1].equals("semiColon")) {
                    stepSizeFound = true;
                    result += "✓ Step Size Found!\n";
                    return i;
                } else if (!flag) {
                    stepSizeFound = true;
                    result += "✓ Step Size Found\n";
                    return i;
                }
            }
        }

        if (!stepSizeFound) {
            result += "✖ Step Size Not Found\n";
        }

        return -1;
    }


    public int initializationChecker(String[] tokens, int startIndex, int endIndex) {
        boolean flag = true;
        for (int i = startIndex; i <= endIndex - 4; i++) {
            if ((tokens[i].equals("typeInt") && tokens[i + 1].equals("variableName") && !tokens[i - 1].equals("variableName") &&  tokens[i + 2].equals("assignmentEqual")
                    && (tokens[i + 3].equals("integer") || tokens[i + 3].equals("variableName")) && tokens[i + 4].equals("semiColon"))) {
                result += "✓ Correct Initialization\n";
                return i;
            }
            if ((tokens[i].equals("typeInt") && tokens[i + 1].equals("variableName") && tokens[i - 1].equals("variableName")))
            {
                flag = false;
                break;
            }
        }
        
        if(flag)
        {
            for (int i = startIndex; i <= endIndex - 3; i++) {
                if ((tokens[i].equals("variableName") && !tokens[i - 1].equals("variableName") && tokens[i + 1].equals("assignmentEqual")
                        && (tokens[i + 2].equals("integer") || tokens[i + 2].equals("variableName"))
                        && tokens[i + 3].equals("semiColon"))) {
                    result += "✓ Correct Initialization\n";
                    return i;
                }
                if ((tokens[i].equals("variableName") && tokens[i + 1].equals("assignmentEqual")))
                {
                    flag = false;
                    break;
                }
            }
        }
        result += "✖ Initialization is incorrect!\n";
        return -1;
    }

    public int conditionChecker(String[] tokensArray, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex - 3; i++) {
            if ((tokensArray[i].equals("variableName")
                    && (tokensArray[i + 1].equals("lessThan") || tokensArray[i + 1].equals("greaterThan")
                    || tokensArray[i + 1].equals("lessOrEqual") || tokensArray[i + 1].equals("greaterOrEqual")
                    || tokensArray[i + 1].equals("equalEqual") || tokensArray[i + 1].equals("notEqual"))
                    && (tokensArray[i + 2].equals("integer") || tokensArray[i + 2].equals("variableName"))
                    && tokensArray[i + 3].equals("semiColon"))) {
                result += "✓ Correct Condition\n";
                return i;
            }
        }
        result += "✖ Wrong Condition\n";
        return -1;
    }

    public int printChecker(String[] tokensArray, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex - 4; i++) {
            if ((tokensArray[i].equals("printMethod") || tokensArray[i].equals("printNewLine"))
                    && tokensArray[i + 1].equals("parenthesis1")
                    && (tokensArray[i + 2].equals("stringValue") || tokensArray[i + 2].equals("variableName"))
                    && tokensArray[i + 3].equals("parenthesis2")
                    && tokensArray[i + 4].equals("semiColon")) {
                result += "✓ Correct Print Method\n";
                return i;
            }
        }
        result += "✖ Wrong Print Method\n";
        return -1;
    }

    public boolean bodyChecker(String[] tokensArray, int startIndex, int endIndex) {
        boolean flag2 = true;
        int initCounter = 0, incDecCounter = 0, printMethodCounter = 0, indexTracker = startIndex;

        for (int i = startIndex + 1; i < endIndex; i++) {
            if (tokensArray[i].equals("typeInt")) {
                initCounter++;
            } else if (tokensArray[i].equals("variableName")) {
                initCounter++;
                printMethodCounter++;
            } else if (tokensArray[i].equals("assignmentEqual")) {
                initCounter++;
            } else if (tokensArray[i].equals("integer")) {
                initCounter++;
                printMethodCounter++;
            } else if (tokensArray[i].equals("incDec")) {
                incDecCounter += 10;
            } else if (tokensArray[i].equals("printMethod") || tokensArray[i].equals("printNewLine") || tokensArray[i].equals("parenthesis1") || tokensArray[i].equals("stringValue") || tokensArray[i].equals("parenthesis2")) {
                printMethodCounter++;
            } else if (tokensArray[i].equals("semiColon")) {
                if (initCounter > incDecCounter && initCounter > printMethodCounter) {
                    initializationChecker(tokensArray, indexTracker, i + 1);
                    indexTracker = i + 1;
                    initCounter = 0;
                    incDecCounter = 0;
                    printMethodCounter = 0;
                    flag2 = false;
                } else if (incDecCounter > initCounter && incDecCounter > printMethodCounter) {
                    stepSizeChecker(tokensArray, indexTracker, i + 1, true);
                    indexTracker = i + 1;
                    initCounter = 0;
                    incDecCounter = 0;
                    printMethodCounter = 0;
                    flag2 = false;
                } else if (printMethodCounter > initCounter && printMethodCounter > incDecCounter) {
                    printChecker(tokensArray, indexTracker, i + 1);
                    indexTracker = i + 1;
                    initCounter = 0;
                    incDecCounter = 0;
                    printMethodCounter = 0;
                    flag2 = false;
                } else if (tokensArray[i + 1].equals("curlyBracket2")) {
                    flag2 = false;
                    return true;
                }
            }
        }

        if (flag2) {
            result += "✖ Error\n";
        }
        return false;
    }
}