/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Feb 26, 2021     ********           Administrator            Initialize                  
 *                                                                                
 */
package com.taivn.common.builder;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.ValueHandlerFactory;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.BinaryOperatorExpression;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

/**
 * The Class ArrayPredicate.
 *
 * @author Taivn
 */
public class ArrayPredicate extends AbstractSimplePredicate implements BinaryOperatorExpression<Boolean>, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1465502824980307327L;

    /** The comparison operator. */
    private final ArrayComparisonOperator comparisonOperator;

    /** The left hand side. */
    private final Expression<?> leftHandSide;

    /** The right hand side. */
    private final Expression<?> rightHandSide;

    /**
     * Instantiates a new array predicate.
     *
     * @param criteriaBuilder    the criteria builder
     * @param comparisonOperator the comparison operator
     * @param leftHandSide       the left hand side
     * @param rightHandSide      the right hand side
     */
    public ArrayPredicate(CriteriaBuilderImpl criteriaBuilder, ArrayComparisonOperator comparisonOperator,
                          Expression<?> leftHandSide, Expression<?> rightHandSide) {
        super(criteriaBuilder);
        this.comparisonOperator = comparisonOperator;
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    /**
     * Instantiates a new array predicate.
     *
     * @param criteriaBuilder    the criteria builder
     * @param comparisonOperator the comparison operator
     * @param leftHandSide       the left hand side
     * @param rightHandSide      the right hand side
     */
    @SuppressWarnings({ "unchecked" })
    public ArrayPredicate(CriteriaBuilderImpl criteriaBuilder, ArrayComparisonOperator comparisonOperator,
                          Object leftHandSide, Expression<?> rightHandSide) {
        super(criteriaBuilder);
        this.comparisonOperator = comparisonOperator;
        this.rightHandSide = rightHandSide;
        if (ValueHandlerFactory.isNumeric(rightHandSide.getJavaType())) {
            this.leftHandSide = new LiteralExpression<Object>(criteriaBuilder,
                    ValueHandlerFactory.convert(rightHandSide, (Class<Number>) rightHandSide.getJavaType()));
        } else {
            this.leftHandSide = new LiteralExpression<Object>(criteriaBuilder, rightHandSide);
        }
    }

    /**
     * Instantiates a new array predicate.
     *
     * @param <N>                the number type
     * @param criteriaBuilder    the criteria builder
     * @param comparisonOperator the comparison operator
     * @param leftHandSide       the left hand side
     * @param rightHandSide      the right hand side
     */
    @SuppressWarnings({ "unchecked" })
    public <N extends Number> ArrayPredicate(CriteriaBuilderImpl criteriaBuilder,
                                             ArrayComparisonOperator comparisonOperator, Expression<N> leftHandSide, Number rightHandSide) {
        super(criteriaBuilder);
        this.comparisonOperator = comparisonOperator;
        this.leftHandSide = leftHandSide;
        Class<?> type = leftHandSide.getJavaType();
        if (Number.class.equals(type)) {
            this.rightHandSide = new LiteralExpression<Object>(criteriaBuilder, rightHandSide);
        } else {
            N converted = (N) ValueHandlerFactory.convert(rightHandSide, type);
            this.rightHandSide = new LiteralExpression<N>(criteriaBuilder, converted);
        }
    }

    /**
     * Gets the comparison operator.
     *
     * @return the comparison operator
     */
    public ArrayComparisonOperator getComparisonOperator() {
        return getComparisonOperator(isNegated());
    }

    /**
     * Gets the comparison operator.
     *
     * @param isNegated the is negated
     * @return the comparison operator
     */
    public ArrayComparisonOperator getComparisonOperator(boolean isNegated) {
        return isNegated ? comparisonOperator.negated() : comparisonOperator;
    }

    /**
     * Gets the left hand operand.
     *
     * @return the left hand operand
     */
    @SuppressWarnings("unchecked")
    @Override
    public Expression getLeftHandOperand() {
        return leftHandSide;
    }

    /**
     * Gets the right hand operand.
     *
     * @return the right hand operand
     */
    @SuppressWarnings("unchecked")
    @Override
    public Expression getRightHandOperand() {
        return rightHandSide;
    }

    /**
     * Register parameters.
     *
     * @param registry the registry
     */
    @Override
    public void registerParameters(ParameterRegistry registry) {
        Helper.possibleParameter(getLeftHandOperand(), registry);
        Helper.possibleParameter(getRightHandOperand(), registry);
    }

    /**
     * Defines the comparison operators. We could also get away with only 3 and use
     * negation...
     */
    public static enum ArrayComparisonOperator {

        /** The equal. */
        CONTAINS {
            public ArrayComparisonOperator negated() {
                return IS_CONTAINED_BY;
            }

            public String rendered() {
                return " = ";
            }
        },

        /** The not equal. */
        IS_CONTAINED_BY {
            public ArrayComparisonOperator negated() {
                return CONTAINS;
            }

            public String rendered() {
                return " <@ ";
            }
        };

        /**
         * Negated.
         *
         * @return the comparison operator
         */
        public abstract ArrayComparisonOperator negated();

        /**
         * Rendered.
         *
         * @return the string
         */
        public abstract String rendered();
    }

    /**
     * Render.
     *
     * @param isNegated        the is negated
     * @param renderingContext the rendering context
     * @return the string
     */
    @Override
    public String render(boolean isNegated, RenderingContext renderingContext) {
        return ((Renderable) getLeftHandOperand()).render(renderingContext)
                + getComparisonOperator(isNegated).rendered() + (String.format("ANY(%s)", renderingContext));
    }
}
