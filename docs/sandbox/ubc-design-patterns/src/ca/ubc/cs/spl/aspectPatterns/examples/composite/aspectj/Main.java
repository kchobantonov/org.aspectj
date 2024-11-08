package ca.ubc.cs.spl.aspectPatterns.examples.composite.aspectj;

/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This file is part of the design patterns project at UBC
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * either https://www.mozilla.org/MPL/ or https://aspectj.org/MPL/.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is ca.ubc.cs.spl.aspectPatterns.
 * 
 * For more details and the latest version of this code, please see:
 * https://www.cs.ubc.ca/labs/spl/projects/aodps.html
 *
 * Contributor(s):   
 */
 
import java.util.Enumeration;

/**
 * Implements the driver for the Composite design pattern example.<p> 
 *
 * Intent: <i>Compose objects into tree structures to represent part-whole 
 * hierarchies. Composite lets clients treat individual objects and 
 * compositions of objects uniformly.</i><p>
 *
 * Participating classes are <code>Directory</code>s as <i>Composite</i>s,
 * and <code>File</code>s as <i>Leaf</i>s. Both implement the 
 * <i>Component</i> interface.<p>
 *
 * This example creates a simple structure as follows: Composite directory1 
 * has three children: file1, directory2, and file3. directory2 has file2 
 * as a child.
 * 
 * Compact notation: directory1(file1, directory2(file2), file3)
 *
 * <p><i>This is the AspectJ version.</i><p> 
 *
 * <i>Composite</i>s and <i>Leaf</i>s do not need to know about their 
 * role in the pattern.
 * 
 * This example also illustrates how to define methods that collect 
 * information from the whole aggreagate structure (using visitors).
 * One of them prints the composite structure, the other one collects the
 * sum of the values of all leaves in the structure.
 *
 * @author  Jan Hannemann
 * @author  Gregor Kiczales
 * @version 1.1, 02/06/04
 * 
 * @see Directory 
 * @see File
 */
  
  public class Main {  
    
	/**
	 * helper variable to store recursion depth for pretty printing
	 */
	 
    static int indent = 0;  
    
    /**
     * Print a number of spaces according to the current recursion depth
     */
     	
    private static void indent() {
        for (int i=0; i<indent; i++)
            System.out.print(" ");
    }
    
    /** 
     * Pretty-prints a recursive composite structure 
     *
     * @param comp the component denoting the entry point into the structure
     */
     
    private static void printStructure(FileSystemComposition.Component comp) {   
        indent();
        System.out.println(comp);
        indent +=4;                
        for (Enumeration enum = 
        	FileSystemComposition.aspectOf().getAllChildren(comp); 
        	enum.hasMoreElements();) {
            	printStructure(
            		(FileSystemComposition.Component) enum.nextElement());
        }
        indent -= 4;
    }


    /**
	 * This example creates a simple structure as follows: Composite directory1 
	 * has three children: file1, directory2, and file3. directory2 has file2 
	 * as a child.
     *
     * Also, this example illustrates how to define methods that collect 
     * information from the whole aggreagate structure (using visitors).
     * One of them prints the compiste structure, the other one collects the
     * sum of the values of all leaves in the structure.   
     */

    public static void main(String[] args) { 
        
		System.out.println("\n<<< Sample AOP implementation of Composite pattern >>>\n");
		System.out.print  ("Creating Composite structure ...\n");

		Directory directory1 = new Directory("Directory1");
		Directory directory2 = new Directory("Directory2");
		File 	  file1      = new File("File1", 123);
		File      file2      = new File("File2", 4556);
		File      file3      = new File("File3", 16); 
        
        FileSystemComposition.aspectOf().addChild(directory1, file1);
        FileSystemComposition.aspectOf().addChild(directory1, directory2);
        FileSystemComposition.aspectOf().addChild(directory2, file2);
        FileSystemComposition.aspectOf().addChild(directory1, file3);
        
        System.out.println("done."); 
        System.out.println("This is the Structure:");
        
        printStructure(directory1);
        
        System.out.println("\nCalling printStructure(PrintStream) on Composition.Components ...");
        directory1.printStructure(System.out);  
        System.out.println("... done.");

        System.out.println("\nCalling subSum():int on the structure ...");
        System.out.println("The total size of the file system is: "+directory1.subSum());  
        System.out.println("... done.");

        System.out.println("\n<<< Test completed >>>\n");
    }
}
