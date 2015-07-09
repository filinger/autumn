package com.technoirarts.autumn.exception;

import com.technoirarts.autumn.eval.PropertyEvaluator;

/**
 * @author Filinger
 * @author Filinger (current maintainer)
 * @version 7/9/2015
 * @since 1.0
 */
public class PropertyEvaluationException extends Exception {

    private PropertyEvaluationException() {
    }

    private PropertyEvaluationException(String message) {
        super(message);
    }

    private PropertyEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    private PropertyEvaluationException(Throwable cause) {
        super(cause);
    }

    public PropertyEvaluationException(PropertyEvaluator evaluator, String message) {
        super(makeMessage(evaluator, message));
    }

    public PropertyEvaluationException(PropertyEvaluator evaluator, String message, Throwable cause) {
        super(makeMessage(evaluator, message), cause);
    }

    private static String makeMessage(PropertyEvaluator evaluator, String message) {
        return "Error on evaluator: " + evaluator + ": " + message;
    }
}
