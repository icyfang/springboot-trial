package com.example.springcontext.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Hodur
 * @date 2021/9/15
 */
public class SpelParserDemo {

    private final static ExpressionParser PARSER = new SpelExpressionParser();

    public static String evalWithDollarSign(String expr) {
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "avatar");
        Expression expression = PARSER.parseExpression(expr, new TemplateParserContext("${", "}"));
        return expression.getValue(context, String.class);
    }

    public static String eval(String expr) {
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "avatar");
        Expression expression = PARSER.parseExpression(expr, new TemplateParserContext());
        return expression.getValue(context, String.class);
    }

    public static void main(String[] args) {

        System.out.println(eval("#{#name=='avatar'?'no name':#name}, is logging"));
        System.out.println(evalWithDollarSign("${#name=='avatar'?'no name':#name}, is logging"));
    }
}
