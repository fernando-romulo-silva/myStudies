package br.com.fernando.chapter11_beanValidation.part01_builtInConstraints;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BuiltInConstraints02 {

    // -------------------------------------------------------------------------------------------------------------------------
    // Each constraint declaration can also override the message, group, and payload fields.
    //
    // Message: Message is used to override the default error message when the constraint is violated.
    //
    // Payload: Payload type that can be attached to a given constraint declaration.
    // Payloads are typically used to carry on metadata information consumed by a validation client.
    //
    // Group: Group is used to override the default validation group, explained later.
    //
    // ========================================================================================================================================================== //
    // Simple Payload
    //
    public static class Severity {

        public static class Info implements Payload {
        };

        public static class Error implements Payload {
        };
    }

    public static class Bean {

        // Constraint annotation must have an element 'payload' which may be assigned to an array of classes extending javax.validation.Payload.
        // By default this array should be empty , so it's optional.
        // Use of payloads is not considered portable.
        @NotNull(message = "", payload = { Severity.Error.class })
        private String nickName;

        @DecimalMax(value = "100000", message = "Price must not be higher than ${value}")
        private BigDecimal price;

        // Message with expression
        @Min(value = 2, message = "There must be at least {value} seat${value > 1 ? 's' : ''}")
        private int seatCount;

        @DecimalMax(value = "350", message = "The top speed ${formatter.format('%1$.2f', validatedValue)} is higher " + "than {value}")
        private double topSpeed;
    }

    // ========================================================================================================================================================== //
    // Invoking dynamic code using payload
    //
    // This example shows how payload can be used to invoke some dynamic code using reflection.
    // The idea is to attach an application level error handler, for instance a handler which will send email to the support team on errors.
    //
    public static interface AppErrorHandler<T> extends Payload {

        void onError(ConstraintViolation<T> violation);
    }

    public static class ErrorEmailSender<T> implements AppErrorHandler<T> {

        @Override
        public void onError(ConstraintViolation<T> violation) {
            System.out.println("Sending email to support team: " + violation.getPropertyPath() + " " + violation.getMessage());
        }
    }

    public static class TestBean {

        @NotNull(payload = { ErrorEmailSender.class })
        private String str;

        // getters and setters
    }

    // ========================================================================================================================================================== //
    // Java EE 7 adds a new attribute, validationAppliesTo, to constraint declaration that defines the constraint target
    // (i.e., the annotated element, the method return value, or the method parameters).
    //
    // The attribute can have the following values:
    //
    // ConstraintTarget.IMPLICIT: This is the default value and discovers the target when no ambiguity is present. It implies the annotated element if
    // it is not specified on a method or a constructor. If specified on a method or constructor with no parameter, it implies RETURN_VALUE.
    // If specified on a method with no return value, then it implies PARAMETERS. If there is ambiguity, then either RETURN_VALUE or PARAMETERS is required.
    //
    // ConstraintTarget.RETURN_VALUE: Applies to the return value of a method or constructor.
    //
    // ConstraintTarget.PARAMETERS: Applies to the parameters of a method or constructor.

    // ==========================================================================================================================================================
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void javaSe() throws Exception {

        Locale.setDefault(Locale.US);// Locale.FRANCE

        final Configuration<?> config = Validation.byDefaultProvider().configure();
        final ValidatorFactory factory = config.buildValidatorFactory(); // Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        final Bean bean = new Bean();

        final Set<ConstraintViolation<Bean>> constraintsViolationBean = validator.validate(bean);

        for (final ConstraintViolation<Bean> constraintViolationBean : constraintsViolationBean) {

            final Set<Class<? extends Payload>> payloadsBean = constraintViolationBean.getConstraintDescriptor().getPayload();

            for (final Class<? extends Payload> payloadBean : payloadsBean) {
                System.out.println("Payload: " + payloadBean + " " + constraintViolationBean.getPropertyPath() + " " + constraintViolationBean.getMessage());
            }

            System.out.println(constraintViolationBean);
        }

        // ------------------------------------------------------------------------------------------------------------------------------------

        final TestBean testBean = new TestBean();

        final Set<ConstraintViolation<TestBean>> constraintsViolationTestBean = validator.validate(testBean);

        for (final ConstraintViolation<TestBean> constraintViolationTestBean : constraintsViolationTestBean) {

            final Set<Class<? extends Payload>> payloadsTestBean = constraintViolationTestBean.getConstraintDescriptor().getPayload();

            for (final Class<? extends Payload> payloadTestBean : payloadsTestBean) {
                System.out.println("Payload: " + payloadTestBean + " " + constraintViolationTestBean.getPropertyPath() + " " + constraintViolationTestBean.getMessage());

                if (AppErrorHandler.class.isAssignableFrom(payloadTestBean)) {
                    try {
                        AppErrorHandler errorHandler = (AppErrorHandler) payloadTestBean.newInstance();
                        errorHandler.onError(constraintViolationTestBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println(constraintViolationTestBean);
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        javaSe();
    }
}
