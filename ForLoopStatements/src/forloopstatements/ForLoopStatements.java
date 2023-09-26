
package forloopstatements;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ForLoopStatements 
{
    static userInterface frame = new userInterface();
    
    public String result;
    
    public static void main(String[] args) 
    {
        frame.inputStartUp();
        
        
        frame.outputStartUp();
    }
    
    public static String[] checkTokens(String input)
    {
        String[] inputArray = input.trim().split("\\s+"); //split the input string into an array
        String[] tokensArray = new String[inputArray.length];
        
        for(int i = 0;i < inputArray.length;i++)
        {
            switch(inputArray[i])
            {
                case "for":
                    tokensArray[i] = "for";
                    break;
                case "(":
                    tokensArray[i] = "openParen";
                    break;
                case ")":
                    tokensArray[i] = "closeParen";
                    break;
                case "{":
                    tokensArray[i] = "openCurly";
                    break;
                case "}":
                    tokensArray[i] = "closeCurly";
                    break;
                case "[":
                    tokensArray[i] = "openBracket";
                    break;
                case "]":
                    tokensArray[i] = "closeBracket";
                    break;
                case ";":
                    tokensArray[i] = "semiColon";
                    break;
                case "int":
                    tokensArray[i] = "intType";
                    break;
                case "==":
                    tokensArray[i] = "equalEqual";
                    break;
                case "!=":
                    tokensArray[i] = "notEqual";
                    break;
                case "<":
                    tokensArray[i] = "lessThan";
                    break;  
                case ">":
                    tokensArray[i] = "greaterThan";
                    break;  
                case "<=":
                    tokensArray[i] = "lessOrEqual";
                    break;  
                case ">=":
                    tokensArray[i] = "greaterOrEqual";
                    break;  
                case "=":
                    tokensArray[i] = "assignmentEqual";
                    break;  
                case "System.out.println":
                    tokensArray[i] = "sysOutLn";
                    break;  
                case "System.out.print":
                    tokensArray[i] = "sysOut";
                    break; 
                default:
                    if(inputArray[i].matches("-?\\d+"))
                    {
                        tokensArray[i] = "integer";
                    }
                    else if(inputArray[i].matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$"))
                    {
                        tokensArray[i] = "variableName";
                    }
                    else if(inputArray[i].matches("^\"[^\"][^\']*\"$"))
                    {
                        tokensArray[i] = "stringValue";
                    }
                    else if(inputArray[i].matches("^[a-zA-Z_$][a-zA-Z_$0-9]*\\+\\+$") ||
                            inputArray[i].matches("^[a-zA-Z_$][a-zA-Z_$0-9]*\\-\\-$") ||
                            inputArray[i].matches("^\\+\\+[a-zA-Z_$][a-zA-Z_$0-9]*$") ||
                            inputArray[i].matches("^\\-\\-[a-zA-Z_$][a-zA-Z_$0-9]*$"))
                    {
                        tokensArray[i] = "incDec";
                    }
                    else
                    {
                        tokensArray[i] = "unknown";
                    }
            }
        }
        
        return tokensArray;
    }
    
   public boolean hasCorrectSeparators(String[] tokensArray)
   {
       int paren = 0;
       int curly = 0;
       int bracket = 0;
       
       for(int i = 0;i < tokensArray.length;i++)
       {
           if(tokensArray[i].equals("openParen"))
           {
               paren++;
           }
           else if(tokensArray[i].equals("closeParen"))
           {
               if(paren == 0)
               {
                   result+="Incorrect Sequence of Separators (for parentheses)\n";
                   return false;
               }
               paren--;
           }
           
           if(tokensArray[i].equals("openCurly"))
           {
               curly++;
           }
           else if(tokensArray[i].equals("closeCurly"))
           {
               if(curly == 0)
               {
                   result+="Incorrect Sequence of Separators (for curly braces)\n";
                   return false;
               }
               curly--;
           }
           
           if(tokensArray[i].equals("openBracket"))
           {
               bracket++;
           }
           else if(tokensArray[i].equals("closeBracket"))
           {
               if(bracket == 0)
               {
                   result+="Incorrect Sequence of Separators (for square brackets)\n";
                   return false;
               }
               bracket--;
           }
       }
       if(paren == 0 && curly == 0 && bracket == 0)
       {
            result+="Balanced and Correct Sequence of Separators\n";
            return true;
       }
       else
       {
           result+="Unbalance Separators\n";
           return false;
       }
   }
   
   public boolean isCorrectSequence(String[] tokensArray)
   {
       boolean foundFor = false;
       boolean foundOpenParen = false;
       boolean foundCloseParen = false;
       boolean foundOpenCurly = false;
       boolean foundCloseCurly = false;
       
       if(tokensArray[0].equals("for"))
       {
           foundFor = true;
           
           if(tokensArray[1].equals("openParen"))
           {
               foundOpenParen = true;
               
               for(int i = 2;i < tokensArray.length;i++)
               {
                   if(tokensArray[i].equals("openParen"))
                   {
                       if(foundCloseParen && !foundOpenCurly)
                       {
                           break;
                       }
                       else if(foundOpenParen && !foundCloseParen)
                       {
                           break;
                       }
                   }
                   else if(tokensArray[i].equals("closeParen"))
                   {
                       foundCloseParen = true;
                   }
                   else if(tokensArray[i].equals("openCurly"))
                   {
                       if(!foundCloseParen)
                       {
                           break;
                       }
                       else if(foundOpenCurly || foundCloseCurly)
                       {
                           break;
                       }
                       foundOpenCurly = true;
                   }
                   else if(tokensArray[i].equals("closeCurly"))
                   {
                       foundCloseCurly = true;
                   }
               }
           }
       }
       
       boolean foundParentheses = foundOpenParen && foundCloseParen;
       boolean foundCurlyBraces = foundOpenCurly && foundCloseCurly;
       
       if(foundFor && foundParentheses && foundCurlyBraces)
       {
           result+="Correct for, (), and {} sequence";
           return true;
       }
       else
       {
           result+="Incorrect for, (), and {} sequence";
           return false;
       }
   }
   
   public int isCorrectIncDec(String[] tokensArray, int startIndex, int endIndex, boolean flag)
   {
       if(flag)
       {
            for(int i = startIndex;i <= endIndex-1;i++)
            {
                if(tokensArray[i].equals("incDec") && tokensArray[i+1].equals("semiColon"))
                {
                    result+="Found Step Size\n";
                    return i;
                }
            }
            result+="Missing Step Size\n";
            return -1;
       }
       else
       {
            for(int i = startIndex;i <= endIndex;i++)
            {
                if(tokensArray[i].equals("incDec"))
                {
                    result+="Found Step Size\n";
                    return i;
                }
            }
            result+="Missing Step Size\n";
            return -1;
       }
   }
   
   public int isCorrectInitialization(String[] tokens, int startIndex, int endIndex)
   {
        for (int i = startIndex; i <= endIndex-4; i++) 
        {
            if ((tokens [i].equals("intType") &&
            tokens [i+1].equals("variableName") &&
            tokens [i+2].equals("assignmentEqual") &&
            (tokens [i+3].equals("integer") || tokens [i+3].equals("variableName")) && tokens [i+4].equals("semiColon"))) 
            {
                result+="Found Correct Initialization\n";
                return i;
            }
            
        }
        for (int i = startIndex; i <= endIndex-3; i++) 
        {
            if ((tokens [i].equals("variableName") &&
            tokens [i+1].equals("assignmentEqual") &&
            (tokens [i+2].equals("integer") || tokens [i+2].equals ("variableName")) && tokens [i+3].equals("semiColon"))) 
            {
                result+="Found Correct Initialization\n";
                return i;
            }
               
        }
        result+="Incorrect Initialization\n";
        return -1;
   }
}

class userInterface extends WindowAdapter
{
        private JFrame inputScreen, outputScreen;
        
        private JPanel inputScreenP1, inputScreenP2, inputScreenP3, inputScreenP4,
                    outputScreenP1, outputScreenP2, outputScreenP3,outputScreenP4;
        
        private JButton checkB, backB, exitB;
        
        private JTextArea inputTA, resultTA;
        
        private JLabel forLoopL, forLoopL2, enterL, resultL;
        
        private String inputString;
        
        public userInterface()
        {
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
        
        public void inputStartUp()
        {
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
            inputScreen.addWindowListener(new WindowAdapter()
                                        {
                                            public void windowClosing (WindowEvent e) 
                                            {    
                                                JFrame f = new JFrame();
                                                JOptionPane.showMessageDialog(f,"Exiting Program...","Exit Screen",JOptionPane.WARNING_MESSAGE);
                                                System.exit(0);
                                            }    
                                        });
            
            checkB.addActionListener(
                                    e ->{
                                            inputString = inputTA.getText();
                                        
                                            inputScreen.setVisible(false);
                                            outputScreen.setVisible(true);
                                        }
                                    );
        }
        
        public void outputStartUp()
        {
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
            outputScreen.addWindowListener(new WindowAdapter()
                                        {
                                            public void windowClosing (WindowEvent e) 
                                            {    
                                                JFrame f = new JFrame();
                                                JOptionPane.showMessageDialog(f,"Exiting Program...","Exit Screen",JOptionPane.WARNING_MESSAGE);
                                                System.exit(0);
                                            }    
                                        });
            
            backB.addActionListener(
                                    e ->{
                                            outputScreen.setVisible(false);
                                            inputScreen.setVisible(true);
                                        }
                                    );
            
            exitB.addActionListener(
                                    e ->{
                                            JFrame f = new JFrame();
                                            JOptionPane.showMessageDialog(f,"Exiting Program...","Exit Screen",JOptionPane.WARNING_MESSAGE);
                                            System.exit(0);
                                        }
                                    );
        }
}





