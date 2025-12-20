package org.crashcourse.infra.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class LocalDateScalar implements Coercing<LocalDate, String> {

    private static final String DATE_PATTERN = "dd-MMM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public String serialize(final Object dataFetcherResult, final GraphQLContext graphQLContext, final Locale locale) throws CoercingSerializeException {
	
	if (dataFetcherResult instanceof LocalDate) {
	    return ((LocalDate) dataFetcherResult).format(DATE_FORMATTER);
	}
	
	throw new CoercingSerializeException("Invalid LocalDate value: " + dataFetcherResult);
    }

    @Override
    public LocalDate parseValue(final Object input, final GraphQLContext graphQLContext, final Locale locale) throws CoercingParseValueException {
	
	if (input instanceof String) {
	    try {
		return LocalDate.parse((String) input, DATE_FORMATTER);
	    } catch (DateTimeParseException e) {
		throw new CoercingParseValueException("Invalid LocalDate value: " + input);
	    }
	}
	
	throw new CoercingParseValueException("Invalid LocalDate value: " + input);
    }

    @Override
    public LocalDate parseLiteral(final Value<?> input, final CoercedVariables variables, final GraphQLContext graphQLContext, final Locale locale) throws CoercingParseLiteralException {
	
	if (input instanceof StringValue) {
	    try {
		return LocalDate.parse(((StringValue) input).getValue(), DATE_FORMATTER);
	    } catch (DateTimeParseException e) {
		throw new CoercingParseLiteralException("Invalid LocalDate value: " + input);
	    }
	}
	
	throw new CoercingParseLiteralException("Invalid LocalDate value: " + input);
    }

}
