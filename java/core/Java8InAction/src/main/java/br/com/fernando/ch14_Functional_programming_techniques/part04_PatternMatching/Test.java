package br.com.fernando.ch14_Functional_programming_techniques.part04_PatternMatching;

import java.util.function.Function;
import java.util.function.Supplier;

// Pattern matching
public class Test {

    // Visitor design pattern
    public static void test01() {
	final Expr e1 = new BinOp("+", new Number(5), new Number(0));
	final Expr match1 = simplify(e1);
	System.out.println(match1);
	
	System.out.println();
	
	final Expr e2 = new BinOp("+", new Number(5), new Number(3));
	final Expr match2 = simplify(e2);
	System.out.println(match2);
    }

    // ==================================================================================

    // static Expr simplifyExpression(Expr expr) {
    // if (expr instanceof BinOp && ((BinOp) expr).opname.equals("+") && ((BinOp) expr).right instanceof Number) {
    // return ((BinOp) expr).left;
    // }
    // }

    static <T> T myIf(boolean b, Supplier<T> truecase, Supplier<T> falsecase) {
	return b ? truecase.get() : falsecase.get();
    }

    interface TriFunction<S, T, U, R> {

	R apply(S s, T t, U u);
    }

    static <T> T patternMatchExpr(Expr e, TriFunction<String, Expr, Expr, T> binopcase, Function<Integer, T> numcase, Supplier<T> defaultcase) {

	return (e instanceof BinOp) ? //
	                            binopcase.apply(((BinOp) e).opname, ((BinOp) e).left, ((BinOp) e).right) : //
	                            (e instanceof Number) ? //
	                                                  numcase.apply(((Number) e).val) : //
	                                                  defaultcase.get();
    }

    static Expr simplify(Expr e) {
	final TriFunction<String, Expr, Expr, Expr> binopcase = // Deals with a BinOp expression
	                                                      (opname, left, right) -> {

	                                                          if ("+".equals(opname)) { // Deals with the addition case
		                                                      if (left instanceof Number && ((Number) left).val == 0) {
		                                                          return right;
		                                                      }
		                                                      if (right instanceof Number && ((Number) right).val == 0) {
		                                                          return left;
		                                                      }
	                                                          }

	                                                          if ("*".equals(opname)) { // Deals with the multiplication case
		                                                      if (left instanceof Number && ((Number) left).val == 1) {
		                                                          return right;
		                                                      }
		                                                      if (right instanceof Number && ((Number) right).val == 1) {
		                                                          return left;
		                                                      }
	                                                          }
	                                                          return new BinOp(opname, left, right);
	                                                      };

	final Function<Integer, Expr> numcase = val -> new Number(val); // Deals with a Number
	final Supplier<Expr> defaultcase = () -> new Number(0); // A default case if the user provides an Expr that's not recognized

	return patternMatchExpr(e, binopcase, numcase, defaultcase);

	// final Expr e = new BinOp("+", new Number(5), new Number(0));
	// final Expr match = patternMatchExpr(e, binopcase, numcase, defaultcase);
	//
	// if (match instanceof Number) {
	// System.out.println("Number: " + match);
	// } else if (match instanceof BinOp) {
	// System.out.println("BinOp: " + match);
	// }
    }

    // **********************************************************************************

    static class Expr {
    }

    static class Number extends Expr {

	int val;

	public Number(int val) {
	    this.val = val;
	}

	@Override
	public String toString() {
	    return "" + val;
	}
    }

    static class BinOp extends Expr {

	String opname;

	Expr left, right;

	public BinOp(String opname, Expr left, Expr right) {
	    this.opname = opname;
	    this.left = left;
	    this.right = right;
	}

	@Override
	public String toString() {
	    return "(" + left + " " + opname + " " + right + ")";
	}

	public Expr accept(SimplifyExprVisitor v) {
	    return v.visit(this);
	}
    }

    static class SimplifyExprVisitor {

	public Expr visit(BinOp e) {
	    if ("+".equals(e.opname) && e.right instanceof Number) {
		return e.left;
	    }
	    return e;
	}
    }

    // =================================================================================
    public static void main(String[] args) {
	test01();
    }

}
