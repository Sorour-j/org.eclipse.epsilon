package org.eclipse.epsilon.flock.parse;

import junit.framework.TestCase;
import java.io.*;
import java.lang.reflect.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class TestFlock extends TestCase {
	String stdout;
	String stderr;

	public void testFlockModule1() throws Exception {
		// test input: ""
		Object retval = execParser("flockModule", "", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "FLOCKMODULE";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule2() throws Exception {
		// test input: "migrate Person { name := nom; }"
		Object retval = execParser("flockModule", "migrate Person { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (MIGRATE Person (BLOCK (:= name nom))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule3() throws Exception {
		// test input: "migrate Person { name := nom; } migrate Animal { name := nom; }"
		Object retval = execParser("flockModule", "migrate Person { name := nom; } migrate Animal { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (MIGRATE Person (BLOCK (:= name nom))) (MIGRATE Animal (BLOCK (:= name nom))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule4() throws Exception {
		// test input: "operation Integer inc() { self := self + 1; }"
		Object retval = execParser("flockModule", "operation Integer inc() { self := self + 1; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (HELPERMETHOD Integer inc (BLOCK (:= self (+ self 1)))))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFlockModule5() throws Exception {
		// test input: "@cached operation Integer inc() { self := self + 1; }"
		Object retval = execParser("flockModule", "@cached operation Integer inc() { self := self + 1; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(FLOCKMODULE (ANNOTATIONBLOCK @cached operation Integer inc() { self := self + 1; }))";

		assertEquals("testing rule "+"flockModule", expecting, actual);
	}

	public void testFullMigrateRule1() throws Exception {
		// test input: "migrate Person { }"
		Object retval = execParser("fullMigrateRule", "migrate Person { }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person BLOCK)";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule2() throws Exception {
		// test input: "migrate Person { name := nom; "
		Object retval = execParser("fullMigrateRule", "migrate Person { name := nom; ", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule3() throws Exception {
		// test input: "migrate Person { name := nom; }"
		Object retval = execParser("fullMigrateRule", "migrate Person { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person (BLOCK (:= name nom)))";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule4() throws Exception {
		// test input: "migrate Animal to Dog { }"
		Object retval = execParser("fullMigrateRule", "migrate Animal to Dog { }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Animal Dog BLOCK)";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule5() throws Exception {
		// test input: "migrate Animal to { }"
		Object retval = execParser("fullMigrateRule", "migrate Animal to { }", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule6() throws Exception {
		// test input: "migrate Person when: original.name.isDefined() { name := nom; }"
		Object retval = execParser("fullMigrateRule", "migrate Person when: original.name.isDefined() { name := nom; }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person (GUARD (. (. original name) (isDefined PARAMETERS))) (BLOCK (:= name nom)))";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule7() throws Exception {
		// test input: "migrate Person when {}"
		Object retval = execParser("fullMigrateRule", "migrate Person when {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule8() throws Exception {
		// test input: "migrate Person original.name.isDefined(); {}"
		Object retval = execParser("fullMigrateRule", "migrate Person original.name.isDefined(); {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule9() throws Exception {
		// test input: "migrate Person when: original.name.isDefined(); {}"
		Object retval = execParser("fullMigrateRule", "migrate Person when: original.name.isDefined(); {}", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testFullMigrateRule10() throws Exception {
		// test input: "migrate Person"
		Object retval = execParser("fullMigrateRule", "migrate Person", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"fullMigrateRule", expecting, actual);
	}

	public void testShorthandMigrateRule1() throws Exception {
		// test input: "migrate Person to Salesperson"
		Object retval = execParser("shorthandMigrateRule", "migrate Person to Salesperson", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(MIGRATE Person Salesperson)";

		assertEquals("testing rule "+"shorthandMigrateRule", expecting, actual);
	}

	public void testShorthandMigrateRule2() throws Exception {
		// test input: "migrate Person"
		Object retval = execParser("shorthandMigrateRule", "migrate Person", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"shorthandMigrateRule", expecting, actual);
	}

	public void testGuard1() throws Exception {
		// test input: "when: original.name.isDefined()"
		Object retval = execParser("guard", "when: original.name.isDefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(GUARD (. (. original name) (isDefined PARAMETERS)))";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	public void testGuard2() throws Exception {
		// test input: "when { var n := original.name; return n.isDefined(); }"
		Object retval = execParser("guard", "when { var n := original.name; return n.isDefined(); }", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(GUARD (BLOCK (:= (var n) (. original name)) (RETURN (. n (isDefined PARAMETERS)))))";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	public void testGuard3() throws Exception {
		// test input: "original.name.isDefined()"
		Object retval = execParser("guard", "original.name.isDefined()", false);
		Object actual = examineParserExecResult(28, retval);
		Object expecting = "FAIL";

		assertEquals("testing rule "+"guard", expecting, actual);
	}

	public void testDeleteRule1() throws Exception {
		// test input: "delete Person"
		Object retval = execParser("deleteRule", "delete Person", false);
		Object actual = examineParserExecResult(10, retval);
		Object expecting = "(DELETE Person)";

		assertEquals("testing rule "+"deleteRule", expecting, actual);
	}

	public void testDeleteRule2() throws Exception {
		// test input: "delete Person when: original.name.isUndefined()"
		Object retval = execParser("deleteRule", "delete Person when: original.name.isUndefined()", false);
		Object actual = examineParserExecResult(8, retval);
		Object expecting = "(DELETE Person (GUARD (. (. original name) (isUndefined PARAMETERS))))";

		assertEquals("testing rule "+"deleteRule", expecting, actual);
	}

	// Invoke target parser.rule
	public Object execParser(String testRuleName, String testInput, boolean isFile) throws Exception {
		CharStream input;
		/** Set up ANTLR input stream based on input source, file or String */
		if ( isFile==true ) {
			input = new ANTLRFileStream(testInput);
		}
		else {
			input = new ANTLRStringStream(testInput);
		}
		try {
			FlockLexer lexer = new FlockLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			FlockParser parser = new FlockParser(tokens);

			parser.prepareForGUnit();

			/** Use Reflection to get rule method from parser */
			Method ruleName = Class.forName("org.eclipse.epsilon.flock.parse.FlockParser").getMethod(testRuleName);

			/** Start of I/O Redirecting */
			PipedInputStream pipedIn = new PipedInputStream();
			PipedOutputStream pipedOut = new PipedOutputStream();
			PipedInputStream pipedErrIn = new PipedInputStream();
			PipedOutputStream pipedErrOut = new PipedOutputStream();
			try {
				pipedOut.connect(pipedIn);
				pipedErrOut.connect(pipedErrIn);
			}
			catch(IOException e) {
				System.err.println("connection failed...");
				System.exit(1);
			}
			PrintStream console = System.out;
			PrintStream consoleErr = System.err;
			PrintStream ps = new PrintStream(pipedOut);
			PrintStream ps2 = new PrintStream(pipedErrOut);
			System.setOut(ps);
			System.setErr(ps2);
			/** End of redirecting */

			/** Invoke grammar rule, and store if there is a return value */
			Object ruleReturn = ruleName.invoke(parser);
			String astString = null;
			/** If rule has return value, determine if it's an AST */
			if ( ruleReturn!=null ) {
				/** If return object is instanceof AST, get the toStringTree */
				if ( ruleReturn.toString().indexOf(testRuleName+"_return")>0 ) {
					try {	// NullPointerException may happen here...
						Class _return = Class.forName("org.eclipse.epsilon.flock.parse.FlockParser"+"$"+testRuleName+"_return");            	
						Method[] methods = _return.getDeclaredMethods();
                		for(Method method : methods) {
			                if ( method.getName().equals("getTree") ) {
			                	Method returnName = _return.getMethod("getTree");
		                    	CommonTree tree = (CommonTree) returnName.invoke(ruleReturn);
		                    	astString = tree.toStringTree();
			                }
			            }
					}
					catch(Exception e) {
                		System.err.println(e);
                	}
				}
			}

			org.antlr.gunit.gUnitExecuter.StreamVacuum stdoutVacuum = new org.antlr.gunit.gUnitExecuter.StreamVacuum(pipedIn);
			org.antlr.gunit.gUnitExecuter.StreamVacuum stderrVacuum = new org.antlr.gunit.gUnitExecuter.StreamVacuum(pipedErrIn);
			ps.close();
			ps2.close();
			System.setOut(console);			// Reset standard output
			System.setErr(consoleErr);		// Reset standard err out
			this.stdout = null;
			this.stderr = null;
			stdoutVacuum.start();
			stderrVacuum.start();			
			stdoutVacuum.join();
			stderrVacuum.join();
			// retVal could be actual return object from rule, stderr or stdout
			if ( stderrVacuum.toString().length()>0 ) {
				this.stderr = stderrVacuum.toString();
				return this.stderr;
			}
			if ( stdoutVacuum.toString().length()>0 ) {
				this.stdout = stdoutVacuum.toString();
			}
			if ( astString!=null ) {	// Return toStringTree of AST
				return astString;
			}
			if ( ruleReturn!=null ) {
				return ruleReturn;
			}
			if ( stderrVacuum.toString().length()==0 && stdoutVacuum.toString().length()==0 ) {
				return null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); System.exit(1);
		} catch (SecurityException e) {
			e.printStackTrace(); System.exit(1);
		} catch (NoSuchMethodException e) {
			e.printStackTrace(); System.exit(1);
		} catch (IllegalAccessException e) {
			e.printStackTrace(); System.exit(1);
		} catch (InvocationTargetException e) {
			e.printStackTrace(); System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace(); System.exit(1);
		} catch (Exception e) {
			e.printStackTrace(); System.exit(1);
		}
		return stdout;
	}

	// Modify the return value if the expected token type is OK or FAIL
	public Object examineParserExecResult(int tokenType, Object retVal) {	
		if ( tokenType==27 ) {	// expected Token: OK
			if ( this.stderr==null ) {
				return "OK";
			}
			else {
				return "FAIL";
			}
		}
		else if ( tokenType==28 ) {	// expected Token: FAIL
			if ( this.stderr!=null ) {
				return "FAIL";
			}
			else {
				return "OK";
			}
		}
		else {	// return the same object for the other token types
			return retVal;
		}		
	}

}
