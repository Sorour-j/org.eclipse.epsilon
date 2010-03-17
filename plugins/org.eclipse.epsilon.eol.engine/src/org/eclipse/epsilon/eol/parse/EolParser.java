// $ANTLR 3.1b1 E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g 2010-03-17 00:45:28

package org.eclipse.epsilon.eol.parse;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
public class EolParser extends org.eclipse.epsilon.commons.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "DIGIT", "INT", "POINT", "POINT_POINT", "ARROW", "BOOLEAN", "EscapeSequence", "STRING", "StrangeNameLiteral", "Letter", "JavaIDDigit", "NAME", "WS", "COMMENT", "LINE_COMMENT", "Annotation", "FORMAL", "PARAMLIST", "ASSIGNMENT", "SPECIAL_ASSIGNMENT", "HELPERMETHOD", "StatementBlock", "FOR", "IF", "ELSE", "WHILE", "RETURN", "BREAK", "BREAKALL", "CONTINUE", "TRANSACTION", "COLLECTION", "ABORT", "CollectionType", "ModelElementType", "PARAMETERS", "NewExpression", "VAR", "NEW", "ANNOTATIONBLOCK", "EXECUTABLEANNOTATION", "DELETE", "THROW", "EXPRLIST", "EXPRRANGE", "NativeType", "MultiplicativeExpression", "OPERATOR", "FeatureCall", "EOLMODULE", "BLOCK", "FEATURECALL", "TYPE", "ENUMERATION_VALUE", "IMPORT", "MODELDECLARATION", "NAMESPACE", "ALIAS", "DRIVER", "MODELDECLARATIONPARAMETERS", "MODELDECLARATIONPARAMETER", "ITEMSELECTOR", "'model'", "';'", "':'", "','", "'alias'", "'driver'", "'{'", "'}'", "'='", "'operation'", "'function'", "'('", "')'", "'import'", "'$'", "'!'", "'::'", "'#'", "'Native'", "'Collection'", "'Sequence'", "'List'", "'Bag'", "'Set'", "'OrderedSet'", "'for'", "'in'", "'if'", "'else'", "'while'", "'return'", "'throw'", "'delete'", "'break'", "'breakAll'", "'continue'", "'abort'", "'transaction'", "':='", "'::='", "'or'", "'and'", "'xor'", "'implies'", "'=='", "'>'", "'<'", "'>='", "'<='", "'<>'", "'+'", "'-'", "'*'", "'/'", "'not'", "'['", "']'", "'|'", "'new'", "'var'"
    };
    public static final int StatementBlock=26;
    public static final int WHILE=30;
    public static final int StrangeNameLiteral=13;
    public static final int NEW=43;
    public static final int FeatureCall=53;
    public static final int EOF=-1;
    public static final int BREAK=32;
    public static final int TYPE=57;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int IMPORT=59;
    public static final int T__92=92;
    public static final int NAME=16;
    public static final int T__90=90;
    public static final int RETURN=31;
    public static final int NewExpression=41;
    public static final int VAR=42;
    public static final int ANNOTATIONBLOCK=44;
    public static final int NativeType=50;
    public static final int ABORT=37;
    public static final int COMMENT=18;
    public static final int T__99=99;
    public static final int ITEMSELECTOR=66;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int MultiplicativeExpression=51;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LINE_COMMENT=19;
    public static final int BREAKALL=33;
    public static final int TRANSACTION=35;
    public static final int DRIVER=63;
    public static final int ELSE=29;
    public static final int EOLMODULE=54;
    public static final int MODELDECLARATION=60;
    public static final int PARAMLIST=22;
    public static final int INT=6;
    public static final int DELETE=46;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int HELPERMETHOD=25;
    public static final int T__89=89;
    public static final int NAMESPACE=61;
    public static final int T__88=88;
    public static final int CollectionType=38;
    public static final int T__126=126;
    public static final int T__125=125;
    public static final int T__71=71;
    public static final int WS=17;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int ALIAS=62;
    public static final int JavaIDDigit=15;
    public static final int Annotation=20;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int EscapeSequence=11;
    public static final int Letter=14;
    public static final int THROW=47;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int SPECIAL_ASSIGNMENT=24;
    public static final int T__67=67;
    public static final int MODELDECLARATIONPARAMETER=65;
    public static final int PARAMETERS=40;
    public static final int POINT=7;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int T__122=122;
    public static final int FOR=27;
    public static final int ENUMERATION_VALUE=58;
    public static final int T__121=121;
    public static final int T__120=120;
    public static final int FLOAT=4;
    public static final int EXECUTABLEANNOTATION=45;
    public static final int IF=28;
    public static final int ModelElementType=39;
    public static final int BOOLEAN=10;
    public static final int CONTINUE=34;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int COLLECTION=36;
    public static final int DIGIT=5;
    public static final int EXPRRANGE=49;
    public static final int OPERATOR=52;
    public static final int EXPRLIST=48;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int POINT_POINT=8;
    public static final int MODELDECLARATIONPARAMETERS=64;
    public static final int BLOCK=55;
    public static final int FEATURECALL=56;
    public static final int FORMAL=21;
    public static final int ARROW=9;
    public static final int ASSIGNMENT=23;
    public static final int STRING=12;

    // delegates
    public Eol_EolParserRules gEolParserRules;
    // delegators


        public EolParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EolParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gEolParserRules = new Eol_EolParserRules(input, state, this);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EolParser.tokenNames; }
    public String getGrammarFileName() { return "E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g"; }


    public static class eolModule_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start eolModule
    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:89:1: eolModule : ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock ( statement | operationDeclarationOrAnnotationBlock )* )? EOF -> ^( EOLMODULE ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock )* ) ;
    public final EolParser.eolModule_return eolModule() throws RecognitionException {
        EolParser.eolModule_return retval = new EolParser.eolModule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOF7=null;
        Eol_EolParserRules.importStatement_return importStatement1 = null;

        Eol_EolParserRules.modelDeclaration_return modelDeclaration2 = null;

        Eol_EolParserRules.block_return block3 = null;

        Eol_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock4 = null;

        Eol_EolParserRules.statement_return statement5 = null;

        Eol_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock6 = null;


        CommonTree EOF7_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        RewriteRuleSubtreeStream stream_modelDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule modelDeclaration");
        RewriteRuleSubtreeStream stream_operationDeclarationOrAnnotationBlock=new RewriteRuleSubtreeStream(adaptor,"rule operationDeclarationOrAnnotationBlock");
        try {
            // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:94:2: ( ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock ( statement | operationDeclarationOrAnnotationBlock )* )? EOF -> ^( EOLMODULE ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock )* ) )
            // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:95:2: ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock ( statement | operationDeclarationOrAnnotationBlock )* )? EOF
            {
            // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:95:2: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==80) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:95:3: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_eolModule64);
            	    importStatement1=importStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_importStatement.add(importStatement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:96:2: ( modelDeclaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==67) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:96:3: modelDeclaration
            	    {
            	    pushFollow(FOLLOW_modelDeclaration_in_eolModule70);
            	    modelDeclaration2=modelDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_modelDeclaration.add(modelDeclaration2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            pushFollow(FOLLOW_block_in_eolModule75);
            block3=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block3.getTree());
            // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:98:2: ( operationDeclarationOrAnnotationBlock ( statement | operationDeclarationOrAnnotationBlock )* )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==Annotation||(LA4_0>=76 && LA4_0<=77)||LA4_0==81) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:98:3: operationDeclarationOrAnnotationBlock ( statement | operationDeclarationOrAnnotationBlock )*
                    {
                    pushFollow(FOLLOW_operationDeclarationOrAnnotationBlock_in_eolModule79);
                    operationDeclarationOrAnnotationBlock4=operationDeclarationOrAnnotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_operationDeclarationOrAnnotationBlock.add(operationDeclarationOrAnnotationBlock4.getTree());
                    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:98:41: ( statement | operationDeclarationOrAnnotationBlock )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==FLOAT||LA3_0==INT||LA3_0==BOOLEAN||LA3_0==STRING||LA3_0==NAME||LA3_0==78||(LA3_0>=85 && LA3_0<=92)||LA3_0==94||(LA3_0>=96 && LA3_0<=104)||LA3_0==118||LA3_0==121||(LA3_0>=125 && LA3_0<=126)) ) {
                            alt3=1;
                        }
                        else if ( (LA3_0==Annotation||(LA3_0>=76 && LA3_0<=77)||LA3_0==81) ) {
                            alt3=2;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:98:42: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_eolModule82);
                    	    statement5=statement();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_statement.add(statement5.getTree());

                    	    }
                    	    break;
                    	case 2 :
                    	    // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:98:54: operationDeclarationOrAnnotationBlock
                    	    {
                    	    pushFollow(FOLLOW_operationDeclarationOrAnnotationBlock_in_eolModule86);
                    	    operationDeclarationOrAnnotationBlock6=operationDeclarationOrAnnotationBlock();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_operationDeclarationOrAnnotationBlock.add(operationDeclarationOrAnnotationBlock6.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            EOF7=(Token)match(input,EOF,FOLLOW_EOF_in_eolModule93); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF7);



            // AST REWRITE
            // elements: block, modelDeclaration, operationDeclarationOrAnnotationBlock, importStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 100:2: -> ^( EOLMODULE ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock )* )
            {
                // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:100:5: ^( EOLMODULE ( importStatement )* ( modelDeclaration )* block ( operationDeclarationOrAnnotationBlock )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EOLMODULE, "EOLMODULE"), root_1);

                // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:100:17: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();
                // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:100:34: ( modelDeclaration )*
                while ( stream_modelDeclaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_modelDeclaration.nextTree());

                }
                stream_modelDeclaration.reset();
                adaptor.addChild(root_1, stream_block.nextTree());
                // E:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.eol.engine\\src\\org\\eclipse\\epsilon\\eol\\parse\\Eol.g:100:58: ( operationDeclarationOrAnnotationBlock )*
                while ( stream_operationDeclarationOrAnnotationBlock.hasNext() ) {
                    adaptor.addChild(root_1, stream_operationDeclarationOrAnnotationBlock.nextTree());

                }
                stream_operationDeclarationOrAnnotationBlock.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end eolModule

    // Delegated rules
    public Eol_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException { return gEolParserRules.modelDeclarationParameter(); }
    public Eol_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException { return gEolParserRules.expressionOrStatementBlock(); }
    public Eol_EolParserRules.expressionList_return expressionList() throws RecognitionException { return gEolParserRules.expressionList(); }
    public Eol_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException { return gEolParserRules.executableAnnotation(); }
    public Eol_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException { return gEolParserRules.logicalExpression(); }
    public Eol_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException { return gEolParserRules.statementOrStatementBlock(); }
    public Eol_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException { return gEolParserRules.formalParameterList(); }
    public Eol_EolParserRules.declarativeFeatureCall_return declarativeFeatureCall() throws RecognitionException { return gEolParserRules.declarativeFeatureCall(); }
    public Eol_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException { return gEolParserRules.additiveExpression(); }
    public Eol_EolParserRules.formalParameter_return formalParameter() throws RecognitionException { return gEolParserRules.formalParameter(); }
    public Eol_EolParserRules.literal_return literal() throws RecognitionException { return gEolParserRules.literal(); }
    public Eol_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException { return gEolParserRules.breakAllStatement(); }
    public Eol_EolParserRules.newExpression_return newExpression() throws RecognitionException { return gEolParserRules.newExpression(); }
    public Eol_EolParserRules.modelAlias_return modelAlias() throws RecognitionException { return gEolParserRules.modelAlias(); }
    public Eol_EolParserRules.nativeType_return nativeType() throws RecognitionException { return gEolParserRules.nativeType(); }
    public Eol_EolParserRules.statement_return statement() throws RecognitionException { return gEolParserRules.statement(); }
    public Eol_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException { return gEolParserRules.deleteStatement(); }
    public Eol_EolParserRules.statementB_return statementB() throws RecognitionException { return gEolParserRules.statementB(); }
    public Eol_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException { return gEolParserRules.modelDeclaration(); }
    public Eol_EolParserRules.parameterList_return parameterList() throws RecognitionException { return gEolParserRules.parameterList(); }
    public Eol_EolParserRules.annotation_return annotation() throws RecognitionException { return gEolParserRules.annotation(); }
    public Eol_EolParserRules.returnStatement_return returnStatement() throws RecognitionException { return gEolParserRules.returnStatement(); }
    public Eol_EolParserRules.throwStatement_return throwStatement() throws RecognitionException { return gEolParserRules.throwStatement(); }
    public Eol_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException { return gEolParserRules.unaryExpression(); }
    public Eol_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException { return gEolParserRules.expressionStatement(); }
    public Eol_EolParserRules.statementBlock_return statementBlock() throws RecognitionException { return gEolParserRules.statementBlock(); }
    public Eol_EolParserRules.continueStatement_return continueStatement() throws RecognitionException { return gEolParserRules.continueStatement(); }
    public Eol_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException { return gEolParserRules.modelDeclarationParameters(); }
    public Eol_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException { return gEolParserRules.simpleFeatureCall(); }
    public Eol_EolParserRules.typeName_return typeName() throws RecognitionException { return gEolParserRules.typeName(); }
    public Eol_EolParserRules.breakStatement_return breakStatement() throws RecognitionException { return gEolParserRules.breakStatement(); }
    public Eol_EolParserRules.statementA_return statementA() throws RecognitionException { return gEolParserRules.statementA(); }
    public Eol_EolParserRules.expressionRange_return expressionRange() throws RecognitionException { return gEolParserRules.expressionRange(); }
    public Eol_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gEolParserRules.multiplicativeExpression(); }
    public Eol_EolParserRules.forStatement_return forStatement() throws RecognitionException { return gEolParserRules.forStatement(); }
    public Eol_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException { return gEolParserRules.transactionStatement(); }
    public Eol_EolParserRules.litteralCollection_return litteralCollection() throws RecognitionException { return gEolParserRules.litteralCollection(); }
    public Eol_EolParserRules.importStatement_return importStatement() throws RecognitionException { return gEolParserRules.importStatement(); }
    public Eol_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException { return gEolParserRules.assignmentStatement(); }
    public Eol_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException { return gEolParserRules.expressionListOrRange(); }
    public Eol_EolParserRules.pathName_return pathName() throws RecognitionException { return gEolParserRules.pathName(); }
    public Eol_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException { return gEolParserRules.primitiveExpression(); }
    public Eol_EolParserRules.featureCall_return featureCall() throws RecognitionException { return gEolParserRules.featureCall(); }
    public Eol_EolParserRules.modelElementType_return modelElementType() throws RecognitionException { return gEolParserRules.modelElementType(); }
    public Eol_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException { return gEolParserRules.variableDeclarationExpression(); }
    public Eol_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException { return gEolParserRules.postfixExpression(); }
    public Eol_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException { return gEolParserRules.operationDeclaration(); }
    public Eol_EolParserRules.whileStatement_return whileStatement() throws RecognitionException { return gEolParserRules.whileStatement(); }
    public Eol_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException { return gEolParserRules.operationDeclarationOrAnnotationBlock(); }
    public Eol_EolParserRules.elseStatement_return elseStatement() throws RecognitionException { return gEolParserRules.elseStatement(); }
    public Eol_EolParserRules.block_return block() throws RecognitionException { return gEolParserRules.block(); }
    public Eol_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException { return gEolParserRules.relationalExpression(); }
    public Eol_EolParserRules.ifStatement_return ifStatement() throws RecognitionException { return gEolParserRules.ifStatement(); }
    public Eol_EolParserRules.collectionType_return collectionType() throws RecognitionException { return gEolParserRules.collectionType(); }
    public Eol_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException { return gEolParserRules.itemSelectorExpression(); }
    public Eol_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException { return gEolParserRules.annotationBlock(); }
    public Eol_EolParserRules.modelNamespace_return modelNamespace() throws RecognitionException { return gEolParserRules.modelNamespace(); }
    public Eol_EolParserRules.modelDriver_return modelDriver() throws RecognitionException { return gEolParserRules.modelDriver(); }
    public Eol_EolParserRules.abortStatement_return abortStatement() throws RecognitionException { return gEolParserRules.abortStatement(); }


 

    public static final BitSet FOLLOW_importStatement_in_eolModule64 = new BitSet(new long[]{0x0000000000111450L,0x624001FF5FE37008L});
    public static final BitSet FOLLOW_modelDeclaration_in_eolModule70 = new BitSet(new long[]{0x0000000000111450L,0x624001FF5FE27008L});
    public static final BitSet FOLLOW_block_in_eolModule75 = new BitSet(new long[]{0x0000000000100000L,0x0000000000023000L});
    public static final BitSet FOLLOW_operationDeclarationOrAnnotationBlock_in_eolModule79 = new BitSet(new long[]{0x0000000000111450L,0x624001FF5FE27000L});
    public static final BitSet FOLLOW_statement_in_eolModule82 = new BitSet(new long[]{0x0000000000111450L,0x624001FF5FE27000L});
    public static final BitSet FOLLOW_operationDeclarationOrAnnotationBlock_in_eolModule86 = new BitSet(new long[]{0x0000000000111450L,0x624001FF5FE27000L});
    public static final BitSet FOLLOW_EOF_in_eolModule93 = new BitSet(new long[]{0x0000000000000002L});

}